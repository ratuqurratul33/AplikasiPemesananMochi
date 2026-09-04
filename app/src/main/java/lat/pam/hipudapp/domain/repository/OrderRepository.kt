package lat.pam.hipudapp.domain.repository

import lat.pam.hipudapp.core.result.AppResult
import lat.pam.hipudapp.domain.model.CartItem
import lat.pam.hipudapp.domain.model.DeliveryBatch

interface OrderRepository {
    suspend fun placeOrder(
        userId: Long,
        items: List<CartItem>,
        addressId: Long,
        batch: DeliveryBatch,
    ): AppResult<Long>
}
