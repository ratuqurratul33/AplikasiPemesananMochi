package lat.pam.hipudapp.data.local.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "variant_options",
    foreignKeys = [
        ForeignKey(
            entity = VariantGroupEntity::class,
            parentColumns = ["id"],
            childColumns = ["groupId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("groupId")],
)
data class VariantOptionEntity(
    @PrimaryKey val id: Long,
    val groupId: Long,
    val label: String,
)
