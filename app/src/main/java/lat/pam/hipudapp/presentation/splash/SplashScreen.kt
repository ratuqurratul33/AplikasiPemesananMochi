package lat.pam.hipudapp.presentation.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import lat.pam.hipudapp.core.designsystem.theme.HeroTextStyle
import lat.pam.hipudapp.core.navigation.HipudDestination
import lat.pam.hipudapp.domain.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _startDestination = MutableStateFlow<String?>(null)
    val startDestination = _startDestination.asStateFlow()

    init {
        viewModelScope.launch {
            val loggedInUser = authRepository.observeCurrentUser().first()
            _startDestination.value = if (loggedInUser != null) HipudDestination.Home.route else HipudDestination.Welcome.route
        }
    }
}

@Composable
fun SplashRoute(
    onSessionResolved: (startDestination: String) -> Unit,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    val startDestination by viewModel.startDestination.collectAsStateWithLifecycle()

    LaunchedEffect(startDestination) {
        startDestination?.let(onSessionResolved)
    }

    SplashScreen()
}

@Composable
private fun SplashScreen() {
    Box(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center,
    ) {
        Text("HI.PuD", style = HeroTextStyle, color = MaterialTheme.colorScheme.primary)
    }
}
