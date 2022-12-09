import kotlin.math.*

data class Complex(val re: Double, val im: Double) {
    constructor(re: Number = 0.0, im: Number = 0.0) : this(re.toDouble(), im.toDouble())

    override fun toString(): String = toAlgebraicForm()
}

//region Extension properties

val Complex.conjugate: Complex
    get() = Complex(re, -im)

val Complex.r: Double
    get() = sqrt(re * re + im * im)

val Complex.theta: Double
    get() = atan2(im, re)

//endregion

//region Arithmetic operators

operator fun Complex.unaryMinus() = Complex(-re, -im)

operator fun Complex.plus(other: Complex) = Complex(re + other.re, im + other.im)
operator fun Complex.plus(num: Number) = Complex(re + num.toDouble(), im)
operator fun Number.plus(c: Complex) = c + this

operator fun Complex.minus(other: Complex) = Complex(re - other.re, im - other.im)
operator fun Complex.minus(num: Number) = Complex(re - num.toDouble(), im)
operator fun Number.minus(c: Complex) = -c + this

operator fun Complex.times(other: Complex) = Complex(re * other.re - im * other.im, re * other.im + im * other.re)
operator fun Complex.times(num: Number) = Complex(num.toDouble() * re, num.toDouble() * im)
operator fun Number.times(c: Complex) = c * this

operator fun Complex.div(other: Complex): Complex {
    require(other.r != 0.0) { "The absolute value of the divisor complex number must not be 0" }

    return (this * other.conjugate) / other.r.pow(2)
}

operator fun Complex.div(num: Number): Complex {
    require(num.toDouble() != 0.0) { "You can't divide a complex number by 0" }

    return Complex(re / num.toDouble(), im / num.toDouble())
}

fun Number.toComplex() = Complex(this)
operator fun Number.div(c: Complex) = this.toComplex() / c

//endregion

//region Powers

fun Complex.pow(n: Number): Complex = r.pow(n.toDouble()) * (cos(n.toDouble() * theta) + sin(n.toDouble() * theta) * i)
fun Complex.square() = pow(2)
fun Complex.cube() = pow(3)
fun exp(c: Complex): Complex = exp(c.re) * (cos(c.im) + sin(c.im) * i)

//endregion

//region Roots

fun Complex.root(n: Int): Complex {
    require(n > 0) { "The root must be positive" }

    return pow(1.0 / n)
}
fun Complex.sqrt() = root(2)
fun Complex.cbrt() = root(3)

//endregion

//region Display forms

fun Complex.toAlgebraicForm(maxFractionDigits: Int = 4): String {
    require(maxFractionDigits >= 0) { "The number of fraction digits must not be negative" }

    val reDisplayString = re.toPrettyString(maxFractionDigits)
    val imDisplayString = im.toPrettyString(maxFractionDigits)
    val absImDisplayString = abs(im).toPrettyString(maxFractionDigits)

    return when {
        im >= 0 -> "$reDisplayString + ${imDisplayString}i"
        else -> "$reDisplayString - ${absImDisplayString}i"
    }
}

fun Complex.toTrigonometricForm(maxFractionDigits: Int = 4): String {
    require(maxFractionDigits >= 0) { "The number of fraction digits must not be negative" }

    val rDisplayString = r.toPrettyString(maxFractionDigits)
    val thetaDisplayString = theta.toPrettyString(maxFractionDigits)

    return "$rDisplayString (cos($thetaDisplayString) + sin($thetaDisplayString)i)"
}

fun Complex.toExponentialForm(maxFractionDigits: Int = 4): String {
    require(maxFractionDigits >= 0) { "The number of fraction digits must not be negative" }

    val rDisplayString = r.toPrettyString(maxFractionDigits)
    val thetaDisplayString = theta.toPrettyString(maxFractionDigits)

    return "$rDisplayString e^${thetaDisplayString}i"
}

//endregion

val i: Complex by lazy { Complex(0.0, 1.0) }