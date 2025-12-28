📱 HipudApp
Aplikasi Pemesanan Dessert Mochi Berbasis Android
**BAB I

PENDAHULUAN**

1.1 Latar Belakang

Perkembangan teknologi mobile telah mendorong pemanfaatan aplikasi Android dalam berbagai bidang, termasuk sektor kuliner. Aplikasi mobile memungkinkan proses pemesanan makanan menjadi lebih praktis, cepat, dan efisien bagi pengguna.

Berdasarkan hal tersebut, dikembangkan sebuah aplikasi Android bernama HipudApp, yaitu aplikasi pemesanan dessert berbasis mochi yang dirancang sebagai media pembelajaran pada mata kuliah Pemrograman Aplikasi Mobile (PAM). Aplikasi ini mengimplementasikan konsep dasar pengembangan Android, seperti penggunaan Activity, Intent, RecyclerView, serta penyimpanan data lokal.

1.2 Tujuan Pengembangan

Tujuan dari pengembangan aplikasi HipudApp adalah sebagai berikut:

Mengimplementasikan konsep dasar pengembangan aplikasi Android

Menerapkan navigasi antar Activity menggunakan Intent

Menggunakan RecyclerView untuk menampilkan daftar menu

Mengelola data pengguna menggunakan SharedPreferences

Membuat alur aplikasi pemesanan yang terstruktur dan mudah dipahami

1.3 Ruang Lingkup Aplikasi

Ruang lingkup aplikasi HipudApp meliputi:

Autentikasi pengguna (Register & Login)

Penampilan daftar menu dessert

Rekap pesanan

Pengisian alamat pengiriman

Konfirmasi pemesanan

Tampilan profil pengguna

**BAB II

LANDASAN TEORI**

2.1 Android

Android merupakan sistem operasi berbasis Linux yang dikembangkan oleh Google untuk perangkat mobile. Android menyediakan Software Development Kit (SDK) yang memungkinkan pengembang membangun aplikasi menggunakan bahasa pemrograman Kotlin atau Java.

2.2 Kotlin

Kotlin adalah bahasa pemrograman modern yang bersifat statically typed dan direkomendasikan oleh Google sebagai bahasa utama untuk pengembangan aplikasi Android karena lebih ringkas, aman, dan efisien.

2.3 SharedPreferences

SharedPreferences merupakan mekanisme penyimpanan data sederhana pada Android yang digunakan untuk menyimpan data dalam bentuk pasangan key-value, seperti username dan password.

**BAB III

PERANCANGAN SISTEM**

3.1 Teknologi yang Digunakan
Komponen	Keterangan
Bahasa Pemrograman	Kotlin
IDE	Android Studio
UI Layout	ConstraintLayout, LinearLayout
Komponen UI	RecyclerView, CardView, MaterialButton
Navigasi	BottomNavigationView
Penyimpanan Data	SharedPreferences
Minimum SDK	API Level 24
3.2 Daftar Menu Dessert

Aplikasi HipudApp menyediakan 10 menu dessert mochi, yaitu:

Mochi Strawberry

Mochi Choco

Mochi Greentea

Mochi Mangga

Mochi Red Velvet

Mochi Matcha

Mochi Kacang Choco

Mochi Berry

Mochi Blueberry

Mochi Oreo

3.3 Alur Aplikasi

Alur penggunaan aplikasi adalah sebagai berikut:

Pengguna membuka aplikasi (Main Page)

Pengguna menekan tombol Start Now

Masuk ke halaman Autentikasi

Pengguna melakukan Register atau Login

Masuk ke halaman Home

Pengguna memilih menu dessert

Masuk ke halaman Order (Rekap Pesanan)

Mengisi alamat pengiriman

Pesanan berhasil dan ditampilkan pada halaman Success

Pengguna dapat melihat data diri pada halaman Profile

**BAB IV

IMPLEMENTASI SISTEM**

4.1 Daftar Halaman Aplikasi

Aplikasi HipudApp terdiri dari 9 halaman utama, yaitu:

Main Page

Auth Page

Register Page

Login Page

Home Page

Order Page

Address Page

Success Page

Profile Page

4.2 Dokumentasi Screenshot Aplikasi

Screenshot aplikasi disimpan pada folder berikut:

![01_main_page png](https://github.com/user-attachments/assets/c1a9cfc8-f64f-4e5d-b983-a5133828e4b7)
![02_auth_page](https://github.com/user-attachments/assets/1f9c5442-8ee3-424e-9063-0a88efa1fa47)
![03_register_page](https://github.com/user-attachments/assets/447d00b6-da63-4032-9046-671b853d77ed)
![04_login_page](https://github.com/user-attachments/assets/58f15708-5d66-4a64-ae4d-1484144b91e2)
![05_home_pag](https://github.com/user-attachments/assets/2d8cd642-ea6e-43ea-8c23-93eccd5c9cbe)




Screenshot ini digunakan sebagai dokumentasi visual pada laporan dan README.

**BAB V

PENGUJIAN SISTEM**

Pengujian dilakukan dengan menjalankan aplikasi pada emulator Android dan perangkat fisik. Seluruh fitur aplikasi dapat berjalan dengan baik, meliputi:

Navigasi antar halaman

Penyimpanan dan pengambilan data pengguna

Penampilan menu dessert

Proses pemesanan hingga konfirmasi

**BAB VI

KESIMPULAN**

Berdasarkan hasil pengembangan dan pengujian, dapat disimpulkan bahwa aplikasi HipudApp berhasil diimplementasikan sesuai dengan tujuan pembelajaran. Aplikasi ini mampu menampilkan alur pemesanan dessert berbasis Android dengan memanfaatkan komponen dasar Android secara optimal.

Aplikasi ini masih dapat dikembangkan lebih lanjut dengan menambahkan database online, sistem pembayaran, serta integrasi API.

IDENTITAS PENGEMBANG

Nama: Ratu Qurratul Aini

Program Studi: Teknik Informatika

Universitas: UIN Sunan Gunung Djati Bandung

Mata Kuliah: Pemrograman Aplikasi Mobile
