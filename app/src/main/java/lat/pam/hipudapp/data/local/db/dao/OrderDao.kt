package lat.pam.hipudapp.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import lat.pam.hipudapp.data.local.db.entity.OrderEntity
import lat.pam.hipudapp.data.local.db.entity.OrderItemEntity

@Dao
interface OrderDao {
    @Insert
    suspend fun insertOrder(order: OrderEntity): Long

    @Insert
    suspend fun insertItems(items: List<OrderItemEntity>)
}
