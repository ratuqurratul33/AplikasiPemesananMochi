package lat.pam.hipudapp.domain.repository

import kotlinx.coroutines.flow.Flow
import lat.pam.hipudapp.domain.model.Product

interface ProductRepository {
    fun observeProducts(): Flow<List<Product>>
    suspend fun getProduct(productId: Long): Product?
}
