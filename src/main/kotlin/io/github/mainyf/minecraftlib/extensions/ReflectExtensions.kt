@file:Suppress("UNCHECKED_CAST")

package io.github.mainyf.minecraftlib.extensions

import java.io.File
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.util.HashSet
import java.util.jar.JarFile

@Throws(NoSuchFieldException::class, IllegalAccessException::class)
fun Any.setFinalFieldValue(fieldName: String, value: Any) {
    setFieldValue(fieldName, value, intArrayOf(Modifier.FINAL, Modifier.FINAL))
}

@Throws(NoSuchFieldException::class, IllegalAccessException::class)
fun Any.setStaticFinalFieldValue(fieldName: String, value: Any) {
    setFieldValue(fieldName, value, intArrayOf(Modifier.FINAL))
}

@Throws(NoSuchFieldException::class, IllegalAccessException::class)
fun Any.setFieldValue(fieldName: String, value: Any) {
    setFieldValue(fieldName, value, null)
}

fun Any.getFieldValue(fieldName: String, hasSuperField: Boolean = true): Any? {
    if(!hasSuperField) {
        return getFieldValue(this, this.javaClass, fieldName)
    }
    var result: Any? = null
    parentOperate(this.javaClass) {
        result = getFieldValue(this, it, fieldName)
    }
    return result
}

private fun getFieldValue(source: Any, clazz: Class<*>, fieldName: String): Any? {
    val field = clazz.declaredFields.getWithFirst { it.name == fieldName } ?: return null
    field.isAccessible = true
    return field.get(source)
}

@Throws(NoSuchFieldException::class, IllegalAccessException::class)
private fun Any.setFieldValue(fieldName: String, value: Any, modifiers: IntArray?) {
    parentOperate(this.javaClass) { clazz ->
        clazz.declaredFields.filter { it.name == fieldName }.forEach { field ->
            field.isAccessible = true
            modifiers?.let {
                val modifiersField = Field::class.java.getDeclaredField("modifiers")
                modifiersField.isAccessible = true
                it.forEach {
                    modifiersField.setInt(field, field.modifiers and it.inv())
                }
            }
            field.set(this, value)
            return@parentOperate
        }
    }
}

fun <T> Class<T>.getTargetAnnotationMethod(annotation: Class<out Annotation>): Method? {
    var result: Method? = null
    parentOperate(this) { clazz ->
        clazz.declaredMethods.filter { it.getAnnotation(annotation) != null }.forEach {
            result = it
            return@parentOperate
        }
    }
    return result
}

fun <T> Class<T>.getTargetAnnotationMethods(annotation: Class<out Annotation>): Set<Method> {
    return HashSet<Method>().let {result ->
        parentOperate(this) { clazz ->
            result.addAll(clazz.declaredMethods.filter { it.getAnnotation(annotation) != null })
        }
        result
    }
}

fun <T> Class<T>.getFieldsAndSuperField(): Set<Field> {
    return mutableSetOf<Field>().let { result ->
        parentOperate(this) { result.addAll(it.declaredFields) }
        result.toSet()
    }
}

private fun parentOperate(type: Class<*>, operater: (clazz: Class<*>) -> Unit) {
    var clazz = type.superclass
    while(clazz != null && clazz != Any::class.java) {
        operater.invoke(clazz)
        clazz = clazz.superclass
    }
}

fun Class<*>.getAllClass(include: List<String> = listOf()): List<Class<*>> {
    val jarFile = File(this.protectionDomain.codeSource.location.toURI())
    return mutableListOf<Class<*>>().apply {
        val jar = JarFile(jarFile)
        jar.stream().filter { it.name.endsWith(".class") }.forEach {
            val className = it.name
                .replace("/", ".")
                .replace(".class", "")
            if(include.isEmpty() || include.containsWith { ig -> className.contains(ig) }) {
                add(Class.forName(className))
            }
        }
    }
}

fun List<Class<*>>.getAnnotation(annotation: Annotation): List<Class<*>> {
    val result = mutableListOf<Class<*>>()
    this.forEach {
        val annotationClass = it.getAnnotation(annotation::class.java)
        if(annotationClass != null) {
            result.add(it)
        }
    }
    return result
}

fun <T> Class<T>.getChildrens(list: List<Class<*>>, hasDirect: Boolean = false): List<Class<T>> {
    if(hasDirect) {
        return list.filter { it.superclass == this }.toList() as List<Class<T>>
    }
    val result = mutableListOf<Class<T>>()
    list.forEach {
        parentOperate(it) { clazz ->
            if(clazz == this) {
                result.add(it as Class<T>)
                return@parentOperate
            }
        }
    }
    return result
}