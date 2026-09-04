package lat.pam.hipudapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import lat.pam.hipudapp.data.local.db.dao.ProductDao
import lat.pam.hipudapp.data.local.db.entity.ProductWithVariants
import lat.pam.hipudapp.domain.model.Product
import lat.pam.hipudapp.domain.model.VariantGroup
import lat.pam.hipudapp.domain.model.VariantOption
import lat.pam.hipudapp.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productDao: ProductDao,
) : ProductRepository {

    override fun observeProducts(): Flow<List<Product>> =
        productDao.observeProductsWithVariants().map { list -> list.map { it.toDomain() } }

    override suspend fun getProduct(productId: Long): Product? =
        productDao.getProductWithVariants(productId)?.toDomain()

    private fun ProductWithVariants.toDomain(): Product = Product(
        id = product.id,
        name = product.name,
        description = product.description,
        price = product.price,
        imageRes = product.imageRes,
        variantGroups = variantGroups.map { groupWithOptions ->
            VariantGroup(
                id = groupWithOptions.group.id,
                productId = groupWithOptions.group.productId,
                title = groupWithOptions.group.title,
                options = groupWithOptions.options.map { option ->
                    VariantOption(id = option.id, groupId = option.groupId, label = option.label)
                },
            )
        },
    )
}
