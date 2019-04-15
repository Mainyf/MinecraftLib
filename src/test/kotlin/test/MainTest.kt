package test

import io.github.mainyf.minecraftlib.utils.RandomUtils
import org.junit.Assert.assertTrue
import org.junit.Test

class MainTest {

    @Test
    fun test() {
    }

    @Test
    fun testRandom() {
        for(i in 0 until 1000) {
            val value = RandomUtils.randomPercent(0)
//            println(value)
            assertTrue(!value)
        }
        for(i in 0 until 1000) {
            val value = RandomUtils.randomPercent(100)
//            println(value)
            assertTrue(value)
        }
    }

}