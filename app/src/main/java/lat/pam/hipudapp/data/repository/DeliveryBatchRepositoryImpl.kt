package lat.pam.hipudapp.data.repository

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import lat.pam.hipudapp.core.result.AppError
import lat.pam.hipudapp.core.result.AppResult
import lat.pam.hipudapp.data.local.db.dao.DeliveryBatchDao
import lat.pam.hipudapp.data.local.db.entity.DeliveryBatchEntity
import lat.pam.hipudapp.domain.model.DeliveryBatch
import lat.pam.hipudapp.domain.repository.DeliveryBatchRepository
import javax.inject.Inject

class DeliveryBatchRepositoryImpl @Inject constructor(
    private val batchDao: DeliveryBatchDao,
) : DeliveryBatchRepository {

    override fun observeBatches(): Flow<List<DeliveryBatch>> =
        batchDao.observeBatches().map { list -> list.map { it.toDomain() } }

    override suspend fun getBatch(batchId: Long): DeliveryBatch? = batchDao.getBatch(batchId)?.toDomain()

    override suspend fun decrementQuota(batchId: Long): AppResult<Unit> = try {
        val rowsAffected = batchDao.decrementQuota(batchId)
        if (rowsAffected > 0) AppResult.Success(Unit) else AppResult.Error(AppError.BatchFull)
    } catch (c: CancellationException) {
        throw c
    } catch (t: Throwable) {
        AppResult.Error(AppError.Unknown(t))
    }

    private fun DeliveryBatchEntity.toDomain() = DeliveryBatch(
        id = id,
        label = label,
        timeWindow = timeWindow,
        quotaTotal = quotaTotal,
        quotaRemaining = quotaRemaining,
    )
}
