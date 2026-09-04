package lat.pam.hipudapp.domain.usecase

import lat.pam.hipudapp.core.result.AppError
import lat.pam.hipudapp.core.result.AppResult
import lat.pam.hipudapp.domain.model.User
import lat.pam.hipudapp.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(username: String, password: String): AppResult<User> {
        if (username.isBlank() || password.isBlank()) {
            return AppResult.Error(AppError.EmptyCredentials)
        }
        return authRepository.login(username.trim(), password)
    }
}
