package lat.pam.hipudapp.domain.model

data class DeliveryBatch(
    val id: Long,
    val label: String,
    val timeWindow: String,
    val quotaTotal: Int,
    val quotaRemaining: Int,
) {
    val isFull: Boolean get() = quotaRemaining <= 0
}
