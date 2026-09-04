package lat.pam.hipudapp.core.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import lat.pam.hipudapp.core.designsystem.component.BottomNavItem
import lat.pam.hipudapp.core.designsystem.component.HipudBottomBar
import lat.pam.hipudapp.domain.repository.CartRepository
import lat.pam.hipudapp.presentation.address.AddressRoute
import lat.pam.hipudapp.presentation.auth.AuthChoiceScreen
import lat.pam.hipudapp.presentation.auth.login.LoginRoute
import lat.pam.hipudapp.presentation.auth.register.RegisterRoute
import lat.pam.hipudapp.presentation.cart.CartRoute
import lat.pam.hipudapp.presentation.home.HomeRoute
import lat.pam.hipudapp.presentation.productdetail.ProductDetailRoute
import lat.pam.hipudapp.presentation.profile.ProfileRoute
import lat.pam.hipudapp.presentation.splash.SplashRoute
import lat.pam.hipudapp.presentation.success.SuccessRoute
import lat.pam.hipudapp.presentation.welcome.WelcomeScreen
import javax.inject.Inject

@HiltViewModel
class CartBadgeViewModel @Inject constructor(
    cartRepository: CartRepository,
) : ViewModel() {
    val itemCount: StateFlow<Int> = cartRepository.observeCart()
        .map { items -> items.sumOf { it.quantity } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)
}

private val bottomNavItems = listOf(
    BottomNavItem(HipudDestination.Home.route, "Home", Icons.Filled.Home, Icons.Outlined.Home),
    BottomNavItem(HipudDestination.Cart.route, "Keranjang", Icons.Filled.ShoppingCart, Icons.Outlined.ShoppingCart),
    BottomNavItem(HipudDestination.Profile.route, "Profile", Icons.Filled.Person, Icons.Outlined.Person),
)

@Composable
fun HipudNavHost() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = bottomNavItems.any { it.route == currentRoute }
    val cartBadgeViewModel: CartBadgeViewModel = hiltViewModel()
    val cartItemCount by cartBadgeViewModel.itemCount.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            if (showBottomBar) {
                HipudBottomBar(
                    items = bottomNavItems,
                    currentRoute = currentRoute,
                    onItemClick = { item ->
                        navController.navigate(item.route) {
                            popUpTo(HipudDestination.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    badgeCount = { item -> if (item.route == HipudDestination.Cart.route) cartItemCount else null },
                )
            }
        },
    ) { paddingValues ->
        val contentModifier = Modifier.padding(paddingValues)

        NavHost(
            navController = navController,
            startDestination = HipudDestination.Splash.route,
            modifier = Modifier.fillMaxSize(),
        ) {
            composable(HipudDestination.Splash.route) {
                SplashRoute(onSessionResolved = { destination ->
                    navController.navigate(destination) {
                        popUpTo(HipudDestination.Splash.route) { inclusive = true }
                    }
                })
            }
            composable(HipudDestination.Welcome.route) {
                WelcomeScreen(onStartClick = { navController.navigate(HipudDestination.AuthChoice.route) })
            }
            composable(HipudDestination.AuthChoice.route) {
                AuthChoiceScreen(
                    onRegisterClick = { navController.navigate(HipudDestination.Register.route) },
                    onLoginClick = { navController.navigate(HipudDestination.Login.route) },
                )
            }
            composable(HipudDestination.Register.route) {
                RegisterRoute(
                    onRegistered = {
                        navController.navigate(HipudDestination.Home.route) {
                            popUpTo(HipudDestination.Welcome.route) { inclusive = true }
                        }
                    },
                    onBackClick = navController::popBackStack,
                )
            }
            composable(HipudDestination.Login.route) {
                LoginRoute(
                    onLoggedIn = {
                        navController.navigate(HipudDestination.Home.route) {
                            popUpTo(HipudDestination.Welcome.route) { inclusive = true }
                        }
                    },
                    onBackClick = navController::popBackStack,
                )
            }
            composable(HipudDestination.Home.route) {
                HomeRoute(
                    onProductClick = { productId ->
                        navController.navigate(HipudDestination.ProductDetail.createRoute(productId))
                    },
                    modifier = contentModifier,
                )
            }
            composable(
                route = HipudDestination.ProductDetail.route,
                arguments = listOf(navArgument(HipudDestination.ProductDetail.ARG_PRODUCT_ID) { type = NavType.LongType }),
            ) {
                ProductDetailRoute(onBackClick = navController::popBackStack)
            }
            composable(HipudDestination.Cart.route) {
                CartRoute(
                    onProceedToAddress = { batchId ->
                        navController.navigate(HipudDestination.Address.createRoute(batchId))
                    },
                    onBrowseMenuClick = {
                        navController.navigate(HipudDestination.Home.route) {
                            popUpTo(HipudDestination.Home.route) { saveState = true }
                            launchSingleTop = true
                        }
                    },
                    modifier = contentModifier,
                )
            }
            composable(
                route = HipudDestination.Address.route,
                arguments = listOf(navArgument(HipudDestination.Address.ARG_BATCH_ID) { type = NavType.LongType }),
            ) {
                AddressRoute(
                    onOrderPlaced = {
                        navController.navigate(HipudDestination.Success.route) {
                            popUpTo(HipudDestination.Home.route)
                        }
                    },
                    onBackClick = navController::popBackStack,
                )
            }
            composable(HipudDestination.Success.route) {
                SuccessRoute(onDoneClick = {
                    navController.navigate(HipudDestination.Home.route) {
                        popUpTo(HipudDestination.Home.route) { inclusive = true }
                    }
                })
            }
            composable(HipudDestination.Profile.route) {
                ProfileRoute(
                    onLoggedOut = {
                        navController.navigate(HipudDestination.Welcome.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    modifier = contentModifier,
                )
            }
        }
    }
}
