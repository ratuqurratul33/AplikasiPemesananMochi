package lat.pam.hipudapp.data.repository

import lat.pam.hipudapp.core.result.AppResult
import lat.pam.hipudapp.core.result.safeCall
import lat.pam.hipudapp.data.local.db.dao.OrderDao
import lat.pam.hipudapp.data.local.db.entity.OrderEntity
import lat.pam.hipudapp.data.local.db.entity.OrderItemEntity
import lat.pam.hipudapp.domain.model.CartItem
import lat.pam.hipudapp.domain.model.DeliveryBatch
import lat.pam.hipudapp.domain.repository.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderDao: OrderDao,
) : OrderRepository {

    override suspend fun placeOrder(
        userId: Long,
        items: List<CartItem>,
        addressId: Long,
        batch: DeliveryBatch,
    ): AppResult<Long> = safeCall {
        val totalPrice = items.sumOf { it.subtotal }
        val orderId = orderDao.insertOrder(
            OrderEntity(
                userId = userId,
                addressId = addressId,
                batchId = batch.id,
                batchLabelSnapshot = batch.label,
                totalPrice = totalPrice,
                createdAtEpochMillis = System.currentTimeMillis(),
            )
        )
        orderDao.insertItems(
            items.map { item ->
                OrderItemEntity(
                    orderId = orderId,
                    productId = item.product.id,
                    productNameSnapshot = item.product.name,
                    unitPriceSnapshot = item.product.price,
                    quantity = item.quantity,
                    selectedOptionsSnapshot = item.selectedOptions.joinToString(", ") { it.label },
                )
            }
        )
        orderId
    }
}
