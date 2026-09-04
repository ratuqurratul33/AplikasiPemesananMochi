package lat.pam.hipudapp.domain.repository

import kotlinx.coroutines.flow.Flow
import lat.pam.hipudapp.core.result.AppResult
import lat.pam.hipudapp.domain.model.User

interface AuthRepository {
    fun observeCurrentUser(): Flow<User?>
    suspend fun register(username: String, password: String, fullName: String): AppResult<User>
    suspend fun login(username: String, password: String): AppResult<User>
    suspend fun logout()
}
