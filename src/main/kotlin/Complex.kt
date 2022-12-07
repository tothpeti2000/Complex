import kotlin.math.*

data class Complex(val re: Double, val im: Double) {
    constructor(re: Number = 0.0, im: Number = 0.0) : this(re.toDouble(), im.toDouble())

    override fun toString(): String {
        return when {
            re.nearlyEquals(0.0) && im.nearlyEquals(0.0) -> 0.0.toString()
            re.nearlyEquals(0.0) -> "${im}i"
            im.nearlyEquals(0.0) -> re.toString()
            im >= 0 -> "$re + ${im}i"
            else -> "$re - ${abs(im)}i"
        }
    }
}

val Complex.conjugate: Complex
    get() = Complex(re, -im)

val Complex.r: Double
    get() = sqrt(re * re + im * im)

val Complex.quadrant: Quadrant
    get() = when {
        re > 0 && im > 0 -> Quadrant.First
        re < 0 && im > 0 -> Quadrant.Second
        re < 0 && im < 0 -> Quadrant.Third
        re > 0 && im < 0 -> Quadrant.Fourth
        else -> Quadrant.ON_AXIS
    }

val Complex.theta: Double
    get() = atan2(im, re)

operator fun Complex.unaryMinus() = Complex(-re, -im)

operator fun Complex.plus(other: Complex) = Complex(re + other.re, im + other.im)
operator fun Complex.plus(num: Number) = Complex(re + num.toDouble(), im)

operator fun Complex.minus(other: Complex) = Complex(re - other.re, im - other.im)
operator fun Complex.minus(num: Number) = Complex(re - num.toDouble(), im)

operator fun Complex.times(other: Complex) = Complex(re * other.re - im * other.im, re * other.im + im * other.re)
operator fun Complex.times(num: Number) = Complex(num.toDouble() * re, num.toDouble() * im)

operator fun Complex.div(other: Complex): Complex {
    require(other.r > 0) { "The absolute value of the divisor complex number must not be 0" }
    return (this * other.conjugate) / other.r.pow(2)
}
operator fun Complex.div(num: Number): Complex {
    require(num != 0) { "You can't divide a complex number by 0" }
    return Complex(re / num.toDouble(), im / num.toDouble())
}

fun Number.toComplex() = Complex(this)
operator fun Number.plus(c: Complex) = c + this
operator fun Number.minus(c: Complex) = -c + this
operator fun Number.times(c: Complex) = c * this
operator fun Number.div(c: Complex) = this.toComplex() / c

fun Complex.pow(n: Int): Complex = r.pow(n) * (cos(n * theta) + sin(n * theta) * i)
fun Complex.root(n: Int): Complex {
    require(n > 0) { "The root value must be positive" }
    return r.pow(1.0 / n) * (cos(theta / n) + sin(theta / n) * i)
}

fun Complex.toTrigonometricForm() = "$r (cos($theta) + sin($theta)i)"
fun Complex.toExponentialForm() = "$r e^${theta}i"

val i: Complex by lazy { Complex(0.0, 1.0) }

fun exp(c: Complex): Complex = exp(c.re) * (cos(c.im) + sin(c.im) * i)