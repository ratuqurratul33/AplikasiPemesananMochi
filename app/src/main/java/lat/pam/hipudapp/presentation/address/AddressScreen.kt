package lat.pam.hipudapp.presentation.address

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lat.pam.hipudapp.core.designsystem.component.HipudPrimaryButton
import lat.pam.hipudapp.core.designsystem.component.HipudTextField
import lat.pam.hipudapp.core.designsystem.component.HipudTopBar
import lat.pam.hipudapp.core.navigation.HipudDestination
import lat.pam.hipudapp.core.result.AppError
import lat.pam.hipudapp.core.result.AppResult
import lat.pam.hipudapp.domain.model.Address
import lat.pam.hipudapp.domain.repository.AddressRepository
import lat.pam.hipudapp.domain.repository.AuthRepository
import lat.pam.hipudapp.domain.repository.CartRepository
import lat.pam.hipudapp.domain.repository.DeliveryBatchRepository
import lat.pam.hipudapp.domain.usecase.PlaceOrderUseCase
import lat.pam.hipudapp.domain.usecase.SaveAddressUseCase
import javax.inject.Inject

data class AddressUiState(
    val recipientName: String = "",
    val fullAddress: String = "",
    val landmark: String = "",
    val isLoading: Boolean = true,
    val isSubmitting: Boolean = false,
    val errorMessage: String? = null,
    val orderPlaced: Boolean = false,
)

@HiltViewModel
class AddressViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val addressRepository: AddressRepository,
    private val saveAddressUseCase: SaveAddressUseCase,
    private val placeOrderUseCase: PlaceOrderUseCase,
    private val authRepository: AuthRepository,
    private val cartRepository: CartRepository,
    private val deliveryBatchRepository: DeliveryBatchRepository,
) : ViewModel() {

    private val batchId: Long = checkNotNull(savedStateHandle[HipudDestination.Address.ARG_BATCH_ID])

    private val _uiState = MutableStateFlow(AddressUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val user = authRepository.observeCurrentUser().first()
            val lastAddress = user?.let { addressRepository.getLastAddress(it.id) }
            _uiState.update {
                it.copy(
                    recipientName = lastAddress?.recipientName ?: user?.fullName.orEmpty(),
                    fullAddress = lastAddress?.fullAddress.orEmpty(),
                    landmark = lastAddress?.landmark.orEmpty(),
                    isLoading = false,
                )
            }
        }
    }

    fun onRecipientNameChange(value: String) = _uiState.update { it.copy(recipientName = value, errorMessage = null) }
    fun onFullAddressChange(value: String) = _uiState.update { it.copy(fullAddress = value, errorMessage = null) }
    fun onLandmarkChange(value: String) = _uiState.update { it.copy(landmark = value, errorMessage = null) }

    fun submitOrder() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true, errorMessage = null) }

            val user = authRepository.observeCurrentUser().first()
            if (user == null) {
                _uiState.update { it.copy(isSubmitting = false, errorMessage = AppError.NotLoggedIn.message) }
                return@launch
            }

            val state = _uiState.value
            val addressResult = saveAddressUseCase(
                Address(
                    userId = user.id,
                    recipientName = state.recipientName,
                    fullAddress = state.fullAddress,
                    landmark = state.landmark,
                )
            )
            if (addressResult is AppResult.Error) {
                _uiState.update { it.copy(isSubmitting = false, errorMessage = addressResult.error.message) }
                return@launch
            }
            val savedAddress = (addressResult as AppResult.Success).data

            val cartItems = cartRepository.observeCart().first()
            val batch = deliveryBatchRepository.getBatch(batchId)
            when (val orderResult = placeOrderUseCase(user.id, cartItems, savedAddress.id, batch)) {
                is AppResult.Success -> _uiState.update { it.copy(isSubmitting = false, orderPlaced = true) }
                is AppResult.Error -> _uiState.update { it.copy(isSubmitting = false, errorMessage = orderResult.error.message) }
            }
        }
    }
}

@Composable
fun AddressRoute(
    onOrderPlaced: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: AddressViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.orderPlaced) {
        if (uiState.orderPlaced) onOrderPlaced()
    }

    AddressScreen(
        uiState = uiState,
        onRecipientNameChange = viewModel::onRecipientNameChange,
        onFullAddressChange = viewModel::onFullAddressChange,
        onLandmarkChange = viewModel::onLandmarkChange,
        onSubmitClick = viewModel::submitOrder,
        onBackClick = onBackClick,
    )
}

@Composable
private fun AddressScreen(
    uiState: AddressUiState,
    onRecipientNameChange: (String) -> Unit,
    onFullAddressChange: (String) -> Unit,
    onLandmarkChange: (String) -> Unit,
    onSubmitClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { HipudTopBar(title = "Alamat Pengiriman", onBackClick = onBackClick) },
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            HipudTextField(value = uiState.recipientName, onValueChange = onRecipientNameChange, label = "Nama Lengkap")
            HipudTextField(value = uiState.fullAddress, onValueChange = onFullAddressChange, label = "Alamat")
            HipudTextField(value = uiState.landmark, onValueChange = onLandmarkChange, label = "Patokan alamat lengkap")

            uiState.errorMessage?.let { message ->
                Text(message, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.height(8.dp))
            HipudPrimaryButton(text = "Order dan Kirim", onClick = onSubmitClick, loading = uiState.isSubmitting)
        }
    }
}
