package lat.pam.hipudapp.domain.model

data class VariantGroup(
    val id: Long,
    val productId: Long,
    val title: String,
    val options: List<VariantOption>,
)

data class VariantOption(
    val id: Long,
    val groupId: Long,
    val label: String,
)
