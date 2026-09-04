package lat.pam.hipudapp.core.result

sealed class AppError(val message: String) {
    data object EmptyCredentials : AppError("Username dan password wajib diisi")
    data object UsernameTaken : AppError("Username sudah terdaftar, gunakan username lain")
    data object AccountNotFound : AppError("Akun belum terdaftar, silakan daftar terlebih dahulu")
    data object InvalidCredentials : AppError("Username atau password salah")
    data object NotLoggedIn : AppError("Sesi berakhir, silakan login kembali")
    data object BatchFull : AppError("Kuota batch pengiriman ini sudah penuh")
    data object NoBatchSelected : AppError("Pilih jadwal pengiriman terlebih dahulu")
    data object EmptyCart : AppError("Keranjang masih kosong")
    data object InvalidAddress : AppError("Nama, alamat, dan patokan wajib diisi lengkap")
    data class Unknown(val cause: Throwable? = null) : AppError("Terjadi kesalahan, silakan coba lagi")
}
