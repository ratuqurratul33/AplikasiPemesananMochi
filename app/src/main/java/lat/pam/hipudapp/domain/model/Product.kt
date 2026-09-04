package lat.pam.hipudapp.domain.model

data class Product(
    val id: Long,
    val name: String,
    val description: String,
    val price: Int,
    val imageRes: Int,
    val variantGroups: List<VariantGroup> = emptyList(),
)
