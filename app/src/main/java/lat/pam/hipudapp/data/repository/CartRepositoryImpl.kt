package lat.pam.hipudapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import lat.pam.hipudapp.domain.model.CartItem
import lat.pam.hipudapp.domain.model.Product
import lat.pam.hipudapp.domain.model.VariantOption
import lat.pam.hipudapp.domain.repository.CartRepository
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/** In-memory cart, scoped to the process lifetime — intentionally not persisted to Room. */
@Singleton
class CartRepositoryImpl @Inject constructor() : CartRepository {

    private val cartItems = MutableStateFlow<List<CartItem>>(emptyList())

    override fun observeCart(): Flow<List<CartItem>> = cartItems.asStateFlow()

    override suspend fun addItem(product: Product, quantity: Int, selectedOptions: List<VariantOption>) {
        cartItems.update { current ->
            val optionIds = selectedOptions.map { it.id }.toSet()
            val existingIndex = current.indexOfFirst {
                it.product.id == product.id && it.selectedOptions.map { option -> option.id }.toSet() == optionIds
            }
            if (existingIndex >= 0) {
                current.toMutableList().apply {
                    val existing = this[existingIndex]
                    this[existingIndex] = existing.copy(quantity = existing.quantity + quantity)
                }
            } else {
                current + CartItem(
                    id = UUID.randomUUID().toString(),
                    product = product,
                    quantity = quantity,
                    selectedOptions = selectedOptions,
                )
            }
        }
    }

    override suspend fun updateQuantity(cartItemId: String, quantity: Int) {
        cartItems.update { current ->
            if (quantity <= 0) {
                current.filterNot { it.id == cartItemId }
            } else {
                current.map { if (it.id == cartItemId) it.copy(quantity = quantity) else it }
            }
        }
    }

    override suspend fun removeItem(cartItemId: String) {
        cartItems.update { current -> current.filterNot { it.id == cartItemId } }
    }

    override suspend fun clear() {
        cartItems.value = emptyList()
    }
}
