package lat.pam.hipudapp.core.util

import java.util.Locale

fun Int.toRupiah(): String = "Rp${String.format(Locale("in", "ID"), "%,d", this)}"
