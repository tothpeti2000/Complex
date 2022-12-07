import kotlin.math.abs

private const val DOUBLE_PRECISION = 1e-15
fun Double.nearlyEquals(other: Double) = abs(this - other) < DOUBLE_PRECISION