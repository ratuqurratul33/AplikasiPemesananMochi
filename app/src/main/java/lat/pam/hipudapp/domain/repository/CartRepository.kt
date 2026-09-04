package lat.pam.hipudapp.domain.repository

import kotlinx.coroutines.flow.Flow
import lat.pam.hipudapp.domain.model.CartItem
import lat.pam.hipudapp.domain.model.Product
import lat.pam.hipudapp.domain.model.VariantOption

interface CartRepository {
    fun observeCart(): Flow<List<CartItem>>
    suspend fun addItem(product: Product, quantity: Int, selectedOptions: List<VariantOption>)
    suspend fun updateQuantity(cartItemId: String, quantity: Int)
    suspend fun removeItem(cartItemId: String)
    suspend fun clear()
}
