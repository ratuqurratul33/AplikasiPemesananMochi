package lat.pam.hipudapp.domain.repository

import lat.pam.hipudapp.core.result.AppResult
import lat.pam.hipudapp.domain.model.Address

interface AddressRepository {
    suspend fun getLastAddress(userId: Long): Address?
    suspend fun saveAddress(address: Address): AppResult<Address>
}
