package lat.pam.hipudapp.domain.model

data class Order(
    val id: Long,
    val userId: Long,
    val items: List<CartItem>,
    val address: Address,
    val batch: DeliveryBatch,
    val totalPrice: Int,
    val createdAtEpochMillis: Long,
)
