package lat.pam.hipudapp.presentation.productdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lat.pam.hipudapp.core.designsystem.component.HipudPrimaryButton
import lat.pam.hipudapp.core.designsystem.component.HipudTopBar
import lat.pam.hipudapp.core.designsystem.component.QuantityStepper
import lat.pam.hipudapp.core.designsystem.component.VariantChipGroup
import lat.pam.hipudapp.core.navigation.HipudDestination
import lat.pam.hipudapp.core.util.toRupiah
import lat.pam.hipudapp.domain.model.Product
import lat.pam.hipudapp.domain.model.VariantOption
import lat.pam.hipudapp.domain.repository.CartRepository
import lat.pam.hipudapp.domain.repository.ProductRepository
import javax.inject.Inject

data class ProductDetailUiState(
    val product: Product? = null,
    val selectedOptionsByGroup: Map<Long, VariantOption> = emptyMap(),
    val quantity: Int = 1,
    val isLoading: Boolean = true,
    val addedToCart: Boolean = false,
)

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {

    private val productId: Long = checkNotNull(savedStateHandle[HipudDestination.ProductDetail.ARG_PRODUCT_ID])

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val product = productRepository.getProduct(productId)
            val defaultSelections = product?.variantGroups
                ?.associate { group -> group.id to group.options.first() }
                .orEmpty()
            _uiState.update { it.copy(product = product, selectedOptionsByGroup = defaultSelections, isLoading = false) }
        }
    }

    fun onOptionSelected(groupId: Long, option: VariantOption) {
        _uiState.update { it.copy(selectedOptionsByGroup = it.selectedOptionsByGroup + (groupId to option)) }
    }

    fun onQuantityChange(quantity: Int) {
        _uiState.update { it.copy(quantity = quantity) }
    }

    fun addToCart() {
        val state = _uiState.value
        val product = state.product ?: return
        viewModelScope.launch {
            cartRepository.addItem(product, state.quantity, state.selectedOptionsByGroup.values.toList())
            _uiState.update { it.copy(addedToCart = true) }
        }
    }

    fun consumeAddedToCartEvent() {
        _uiState.update { it.copy(addedToCart = false) }
    }
}

@Composable
fun ProductDetailRoute(
    onBackClick: () -> Unit,
    viewModel: ProductDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.addedToCart) {
        if (uiState.addedToCart) {
            snackbarHostState.showSnackbar("${uiState.product?.name} ditambahkan ke keranjang")
            viewModel.consumeAddedToCartEvent()
        }
    }

    ProductDetailScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onBackClick = onBackClick,
        onOptionSelected = viewModel::onOptionSelected,
        onQuantityChange = viewModel::onQuantityChange,
        onAddToCartClick = viewModel::addToCart,
    )
}

@Composable
private fun ProductDetailScreen(
    uiState: ProductDetailUiState,
    snackbarHostState: SnackbarHostState,
    onBackClick: () -> Unit,
    onOptionSelected: (Long, VariantOption) -> Unit,
    onQuantityChange: (Int) -> Unit,
    onAddToCartClick: () -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { HipudTopBar(title = uiState.product?.name.orEmpty(), onBackClick = onBackClick) },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { paddingValues ->
        if (uiState.isLoading || uiState.product == null) {
            Box(Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
            return@Scaffold
        }

        val product = uiState.product
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Image(
                painter = painterResource(product.imageRes),
                contentDescription = product.name,
                modifier = Modifier.fillMaxWidth().height(220.dp).clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.Crop,
            )

            Column {
                Text(product.name, style = MaterialTheme.typography.headlineSmall)
                Text(
                    product.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    product.price.toRupiah(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            product.variantGroups.forEach { group ->
                VariantChipGroup(
                    title = group.title,
                    options = group.options,
                    selectedOptionId = uiState.selectedOptionsByGroup[group.id]?.id,
                    onOptionSelected = { option -> onOptionSelected(group.id, option) },
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("Jumlah", style = MaterialTheme.typography.titleSmall)
                QuantityStepper(quantity = uiState.quantity, onQuantityChange = onQuantityChange)
            }

            HipudPrimaryButton(text = "Tambah ke Keranjang", onClick = onAddToCartClick)
        }
    }
}
