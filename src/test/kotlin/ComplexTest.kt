import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.sqrt

class ComplexTest {
    companion object {
        private const val DELTA = 1e-15
    }

    @Test
    fun `test creation with default values`() {
        val (re, im) = Complex()

        assertEquals(0.0, re, DELTA)
        assertEquals(0.0, im, DELTA)
    }

    @Test
    fun `test creation with real part`() {
        val (re, im) = Complex(1)

        assertEquals(1.0, re, DELTA)
        assertEquals(0.0, im, DELTA)
    }

    @Test
    fun `test creation with imaginary part`() {
        val (re, im) = Complex(im = 1F)

        assertEquals(0.0, re, DELTA)
        assertEquals(1.0, im, DELTA)
    }

    @Test
    fun `test creation with real and imaginary parts`() {
        val (re, im) = Complex(1.0, 1L)

        assertEquals(1.0, re, DELTA)
        assertEquals(1.0, im, DELTA)
    }

    @Test
    fun `test cube root`() {
        val z = 1 + sqrt(3.0) * i

        val cubeRoot = z.cbrt()
        val expected = Complex(1.1839385133590, 0.4309183780640)

        assertComplexEquals(expected, cubeRoot)
    }
}
