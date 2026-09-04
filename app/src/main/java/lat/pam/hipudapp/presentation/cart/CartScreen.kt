package lat.pam.hipudapp.presentation.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import lat.pam.hipudapp.core.designsystem.component.DeliveryBatchCard
import lat.pam.hipudapp.core.designsystem.component.HipudPrimaryButton
import lat.pam.hipudapp.core.designsystem.component.QuantityStepper
import lat.pam.hipudapp.core.util.toRupiah
import lat.pam.hipudapp.domain.model.CartItem
import lat.pam.hipudapp.domain.model.DeliveryBatch
import lat.pam.hipudapp.domain.repository.CartRepository
import lat.pam.hipudapp.domain.repository.DeliveryBatchRepository
import javax.inject.Inject

data class CartUiState(
    val items: List<CartItem> = emptyList(),
    val batches: List<DeliveryBatch> = emptyList(),
    val selectedBatchId: Long? = null,
    val isLoading: Boolean = true,
) {
    val totalPrice: Int get() = items.sumOf { it.subtotal }
}

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    deliveryBatchRepository: DeliveryBatchRepository,
) : ViewModel() {

    private val selectedBatchId = MutableStateFlow<Long?>(null)

    val uiState: StateFlow<CartUiState> = combine(
        cartRepository.observeCart(),
        deliveryBatchRepository.observeBatches(),
        selectedBatchId,
    ) { items, batches, selected ->
        CartUiState(items = items, batches = batches, selectedBatchId = selected, isLoading = false)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), CartUiState())

    fun onBatchSelected(batchId: Long) {
        selectedBatchId.value = batchId
    }

    fun onQuantityChange(cartItemId: String, quantity: Int) {
        viewModelScope.launch { cartRepository.updateQuantity(cartItemId, quantity) }
    }

    fun onRemoveItem(cartItemId: String) {
        viewModelScope.launch { cartRepository.removeItem(cartItemId) }
    }
}

@Composable
fun CartRoute(
    onProceedToAddress: (batchId: Long) -> Unit,
    onBrowseMenuClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    CartScreen(
        uiState = uiState,
        onBatchSelected = viewModel::onBatchSelected,
        onQuantityChange = viewModel::onQuantityChange,
        onRemoveItem = viewModel::onRemoveItem,
        onProceedToAddress = onProceedToAddress,
        onBrowseMenuClick = onBrowseMenuClick,
        modifier = modifier,
    )
}

@Composable
private fun CartScreen(
    uiState: CartUiState,
    onBatchSelected: (Long) -> Unit,
    onQuantityChange: (String, Int) -> Unit,
    onRemoveItem: (String) -> Unit,
    onProceedToAddress: (Long) -> Unit,
    onBrowseMenuClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (uiState.items.isEmpty()) {
        Box(modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Keranjang masih kosong", style = MaterialTheme.typography.titleMedium)
                Text(
                    "Yuk pilih mochi favoritmu dulu",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(Modifier.height(12.dp))
                HipudPrimaryButton(text = "Lihat Menu", onClick = onBrowseMenuClick)
            }
        }
        return
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            Text("Keranjang", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)
        }

        items(uiState.items, key = { it.id }) { item ->
            CartItemRow(
                item = item,
                onQuantityChange = { quantity -> onQuantityChange(item.id, quantity) },
                onRemove = { onRemoveItem(item.id) },
            )
        }

        item {
            Text("Pilih Jadwal Pengiriman", style = MaterialTheme.typography.titleMedium)
        }

        items(uiState.batches, key = { it.id }) { batch ->
            DeliveryBatchCard(
                label = batch.label,
                timeWindow = batch.timeWindow,
                quotaRemaining = batch.quotaRemaining,
                quotaTotal = batch.quotaTotal,
                selected = batch.id == uiState.selectedBatchId,
                onClick = { onBatchSelected(batch.id) },
            )
        }

        item {
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text("Total", style = MaterialTheme.typography.titleMedium)
                Text(
                    uiState.totalPrice.toRupiah(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
            HipudPrimaryButton(
                text = "Lanjut ke Alamat",
                enabled = uiState.selectedBatchId != null,
                onClick = { uiState.selectedBatchId?.let(onProceedToAddress) },
            )
        }
    }
}

@Composable
private fun CartItemRow(
    item: CartItem,
    onQuantityChange: (Int) -> Unit,
    onRemove: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(item.product.imageRes),
                contentDescription = item.product.name,
                modifier = Modifier.size(64.dp).clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
            )
            Column(modifier = Modifier.weight(1f).padding(horizontal = 12.dp)) {
                Text(item.product.name, style = MaterialTheme.typography.titleSmall)
                if (item.selectedOptions.isNotEmpty()) {
                    Text(
                        item.selectedOptions.joinToString(", ") { it.label },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                Text(
                    item.subtotal.toRupiah(),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                IconButton(onClick = onRemove) {
                    Icon(Icons.Default.Delete, contentDescription = "Hapus", tint = MaterialTheme.colorScheme.error)
                }
                QuantityStepper(quantity = item.quantity, onQuantityChange = onQuantityChange)
            }
        }
    }
}
