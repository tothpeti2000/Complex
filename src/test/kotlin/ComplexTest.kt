import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class ComplexTest {
    companion object {
        const val DELTA = 1e-14
    }

    private val z1 = Triple(1 + i,
            sqrt(2.0) * (cos(PI / 4) + sin(PI / 4) * i),
            sqrt(2.0) * exp(PI / 4 * i))

    private val z2 = Triple(1 - i,
        sqrt(2.0) * (cos(-PI / 4) + sin(-PI / 4) * i),
        sqrt(2.0) * exp(-PI / 4 * i))

    private fun assertEquals(expected: Double, actual: Double) = assertEquals(expected, actual, DELTA)

    private fun assertComplexEquals(expected: Complex, actual: Complex) {
        val (expectedRe, expectedIm) = expected
        val (actualRe, actualIm) = actual

        assertEquals(expectedRe, actualRe)
        assertEquals(expectedIm, actualIm)
    }

    private inline fun Triple<Complex, Complex, Complex>.forEach(actions: Complex.() -> Unit) {
        this.toList().forEach { actions(it) }
    }

    @Test
    fun `test constructor creation`() {
        val (re1, im1) = Complex()

        assertEquals(0.0, re1)
        assertEquals(0.0, im1)

        val (re2, im2) = Complex(1)

        assertEquals(1.0, re2)
        assertEquals(0.0, im2)

        val (re3, im3) = Complex(im = 1F)

        assertEquals(0.0, re3)
        assertEquals(1.0, im3)

        val (re4, im4) = Complex(1.0, 1L)

        assertEquals(1.0, re4)
        assertEquals(1.0, im4)
    }

    @Test
    fun `test manual creation in algebraic form`() {
        val (re1, im1) = z1.first
        val (re2, im2) = z2.first

        assertEquals(1.0, re1)
        assertEquals(1.0, im1)

        assertEquals(1.0, re2)
        assertEquals(-1.0, im2)
    }

    @Test
    fun `test manual creation in trigonometric form`() {
        val (re, im) = z1.second

        assertEquals(1.0, re)
        assertEquals(1.0, im)
    }

    @Test
    fun `test manual creation in exponential form`() {
        val (re, im) = z1.third

        assertEquals(1.0, re)
        assertEquals(1.0, im)
    }

    @Test
    fun `test extension properties`() {
        z1.forEach {
            assertComplexEquals(1 - i, conjugate)
            assertEquals(sqrt(2.0), r)
            assertEquals(PI / 4, theta)
        }
    }

    @Test
    fun `test negation`() {
        z1.forEach { assertComplexEquals(-1 - i, -this) }
    }

    @Test
    fun `test complex + complex`() {
        z1.forEach {
            also {
                z2.forEach { assertComplexEquals(Complex(2), it + this) }
            }
        }
    }

    @Test
    fun `test complex + number`() {
        z1.forEach { assertComplexEquals(2 + i, this + 1) }
    }

    @Test
    fun `test number + complex`() {
        z1.forEach { assertComplexEquals(2 + i, 1 + this) }
    }

    @Test
    fun `test complex - complex`() {
        z1.forEach {
            also {
                z2.forEach { assertComplexEquals(2 * i, it - this) }
            }
        }
    }

    @Test
    fun `test complex - number`() {
        z1.forEach { assertComplexEquals(i, this - 1) }
    }

    @Test
    fun `test number - complex`() {
        z1.forEach { assertComplexEquals(-i, 1 - this) }
    }

    @Test
    fun `test complex times complex`() {
        z1.forEach {
            also {
                z2.forEach { assertComplexEquals(Complex(2), it * this) }
            }
        }
    }

    @Test
    fun `test complex times number`() {
        z1.forEach { assertComplexEquals(2 + 2 * i, this * 2) }
    }

    @Test
    fun `test number times complex`() {
        z1.forEach { assertComplexEquals(2 + 2 * i, 2 * this) }
    }

    @Test
    fun `test complex divided by complex with 0 absolute value`() {
        val zero = Complex()
        z1.forEach { assertThrows(IllegalArgumentException::class.java) { this / zero } }
    }

    @Test
    fun `test complex divided by complex`() {
        z1.forEach {
            also {
                z2.forEach { assertComplexEquals(i, it / this) }
            }
        }
    }

    @Test
    fun `test complex divided by 0`() {
        z1.forEach { assertThrows(IllegalArgumentException::class.java) { this / 0 } }
    }

    @Test
    fun `test complex divided by number`() {
        z1.forEach { assertComplexEquals(0.5 + 0.5 * i, this / 2) }
    }

    @Test
    fun `test number divided by complex`() {
        z1.forEach { assertComplexEquals(1 - i, 2 / this) }
    }

    @Test
    fun `test powers`() {
        z1.forEach { assertComplexEquals(2 * i, square()) }
        z1.forEach { assertComplexEquals(-2 + 2 * i, cube()) }
        z1.forEach { assertComplexEquals(Complex(-4), pow(4)) }
        z1.forEach { assertComplexEquals(1.46869393991588 + 2.28735528717884 * i, exp(this)) }
    }

    @Test
    fun `test invalid root`() {
        z1.forEach { assertThrows(java.lang.IllegalArgumentException::class.java) { this.root(-2) } }
    }

    @Test
    fun `test roots`() {
        z1.forEach { assertComplexEquals(1.09868411346780 + 0.45508986056222 * i, sqrt()) }
        z1.forEach { assertComplexEquals(1.08421508149135 + 0.29051455550725 * i, cbrt()) }
        z1.forEach { assertComplexEquals(1.06955393236398 + 0.21274750472674 * i, root(4)) }
    }

    @Test
    fun `test forms with invalid fraction digit numbers`() {
        z1.forEach { assertThrows(java.lang.IllegalArgumentException::class.java) { toAlgebraicForm(-1) } }
        z1.forEach { assertThrows(java.lang.IllegalArgumentException::class.java) { toTrigonometricForm(-1) } }
        z1.forEach { assertThrows(java.lang.IllegalArgumentException::class.java) { toExponentialForm(-1) } }
    }

    @Test
    fun `test algebraic form`() {
        z1.forEach { assertEquals("1.0987 + 0.4551i", sqrt().toAlgebraicForm()) }
        z2.forEach { assertEquals("1.0987 - 0.4551i", sqrt().toAlgebraicForm()) }

        z1.forEach { assertEquals("1.098684 + 0.45509i", sqrt().toAlgebraicForm(6)) }
        z2.forEach { assertEquals("1.098684 - 0.45509i", sqrt().toAlgebraicForm(6)) }
    }

    @Test
    fun `test trigonometric form`() {
        z1.forEach { assertEquals("1.1892 (cos(0.3927) + sin(0.3927)i)", sqrt().toTrigonometricForm()) }
        z1.forEach { assertEquals("1.189207 (cos(0.392699) + sin(0.392699)i)", sqrt().toTrigonometricForm(6)) }
    }

    @Test
    fun `test exponential form`() {
        z1.forEach { assertEquals("1.1892 e^0.3927i", sqrt().toExponentialForm()) }
        z1.forEach { assertEquals("1.189207 e^0.392699i", sqrt().toExponentialForm(6)) }
    }
}
