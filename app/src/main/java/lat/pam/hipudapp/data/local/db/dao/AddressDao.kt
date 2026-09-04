package lat.pam.hipudapp.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import lat.pam.hipudapp.data.local.db.entity.AddressEntity

@Dao
interface AddressDao {
    @Query("SELECT * FROM addresses WHERE userId = :userId ORDER BY savedAtEpochMillis DESC LIMIT 1")
    suspend fun getLastAddress(userId: Long): AddressEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(address: AddressEntity): Long
}
