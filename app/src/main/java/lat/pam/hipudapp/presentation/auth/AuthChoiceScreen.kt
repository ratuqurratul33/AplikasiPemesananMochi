package lat.pam.hipudapp.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import lat.pam.hipudapp.core.designsystem.component.HipudOutlinedButton
import lat.pam.hipudapp.core.designsystem.component.HipudPrimaryButton
import lat.pam.hipudapp.core.designsystem.theme.HeroTextStyle

@Composable
fun AuthChoiceScreen(onRegisterClick: () -> Unit, onLoginClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 32.dp, vertical = 48.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("Sign up", style = HeroTextStyle, color = MaterialTheme.colorScheme.primary)
                Text(
                    text = "Daftar untuk mulai memesan dessert favoritmu dari Hipud",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp),
                )
            }
            Column {
                HipudPrimaryButton(text = "Register", onClick = onRegisterClick)
                Spacer(Modifier.height(12.dp))
                HipudOutlinedButton(text = "Login", onClick = onLoginClick)
            }
        }
    }
}
