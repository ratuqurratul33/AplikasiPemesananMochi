package lat.pam.hipudapp.data.local.db

import lat.pam.hipudapp.R
import lat.pam.hipudapp.data.local.db.entity.DeliveryBatchEntity
import lat.pam.hipudapp.data.local.db.entity.ProductEntity
import lat.pam.hipudapp.data.local.db.entity.VariantGroupEntity
import lat.pam.hipudapp.data.local.db.entity.VariantOptionEntity

/** Initial catalog seeded once when the local database is first created. */
object SeedData {

    fun products(): List<ProductEntity> = listOf(
        ProductEntity(1, "Mochi Strawberry", "Isian strawberry segar", 15_000, R.drawable.mochi_strawberry),
        ProductEntity(2, "Mochi Choco", "Coklat lumer lembut", 14_000, R.drawable.mochi_choco),
        ProductEntity(3, "Mochi Greentea", "Greentea wangi khas", 15_000, R.drawable.mochi_greantea),
        ProductEntity(4, "Mochi Mangga", "Mangga manis segar", 15_000, R.drawable.mochi_mangga),
        ProductEntity(5, "Mochi Red Velvet", "Red velvet creamy", 16_000, R.drawable.mochi_redvelvet),
        ProductEntity(6, "Mochi Matcha", "Matcha premium Jepang", 18_000, R.drawable.mochi_matcha),
        ProductEntity(7, "Mochi Kacang Choco", "Kacang & coklat crunchy", 14_000, R.drawable.mochi_kacangchoco),
        ProductEntity(8, "Mochi Berry", "Campuran berry segar", 16_000, R.drawable.mochi_berry),
        ProductEntity(9, "Mochi Blueberry", "Blueberry manis asam", 15_000, R.drawable.mochi_bluberry),
        ProductEntity(10, "Mochi Oreo", "Oreo crumble favorit", 17_000, R.drawable.mochi_oreo),
    )

    fun variantGroups(): List<VariantGroupEntity> = products().flatMap { product ->
        listOf(
            VariantGroupEntity(id = product.id * 10 + 1, productId = product.id, title = "Ketebalan Kulit Mochi"),
            VariantGroupEntity(id = product.id * 10 + 2, productId = product.id, title = "Tekstur Isian"),
        )
    }

    fun variantOptions(): List<VariantOptionEntity> = products().flatMap { product ->
        val kulitGroupId = product.id * 10 + 1
        val isianGroupId = product.id * 10 + 2
        listOf(
            VariantOptionEntity(id = kulitGroupId * 10 + 1, groupId = kulitGroupId, label = "Tipis"),
            VariantOptionEntity(id = kulitGroupId * 10 + 2, groupId = kulitGroupId, label = "Normal"),
            VariantOptionEntity(id = kulitGroupId * 10 + 3, groupId = kulitGroupId, label = "Tebal"),
            VariantOptionEntity(id = isianGroupId * 10 + 1, groupId = isianGroupId, label = "Lembut"),
            VariantOptionEntity(id = isianGroupId * 10 + 2, groupId = isianGroupId, label = "Creamy"),
            VariantOptionEntity(id = isianGroupId * 10 + 3, groupId = isianGroupId, label = "Crunchy"),
        )
    }

    fun deliveryBatches(): List<DeliveryBatchEntity> = listOf(
        DeliveryBatchEntity(id = 1, label = "Batch Pagi", timeWindow = "08.00 - 10.00", quotaTotal = 30, quotaRemaining = 30),
        DeliveryBatchEntity(id = 2, label = "Batch Sore", timeWindow = "16.00 - 18.00", quotaTotal = 30, quotaRemaining = 18),
    )
}
