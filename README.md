# 🍡 HipudApp

A mochi dessert ordering Android app, built with production-grade **Clean Architecture**,
**Jetpack Compose**, and **Material Design 3**.

---

## 🚀 Features

* User authentication (register & login) with salted, hashed passwords
* Browse the mochi catalog with pricing, description, and images
* Customize product variants (mochi skin thickness & filling texture) via selectable chips
* Delivery batch quota system (Morning / Afternoon batch) with a real-time progress indicator
* Cart management with quantity control and live subtotal
* Address form with input validation
* Order confirmation flow
* Light & Dark theme, switchable from the Profile screen
* Dependency injection wiring the whole app together with Hilt

---

## 🧩 Tech Stack

⚙️ Kotlin
🎨 Jetpack Compose + Material Design 3
💉 Hilt (Dependency Injection)
🧭 Navigation Compose
🔄 Kotlin Coroutines & Flow
---

## 🎯 Project Purpose

This project was built to practice and demonstrate real-world Android engineering concepts:

* Clean Architecture with clear `data` / `domain` / `presentation` separation
* MVVM with unidirectional state (`StateFlow<UiState>` per screen)
* Dependency Injection using Hilt
* Local persistence with Room + DataStore
* Declarative UI with Jetpack Compose
* Single-Activity navigation with Navigation Compose
* Secure credential handling (salted password hashing instead of plaintext)
* Reusable, themeable design system components

---

## 📌 Notes

* Authentication and data are fully local (no real backend) — the repository layer is
  interface-based, so a real API can be swapped in later without touching the UI or ViewModels
* Cart state is in-memory and does not persist across app restarts
* No automated tests or payment integration included yet

---

## 👩‍💻 Preview

> Paste UI screenshots here after running the app on a device/emulator.

### Light Mode

| Welcome | Home | Product Detail | Cart |
|---|---|---|---|
| _placeholder_ | _placeholder_ | _placeholder_ | _placeholder_ |

### Dark Mode

| Welcome | Home | Product Detail | Cart |
|---|---|---|---|
| _placeholder_ | _placeholder_ | _placeholder_ | _placeholder_ |

---

## ▶️ How to Build

1. Clone repository

```bash
git clone https://github.com/USERNAME/AplikasiPemesananMochi.git
```

2. Open in Android Studio

```
File → Open → select the cloned folder
```

3. Sync Gradle

```bash
./gradlew build
```

4. Run the app

Connect a physical device (USB debugging enabled) or start an emulator (API 24+), then hit
**Run ▶** in Android Studio, or:

```bash
./gradlew installDebug
```

5. (Optional) Build a debug APK

```bash
./gradlew assembleDebug
```

The generated APK will be available at `app/build/outputs/apk/debug/`.

---

## 👩‍💻 Developer

* **Name:** Ratu Qurratul Aini
* **Status:** Informatics Engineering Student
* **Email:** ratuquratul@gmail.com
* **LinkedIn:** [linkedin.com/in/ratu-qurratul-aini-885b7a2a6](https://www.linkedin.com/in/ratu-qurratul-aini-885b7a2a6/)
