package lat.pam.hipudapp.domain.repository

import kotlinx.coroutines.flow.Flow
import lat.pam.hipudapp.core.result.AppResult
import lat.pam.hipudapp.domain.model.DeliveryBatch

interface DeliveryBatchRepository {
    fun observeBatches(): Flow<List<DeliveryBatch>>
    suspend fun getBatch(batchId: Long): DeliveryBatch?
    suspend fun decrementQuota(batchId: Long): AppResult<Unit>
}
