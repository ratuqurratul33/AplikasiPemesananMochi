package lat.pam.hipudapp.data.local.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class VariantGroupWithOptions(
    @Embedded val group: VariantGroupEntity,
    @Relation(parentColumn = "id", entityColumn = "groupId")
    val options: List<VariantOptionEntity>,
)

data class ProductWithVariants(
    @Embedded val product: ProductEntity,
    @Relation(entity = VariantGroupEntity::class, parentColumn = "id", entityColumn = "productId")
    val variantGroups: List<VariantGroupWithOptions>,
)
