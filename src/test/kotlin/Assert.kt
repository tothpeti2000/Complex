import org.junit.Assert.assertTrue
import kotlin.math.abs

private const val DELTA = 1e-13

fun Double.nearlyEquals(other: Double, delta: Double = DELTA) = abs(this - other) <= delta

fun assertComplexEquals(expected: Complex, actual: Complex, delta: Double = DELTA) {
    val (expectedRe, expectedIm) = expected
    val (actualRe, actualIm) = actual

    assertTrue(expectedRe.nearlyEquals(actualRe))
    assertTrue(expectedIm.nearlyEquals(actualIm))
}