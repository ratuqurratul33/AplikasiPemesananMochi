package lat.pam.hipudapp.domain.usecase

import lat.pam.hipudapp.core.result.AppError
import lat.pam.hipudapp.core.result.AppResult
import lat.pam.hipudapp.domain.model.CartItem
import lat.pam.hipudapp.domain.model.DeliveryBatch
import lat.pam.hipudapp.domain.repository.CartRepository
import lat.pam.hipudapp.domain.repository.DeliveryBatchRepository
import lat.pam.hipudapp.domain.repository.OrderRepository
import javax.inject.Inject

/**
 * Orchestrates placing an order: validates the cart/batch, atomically decrements the
 * delivery-batch quota, persists the order, then clears the cart. If the quota decrement
 * fails (batch filled up in the meantime) the order is never created.
 */
class PlaceOrderUseCase @Inject constructor(
    private val cartRepository: CartRepository,
    private val deliveryBatchRepository: DeliveryBatchRepository,
    private val orderRepository: OrderRepository,
) {
    suspend operator fun invoke(
        userId: Long,
        items: List<CartItem>,
        addressId: Long,
        batch: DeliveryBatch?,
    ): AppResult<Long> {
        if (items.isEmpty()) return AppResult.Error(AppError.EmptyCart)
        if (batch == null) return AppResult.Error(AppError.NoBatchSelected)
        if (batch.isFull) return AppResult.Error(AppError.BatchFull)

        val quotaResult = deliveryBatchRepository.decrementQuota(batch.id)
        if (quotaResult is AppResult.Error) return quotaResult

        val orderResult = orderRepository.placeOrder(userId, items, addressId, batch)
        if (orderResult is AppResult.Success) {
            cartRepository.clear()
        }
        return orderResult
    }
}
