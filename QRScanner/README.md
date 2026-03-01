# QR Scanner Android App

A modern Android QR code scanning and generation application built with **Jetpack Compose**, featuring Material Design 3, camera-based scanning, and customizable QR code generation.

[![Code Quality](https://github.com/ajinkyaaher/QRScanner/actions/workflows/code-quality.yml/badge.svg)](https://github.com/ajinkyaaher/QRScanner/actions/workflows/code-quality.yml)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.22-purple.svg)](https://kotlinlang.org/)
[![Compose](https://img.shields.io/badge/Jetpack%20Compose-2024.02.00-brightgreen)](https://developer.android.com/jetpack/compose)

---

## Features

### QR Code Scanning
- **Real-time scanning** using ML Kit Barcode Scanning
- **CameraX integration** for smooth camera preview
- **Automatic barcode detection** with high accuracy
- **Permission handling** with graceful degradation

### QR Code Generation
- **6 Modern Color Themes**: Classic, Ocean, Sunset, Forest, Berry, Midnight
- **Gradient Support**: Linear gradient QR codes instead of flat colors
- **Logo Overlay**: Custom app logo embedded in QR codes
- **Corner Styles**: Square, Rounded, or Smooth corners
- **Share & Save**: Built-in functionality to share or download QR codes
- **High Error Correction**: 30% redundancy for reliable scanning

### Modern UI/UX
- **Material Design 3** with dynamic theming
- **Jetpack Compose** for declarative UI
- **Animated transitions** and smooth interactions
- **Responsive layouts** for all screen sizes
- **Dark/Light theme** support

---

## Tech Stack

| Category | Technology |
|----------|------------|
| **Language** | Kotlin 1.9.22 |
| **UI Framework** | Jetpack Compose 2024.02.00 |
| **Build System** | Gradle 8.9 with Android Gradle Plugin 8.5.0 |
| **Architecture** | MVVM with Compose State Management |
| **Camera** | CameraX 1.4.1 |
| **Barcode Scanning** | ML Kit Barcode Scanning 17.3.0 |
| **QR Generation** | ZXing 3.5.3 |
| **Navigation** | Navigation Compose 2.7.7 |
| **Code Quality** | Detekt 1.23.6, KtLint 12.1.0, Android Lint |

---

## Project Structure

```
QRScanner/
├── .github/
│   ├── workflows/           # GitHub Actions CI/CD
│   │   └── code-quality.yml # Code quality checks
│   ├── BRANCH_PROTECTION.md # Branch protection setup guide
│   └── CONFLUENCE.md        # Project documentation
├── app/
│   ├── src/main/
│   │   ├── java/com/ajinkya/qrscanner/
│   │   │   ├── MainActivity.kt          # Entry point
│   │   │   ├── ScanQRActivity.kt        # QR scanning screen
│   │   │   ├── GenerateQRActivity.kt    # QR generation screen
│   │   │   └── ui/theme/                # Compose theme
│   │   │       ├── Color.kt
│   │   │       ├── Theme.kt
│   │   │       └── Type.kt
│   │   ├── res/                        # Resources
│   │   └── AndroidManifest.xml
│   ├── config/                         # Detekt config
│   │   ├── detekt.yml
│   │   └── detekt-baseline.xml
│   ├── lint.xml                        # Android lint rules
│   └── build.gradle                    # App-level build config
├── build.gradle                        # Project-level build config
├── gradle.properties                   # Gradle settings
├── gradle/
│   └── wrapper/
│       └── gradle-wrapper.properties
└── README.md                           # This file
```

---

## Getting Started

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or newer
- JDK 21 (bundled with Android Studio)
- Android SDK 35
- Minimum Android API 21 (Android 5.0)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/ajinkyaaher/QRScanner.git
   cd QRScanner
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Choose the `QRScanner` folder

3. **Sync and Build**
   - Click "Sync Project with Gradle Files"
   - Run the app on an emulator or device

---

## Usage

### Scanning QR Codes
1. Open the app
2. Tap "Scan QR Code"
3. Point camera at a QR code
4. The app automatically detects and displays the content

### Generating QR Codes
1. Open the app
2. Tap "Generate QR Code"
3. Enter text or URL
4. Select color theme and style options
5. Tap "Generate QR Code"
6. Share or save the generated QR code

---

## Code Quality

This project enforces strict code quality standards through automated checks:

### Pre-commit Checks
```bash
# Run all checks locally before pushing
./gradlew detekt ktlintCheck lintDebug

# Auto-format Kotlin code
./gradlew ktlintFormat
```

### GitHub Actions
Every PR triggers:
- **Detekt Analysis** - Kotlin static analysis
- **KtLint Check** - Code formatting
- **Android Lint** - Android-specific issues
- **Unused Code Detection** - Dead code identification
- **Build Verification** - Compilation and unit tests
- **Security Scan** - Vulnerability detection

### Required Status Checks
All checks must pass before merging to `main`, `master`, or `develop`.

---

## 16 KB Page Size Support

This app is fully compatible with Android 15+ devices requiring 16 KB page sizes:
- CameraX 1.4.1+ with 16 KB alignment
- All native libraries are 16 KB aligned
- AGP 8.5.0 with proper ELF alignment

---

## Dependencies

### Core Android
```kotlin
implementation 'androidx.core:core-ktx:1.12.0'
implementation 'androidx.appcompat:appcompat:1.6.1'
implementation 'com.google.android.material:material:1.11.0'
```

### Jetpack Compose
```kotlin
implementation platform('androidx.compose:compose-bom:2024.02.00')
implementation 'androidx.compose.ui:ui'
implementation 'androidx.compose.material3:material3'
implementation 'androidx.navigation:navigation-compose:2.7.7'
```

### Camera & Scanning
```kotlin
implementation 'androidx.camera:camera-camera2:1.4.1'
implementation 'androidx.camera:camera-lifecycle:1.4.1'
implementation 'androidx.camera:camera-view:1.4.1'
implementation 'com.google.mlkit:barcode-scanning:17.3.0'
```

### QR Generation
```kotlin
implementation 'com.google.zxing:core:3.5.3'
```

---

## Contributing

We welcome contributions! Please follow these steps:

1. **Fork the repository**
2. **Create a feature branch**
   ```bash
   git checkout -b feat/your-feature-name
   ```
3. **Make your changes**
4. **Run code quality checks**
   ```bash
   ./gradlew detekt ktlintCheck lintDebug
   ```
5. **Commit with clear messages**
6. **Push and create a PR to `develop`**

### Branch Strategy
- `main`/`master` - Production-ready code
- `develop` - Development branch
- `feat/*` or `feature/*` - Feature branches
- PR required for all changes to protected branches

---

## License

```
MIT License

Copyright (c) 2024 Ajinkya Aher

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---

## Acknowledgments

- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Modern Android UI toolkit
- [CameraX](https://developer.android.com/training/camerax) - Camera library
- [ML Kit](https://developers.google.com/ml-kit) - On-device machine learning
- [ZXing](https://github.com/zxing/zxing) - QR code generation

---

## Contact & Support

- **GitHub Issues**: [Report bugs or request features](../../issues)
- **Documentation**: See [CONFLUENCE.md](.github/CONFLUENCE.md) for detailed docs
- **Project Wiki**: Check the GitHub wiki for additional guides

---

**Built with ❤️ using Jetpack Compose**
