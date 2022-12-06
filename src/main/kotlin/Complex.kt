import kotlin.math.abs
import kotlin.math.sqrt

data class Complex(val re: Double, val im: Double) {
    constructor(re: Number = 0, im: Number = 0) : this(re.toDouble(), im.toDouble())

    override fun toString(): String {
        return when {
            im >= 0 -> "$re + ${im}i"
            else -> "$re - ${abs(im)}i"
        }
    }
}

val Complex.conjugate: Complex
    get() = Complex(re, -im)

val Complex.r: Double
    get() = sqrt(re * re + im * im)

operator fun Complex.unaryMinus() = Complex(-re, -im)

operator fun Complex.plus(other: Complex) = Complex(re + other.re, im + other.im)
operator fun Complex.plus(num: Number) = Complex(re + num.toDouble(), im)

operator fun Complex.minus(other: Complex) = Complex(re - other.re, im - other.im)
operator fun Complex.minus(num: Number) = Complex(re - num.toDouble(), im)

operator fun Complex.times(other: Complex) = Complex(re * other.re - im * other.im, re * other.im + im * other.re)
operator fun Complex.times(num: Number) = Complex(num.toDouble() * re, num.toDouble() * im)

operator fun Complex.div(other: Complex) = (this * other.conjugate) / (other.r * other.r)
operator fun Complex.div(num: Number) = Complex(re / num.toDouble(), im / num.toDouble())


fun Number.toComplex(): Complex = Complex(this)
operator fun Number.plus(c: Complex) = c + this
operator fun Number.minus(c: Complex) = c - this
operator fun Number.times(c: Complex) = c * this
operator fun Number.div(c: Complex) = this.toComplex() / c

val zero: Complex = 0.0.toComplex()
val one: Complex = 1.0.toComplex()
val i: Complex by lazy { Complex(0.0, 1.0) }