package lat.pam.hipudapp.domain.usecase

import lat.pam.hipudapp.domain.repository.AuthRepository
import lat.pam.hipudapp.domain.repository.CartRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val cartRepository: CartRepository,
) {
    suspend operator fun invoke() {
        cartRepository.clear()
        authRepository.logout()
    }
}
