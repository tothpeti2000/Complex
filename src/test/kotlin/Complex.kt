import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.abs

class Complex {
    companion object {
        private const val PRECISION = 1e-15
    }

    private fun assertDoubleEquals(expected: Double, actual: Double) = abs(expected - actual) < PRECISION

    // Watch out for -INF, INF and 0 as well
    @Test
    fun `two plus three equals five`() {
        assertDoubleEquals(0.0, 0.00001)
    }

}
