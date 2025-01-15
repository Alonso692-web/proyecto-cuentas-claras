data class SavingsEntry(
    val amount: Double,
    val date: Long = System.currentTimeMillis(),
    val note: String = ""
)