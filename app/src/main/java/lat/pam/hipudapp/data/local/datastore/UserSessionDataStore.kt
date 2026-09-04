package lat.pam.hipudapp.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.sessionDataStore by preferencesDataStore(name = "user_session")

@Singleton
class UserSessionDataStore @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val keyUserId = longPreferencesKey("logged_in_user_id")

    val currentUserId: Flow<Long?> = context.sessionDataStore.data.map { it[keyUserId] }

    suspend fun setCurrentUserId(userId: Long?) {
        context.sessionDataStore.edit { prefs ->
            if (userId == null) prefs.remove(keyUserId) else prefs[keyUserId] = userId
        }
    }
}
