# 🍡 HipudApp

**HipudApp** adalah aplikasi Android pemesanan dessert mochi ("Cafe Centil") yang dibangun dengan
arsitektur production-grade: Clean Architecture, MVVM, Jetpack Compose, dan Material Design 3.
Pengguna dapat mendaftar/login, menelusuri katalog mochi, mengkustomisasi varian produk, memilih
jadwal pengiriman (delivery batch) sesuai kuota yang tersedia, mengisi alamat pengiriman, hingga
menerima konfirmasi pesanan — semuanya dalam tampilan soft-pink yang clean dan profesional, dengan
dukungan dark mode premium.

## ✨ Fitur Utama

- **Autentikasi lokal** — register & login dengan password yang di-hash (salted SHA-256), bukan
  disimpan sebagai plaintext.
- **Katalog produk** — 10 varian mochi dengan harga, deskripsi, dan gambar.
- **Customizer varian produk** — pilih ketebalan kulit mochi & tekstur isian langsung dari halaman
  detail produk lewat selectable chips.
- **Kuota & jadwal pengiriman (delivery batch)** — Batch Pagi / Batch Sore dengan progress bar
  kuota real-time; batch yang penuh otomatis terkunci.
- **Keranjang & checkout** — kelola jumlah item, pilih jadwal pengiriman, isi alamat, dan lihat
  ringkasan total sebelum memesan.
- **Profil & preferensi tema** — ganti tema Sistem / Terang / Gelap kapan saja.

## 🛠️ Tech Stack

| Layer | Teknologi |
|---|---|
| Bahasa | Kotlin |
| UI | Jetpack Compose + Material Design 3 |
| Arsitektur | Clean Architecture (data / domain / presentation) + MVVM |
| Dependency Injection | Hilt |
| Local Database | Room |
| Preferences | DataStore |
| Navigasi | Navigation Compose |
| Concurrency | Kotlin Coroutines & Flow |
| Font | Poppins (headline) + Roboto (body) |

### Struktur Modul

```
lat.pam.hipudapp/
├── core/            # design system, navigasi, util, penanganan error bersama
├── data/            # Room, DataStore, implementasi repository, modul Hilt
├── domain/          # model bisnis, kontrak repository, use case
└── presentation/    # layar Compose + ViewModel per fitur
```

## 🚀 Cara Menjalankan Secara Lokal

### Prasyarat

- [Android Studio](https://developer.android.com/studio) versi terbaru (Ladybug/Koala ke atas)
- JDK 11 atau lebih baru
- Android SDK dengan `compileSdk 36` terpasang
- Koneksi internet (untuk sinkronisasi dependency Gradle saat pertama kali membuka proyek)

### Langkah-langkah

1. **Clone repository**
   ```bash
   git clone https://github.com/<username>/AplikasiPemesananMochi.git
   cd AplikasiPemesananMochi
   ```

2. **Buka di Android Studio**
   - Pilih **File → Open**, arahkan ke folder hasil clone, lalu tunggu Android Studio
     mengindeks proyek.

3. **Sinkronisasi Gradle**
   - Android Studio akan otomatis menawarkan **Sync Now** — klik untuk mengunduh seluruh
     dependency (Compose, Hilt, Room, dll). Bisa juga dijalankan manual:
     ```bash
     ./gradlew build
     ```

4. **Jalankan aplikasi**
   - Sambungkan perangkat fisik (USB debugging aktif) atau jalankan emulator Android
     (API 24 ke atas) dari **Device Manager**.
   - Klik tombol **Run ▶** di Android Studio, atau lewat terminal:
     ```bash
     ./gradlew installDebug
     ```

5. **(Opsional) Build APK debug**
   ```bash
   ./gradlew assembleDebug
   ```
   APK hasil build tersedia di `app/build/outputs/apk/debug/`.

## 📸 Screenshot

> Tempel screenshot UI di sini setelah aplikasi dijalankan pada perangkat/emulator.

### Light Mode

| Welcome | Home | Product Detail | Cart |
|---|---|---|---|
| _placeholder_ | _placeholder_ | _placeholder_ | _placeholder_ |

### Dark Mode

| Welcome | Home | Product Detail | Cart |
|---|---|---|---|
| _placeholder_ | _placeholder_ | _placeholder_ | _placeholder_ |

## 👩‍💻 Identitas Pengembang

- **Nama:** Ratu Qurratul Aini
- **Program Studi:** Teknik Informatika
- **Universitas:** UIN Sunan Gunung Djati Bandung
- **Mata Kuliah:** Pemrograman Aplikasi Mobile
