package lat.pam.hipudapp.core.navigation

sealed class HipudDestination(val route: String) {
    data object Splash : HipudDestination("splash")
    data object Welcome : HipudDestination("welcome")
    data object AuthChoice : HipudDestination("auth_choice")
    data object Login : HipudDestination("login")
    data object Register : HipudDestination("register")
    data object Home : HipudDestination("home")

    data object ProductDetail : HipudDestination("product_detail/{productId}") {
        const val ARG_PRODUCT_ID = "productId"
        fun createRoute(productId: Long) = "product_detail/$productId"
    }

    data object Cart : HipudDestination("cart")

    data object Address : HipudDestination("address/{batchId}") {
        const val ARG_BATCH_ID = "batchId"
        fun createRoute(batchId: Long) = "address/$batchId"
    }

    data object Success : HipudDestination("success")
    data object Profile : HipudDestination("profile")
}
