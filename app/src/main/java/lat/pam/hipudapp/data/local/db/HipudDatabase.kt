package lat.pam.hipudapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import lat.pam.hipudapp.data.local.db.dao.AddressDao
import lat.pam.hipudapp.data.local.db.dao.DeliveryBatchDao
import lat.pam.hipudapp.data.local.db.dao.OrderDao
import lat.pam.hipudapp.data.local.db.dao.ProductDao
import lat.pam.hipudapp.data.local.db.dao.UserDao
import lat.pam.hipudapp.data.local.db.entity.AddressEntity
import lat.pam.hipudapp.data.local.db.entity.DeliveryBatchEntity
import lat.pam.hipudapp.data.local.db.entity.OrderEntity
import lat.pam.hipudapp.data.local.db.entity.OrderItemEntity
import lat.pam.hipudapp.data.local.db.entity.ProductEntity
import lat.pam.hipudapp.data.local.db.entity.UserEntity
import lat.pam.hipudapp.data.local.db.entity.VariantGroupEntity
import lat.pam.hipudapp.data.local.db.entity.VariantOptionEntity

@Database(
    entities = [
        UserEntity::class,
        ProductEntity::class,
        VariantGroupEntity::class,
        VariantOptionEntity::class,
        DeliveryBatchEntity::class,
        AddressEntity::class,
        OrderEntity::class,
        OrderItemEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class HipudDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao
    abstract fun deliveryBatchDao(): DeliveryBatchDao
    abstract fun addressDao(): AddressDao
    abstract fun orderDao(): OrderDao
}

suspend fun HipudDatabase.seedIfEmpty() {
    val productDao = productDao()
    val batchDao = deliveryBatchDao()
    if (productDao.countProducts() == 0) {
        productDao.insertProducts(SeedData.products())
        productDao.insertVariantGroups(SeedData.variantGroups())
        productDao.insertVariantOptions(SeedData.variantOptions())
    }
    if (batchDao.countBatches() == 0) {
        batchDao.insertBatches(SeedData.deliveryBatches())
    }
}
