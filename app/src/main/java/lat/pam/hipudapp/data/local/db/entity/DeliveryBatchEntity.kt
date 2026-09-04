package lat.pam.hipudapp.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "delivery_batches")
data class DeliveryBatchEntity(
    @PrimaryKey val id: Long,
    val label: String,
    val timeWindow: String,
    val quotaTotal: Int,
    val quotaRemaining: Int,
)
