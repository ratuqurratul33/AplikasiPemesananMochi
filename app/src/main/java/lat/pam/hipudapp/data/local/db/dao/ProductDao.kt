package lat.pam.hipudapp.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import lat.pam.hipudapp.data.local.db.entity.ProductEntity
import lat.pam.hipudapp.data.local.db.entity.ProductWithVariants
import lat.pam.hipudapp.data.local.db.entity.VariantGroupEntity
import lat.pam.hipudapp.data.local.db.entity.VariantOptionEntity

@Dao
interface ProductDao {
    @Transaction
    @Query("SELECT * FROM products ORDER BY name ASC")
    fun observeProductsWithVariants(): Flow<List<ProductWithVariants>>

    @Transaction
    @Query("SELECT * FROM products WHERE id = :productId LIMIT 1")
    suspend fun getProductWithVariants(productId: Long): ProductWithVariants?

    @Query("SELECT COUNT(*) FROM products")
    suspend fun countProducts(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVariantGroups(groups: List<VariantGroupEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVariantOptions(options: List<VariantOptionEntity>)
}
