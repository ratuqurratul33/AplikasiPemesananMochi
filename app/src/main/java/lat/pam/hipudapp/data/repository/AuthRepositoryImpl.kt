package lat.pam.hipudapp.data.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import lat.pam.hipudapp.core.result.AppError
import lat.pam.hipudapp.core.result.AppResult
import lat.pam.hipudapp.core.result.safeCall
import lat.pam.hipudapp.core.security.PasswordHasher
import lat.pam.hipudapp.data.local.datastore.UserSessionDataStore
import lat.pam.hipudapp.data.local.db.dao.UserDao
import lat.pam.hipudapp.data.local.db.entity.UserEntity
import lat.pam.hipudapp.domain.model.User
import lat.pam.hipudapp.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val sessionDataStore: UserSessionDataStore,
) : AuthRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observeCurrentUser(): Flow<User?> =
        sessionDataStore.currentUserId.flatMapLatest { userId ->
            if (userId == null) flowOf(null) else userDao.observeById(userId).map { it?.toDomain() }
        }

    override suspend fun register(username: String, password: String, fullName: String): AppResult<User> {
        if (userDao.findByUsername(username) != null) return AppResult.Error(AppError.UsernameTaken)
        return safeCall {
            val salt = PasswordHasher.generateSalt()
            val hash = PasswordHasher.hash(password, salt)
            val id = userDao.insert(
                UserEntity(username = username, passwordHash = hash, passwordSalt = salt, fullName = fullName)
            )
            sessionDataStore.setCurrentUserId(id)
            User(id = id, username = username, fullName = fullName)
        }
    }

    override suspend fun login(username: String, password: String): AppResult<User> {
        val entity = userDao.findByUsername(username) ?: return AppResult.Error(AppError.AccountNotFound)
        if (!PasswordHasher.matches(password, entity.passwordSalt, entity.passwordHash)) {
            return AppResult.Error(AppError.InvalidCredentials)
        }
        return safeCall {
            sessionDataStore.setCurrentUserId(entity.id)
            entity.toDomain()
        }
    }

    override suspend fun logout() {
        sessionDataStore.setCurrentUserId(null)
    }

    private fun UserEntity.toDomain() = User(id = id, username = username, fullName = fullName)
}
