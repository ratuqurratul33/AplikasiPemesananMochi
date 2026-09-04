package lat.pam.hipudapp.domain.model

data class Address(
    val id: Long = 0,
    val userId: Long,
    val recipientName: String,
    val fullAddress: String,
    val landmark: String,
)
