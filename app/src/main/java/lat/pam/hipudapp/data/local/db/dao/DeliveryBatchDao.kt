package lat.pam.hipudapp.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import lat.pam.hipudapp.data.local.db.entity.DeliveryBatchEntity

@Dao
interface DeliveryBatchDao {
    @Query("SELECT * FROM delivery_batches ORDER BY id ASC")
    fun observeBatches(): Flow<List<DeliveryBatchEntity>>

    @Query("SELECT * FROM delivery_batches WHERE id = :batchId LIMIT 1")
    suspend fun getBatch(batchId: Long): DeliveryBatchEntity?

    @Query("SELECT COUNT(*) FROM delivery_batches")
    suspend fun countBatches(): Int

    /** Atomic, race-safe: only decrements (and returns 1 affected row) if quota is still available. */
    @Query("UPDATE delivery_batches SET quotaRemaining = quotaRemaining - 1 WHERE id = :batchId AND quotaRemaining > 0")
    suspend fun decrementQuota(batchId: Long): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBatches(batches: List<DeliveryBatchEntity>)
}
