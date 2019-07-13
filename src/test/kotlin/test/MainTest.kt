package test

import io.github.mainyf.minecraftlib.data.YamlHelper
import io.github.mainyf.minecraftlib.utils.DoubleValueRange
import org.junit.Test
import java.nio.file.Paths

class MainTest {

    @Test
    fun test() {
    }

    @Test
    fun testRandom() {
//        for(i in 0 until 1000) {
//            val value = RandomUtils.randomPercent(0)
////            println(value)
//            assertTrue(!value)
//        }
//        for(i in 0 until 1000) {
//            val value = RandomUtils.randomPercent(100)
////            println(value)
//            assertTrue(value)
//        }
        val settings = MarkSettings()
        settings.name = "Mainyf"
        settings.text = "SSSSS"
        settings.spliteChar = ":"
        settings.defaultValue = DoubleValueRange(1.0, 1.0)
        YamlHelper.saveFile(Paths.get("demo.yml"), mapOf(
            "asdads" to settings
        ))
    }

    class MarkSettings {

        lateinit var name: String
        lateinit var text: String
        lateinit var spliteChar: String
        var defaultValue: Any? = null

        override fun toString(): String {
            return "MarkSettings(name='$name', text='$text', spliteChar='$spliteChar', defaultValue=$defaultValue)"
        }


    }
}