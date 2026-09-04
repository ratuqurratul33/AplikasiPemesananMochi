package lat.pam.hipudapp.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import lat.pam.hipudapp.core.designsystem.component.DeliveryBatchCard
import lat.pam.hipudapp.core.designsystem.component.ProductCard
import lat.pam.hipudapp.core.util.toRupiah
import lat.pam.hipudapp.domain.model.DeliveryBatch
import lat.pam.hipudapp.domain.model.Product
import lat.pam.hipudapp.domain.repository.AuthRepository
import lat.pam.hipudapp.domain.repository.DeliveryBatchRepository
import lat.pam.hipudapp.domain.repository.ProductRepository
import javax.inject.Inject

data class HomeUiState(
    val greetingName: String = "",
    val products: List<Product> = emptyList(),
    val nearestBatch: DeliveryBatch? = null,
    val isLoading: Boolean = true,
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    productRepository: ProductRepository,
    authRepository: AuthRepository,
    deliveryBatchRepository: DeliveryBatchRepository,
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = combine(
        authRepository.observeCurrentUser(),
        productRepository.observeProducts(),
        deliveryBatchRepository.observeBatches(),
    ) { user, products, batches ->
        HomeUiState(
            greetingName = user?.fullName.orEmpty(),
            products = products,
            nearestBatch = batches.firstOrNull { !it.isFull } ?: batches.firstOrNull(),
            isLoading = false,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), HomeUiState())
}

@Composable
fun HomeRoute(
    onProductClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(uiState = uiState, onProductClick = onProductClick, modifier = modifier)
}

@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    onProductClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (uiState.isLoading) {
        Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
        return
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            Text(
                text = "Halo, ${uiState.greetingName}",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
            )
        }

        uiState.nearestBatch?.let { batch ->
            item {
                DeliveryBatchCard(
                    label = "Jadwal pengiriman terdekat: ${batch.label}",
                    timeWindow = batch.timeWindow,
                    quotaRemaining = batch.quotaRemaining,
                    quotaTotal = batch.quotaTotal,
                    selected = false,
                    onClick = {},
                )
            }
        }

        item {
            Text(
                text = "Menu mochi",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        items(uiState.products, key = { it.id }) { product ->
            ProductCard(
                name = product.name,
                description = product.description,
                priceLabel = product.price.toRupiah(),
                imageRes = product.imageRes,
                onClick = { onProductClick(product.id) },
            )
        }
    }
}
