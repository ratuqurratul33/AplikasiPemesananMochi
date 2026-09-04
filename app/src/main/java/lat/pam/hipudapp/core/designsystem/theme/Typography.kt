package lat.pam.hipudapp.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import lat.pam.hipudapp.R

// Poppins carries the "Cafe Centil" headline vibe; body/label text uses the platform's
// clean default sans-serif (Roboto) so paragraphs stay easy to read at small sizes.
val PoppinsFontFamily = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_bold, FontWeight.Bold),
)

val HipudTypography = Typography().let { base ->
    Typography(
        displayLarge = base.displayLarge.copy(fontFamily = PoppinsFontFamily, fontWeight = FontWeight.SemiBold),
        displayMedium = base.displayMedium.copy(fontFamily = PoppinsFontFamily, fontWeight = FontWeight.SemiBold),
        displaySmall = base.displaySmall.copy(fontFamily = PoppinsFontFamily, fontWeight = FontWeight.SemiBold),
        headlineLarge = base.headlineLarge.copy(fontFamily = PoppinsFontFamily, fontWeight = FontWeight.SemiBold),
        headlineMedium = base.headlineMedium.copy(fontFamily = PoppinsFontFamily, fontWeight = FontWeight.SemiBold),
        headlineSmall = base.headlineSmall.copy(fontFamily = PoppinsFontFamily, fontWeight = FontWeight.SemiBold),
        titleLarge = base.titleLarge.copy(fontFamily = PoppinsFontFamily, fontWeight = FontWeight.SemiBold),
        titleMedium = base.titleMedium.copy(fontFamily = PoppinsFontFamily, fontWeight = FontWeight.Medium),
        titleSmall = base.titleSmall.copy(fontFamily = PoppinsFontFamily, fontWeight = FontWeight.Medium),
        bodyLarge = base.bodyLarge,
        bodyMedium = base.bodyMedium,
        bodySmall = base.bodySmall,
        labelLarge = base.labelLarge.copy(fontFamily = PoppinsFontFamily, fontWeight = FontWeight.Medium),
        labelMedium = base.labelMedium.copy(fontFamily = PoppinsFontFamily, fontWeight = FontWeight.Medium),
        labelSmall = base.labelSmall.copy(fontFamily = PoppinsFontFamily, fontWeight = FontWeight.Medium),
    )
}

/** Slightly larger, heavier style reserved for hero/brand moments (Welcome screen title). */
val HeroTextStyle = TextStyle(
    fontFamily = PoppinsFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 36.sp,
    lineHeight = 42.sp,
)
