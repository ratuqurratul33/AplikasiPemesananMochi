package lat.pam.hipudapp.data.repository

import lat.pam.hipudapp.core.result.AppResult
import lat.pam.hipudapp.core.result.safeCall
import lat.pam.hipudapp.data.local.db.dao.AddressDao
import lat.pam.hipudapp.data.local.db.entity.AddressEntity
import lat.pam.hipudapp.domain.model.Address
import lat.pam.hipudapp.domain.repository.AddressRepository
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(
    private val addressDao: AddressDao,
) : AddressRepository {

    override suspend fun getLastAddress(userId: Long): Address? =
        addressDao.getLastAddress(userId)?.toDomain()

    override suspend fun saveAddress(address: Address): AppResult<Address> = safeCall {
        val id = addressDao.upsert(
            AddressEntity(
                userId = address.userId,
                recipientName = address.recipientName,
                fullAddress = address.fullAddress,
                landmark = address.landmark,
                savedAtEpochMillis = System.currentTimeMillis(),
            )
        )
        address.copy(id = id)
    }

    private fun AddressEntity.toDomain() = Address(
        id = id,
        userId = userId,
        recipientName = recipientName,
        fullAddress = fullAddress,
        landmark = landmark,
    )
}
