package lat.pam.hipudapp.domain.usecase

import lat.pam.hipudapp.core.result.AppError
import lat.pam.hipudapp.core.result.AppResult
import lat.pam.hipudapp.domain.model.Address
import lat.pam.hipudapp.domain.repository.AddressRepository
import javax.inject.Inject

class SaveAddressUseCase @Inject constructor(
    private val addressRepository: AddressRepository,
) {
    suspend operator fun invoke(address: Address): AppResult<Address> {
        if (address.recipientName.isBlank() || address.fullAddress.isBlank() || address.landmark.isBlank()) {
            return AppResult.Error(AppError.InvalidAddress)
        }
        return addressRepository.saveAddress(
            address.copy(
                recipientName = address.recipientName.trim(),
                fullAddress = address.fullAddress.trim(),
                landmark = address.landmark.trim(),
            )
        )
    }
}
