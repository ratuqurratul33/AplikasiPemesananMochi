package lat.pam.hipudapp.domain.model

data class CartItem(
    val id: String,
    val product: Product,
    val quantity: Int,
    val selectedOptions: List<VariantOption>,
) {
    val subtotal: Int get() = product.price * quantity
}
