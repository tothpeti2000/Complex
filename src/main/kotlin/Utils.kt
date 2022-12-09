import java.text.DecimalFormat

fun Double.toPrettyString(maxFractionDigits: Int = 4): String {
    require(maxFractionDigits >= 0) { "The number of fraction digits must not be negative" }

    val pattern = when(maxFractionDigits) {
        0 -> "#"
        else -> "#.${"#".repeat(maxFractionDigits)}"
    }

    val formatter = DecimalFormat(pattern)

    return formatter.format(this)
}