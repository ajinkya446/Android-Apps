# QR Scanner Android App

A modern Android QR code scanning and generation application built with **Jetpack Compose**, featuring Material Design 3, camera-based scanning, and customizable QR code generation.

[![Code Quality](https://github.com/ajinkyaaher/QRScanner/actions/workflows/code-quality.yml/badge.svg)](https://github.com/ajinkyaaher/QRScanner/actions/workflows/code-quality.yml)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.22-purple.svg)](https://kotlinlang.org/)
[![Compose](https://img.shields.io/badge/Jetpack%20Compose-2024.02.00-brightgreen)](https://developer.android.com/jetpack/compose)

---

## 📱 App Screenshots

<div align="center">

### Main Screen
<img src="screenshots/main_screen.png" width="250" alt="Main Screen">

*Choose between Scan or Generate QR codes*

### QR Scanning
<img src="screenshots/scan_screen.png" width="250" alt="Scan Screen">

*Real-time QR code detection with camera*

### QR Generation
<img src="screenshots/generate_screen.png" width="250" alt="Generate Screen">

*Create custom QR codes with themes and styles*

### Generated QR with Gradient
<img src="screenshots/qr_gradient.png" width="250" alt="Gradient QR">

*Beautiful gradient QR code with logo overlay*

</div>

> **Note:** Add your screenshots to a `screenshots/` folder in the repository root. Recommended size: 1080x1920 pixels.

---

## ✨ Features

### QR Code Scanning
- **Real-time scanning** using ML Kit Barcode Scanning
- **CameraX integration** for smooth camera preview
- **Automatic barcode detection** with high accuracy
- **Permission handling** with graceful degradation
- **Instant result display** with copy/share options

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

## 🚀 How the App Works

### Architecture Flow

```
┌─────────────────────────────────────────────────────────────┐
│                         USER                                  │
└──────────────────────┬────────────────────────────────────────┘
                       │
       ┌───────────────┼───────────────┐
       ▼               ▼               ▼
┌──────────────┐ ┌──────────────┐ ┌──────────────┐
│   Scan QR    │ │  Generate QR │ │    Main      │
│   Screen     │ │   Screen     │ │    Menu      │
└──────┬───────┘ └──────┬───────┘ └──────────────┘
       │                │
       ▼                ▼
┌─────────────────────────────────────────────────────────────┐
│                    PROCESSING LAYER                          │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐       │
│  │ ML Kit       │  │ ZXing        │  │ CameraX      │       │
│  │ Barcode      │  │ QR Encoder   │  │ Preview      │       │
│  │ Detection    │  │              │  │              │       │
│  └──────────────┘  └──────────────┘  └──────────────┘       │
└─────────────────────────────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│                      OUTPUT                                  │
│         QR Content / Generated QR Bitmap                     │
└─────────────────────────────────────────────────────────────┘
```

### Feature Workflows

#### QR Scanning Workflow
1. **User taps "Scan QR Code"** → Opens camera preview
2. **CameraX initializes** → Starts camera feed with `PreviewView`
3. **ML Kit BarcodeScanner analyzes** → Processes each frame for QR codes
4. **QR Code detected** → Extracts content (URL, text, contact, etc.)
5. **Result displayed** → Shows content with options to copy, share, or open

#### QR Generation Workflow
1. **User taps "Generate QR Code"** → Opens generation screen
2. **Enter text/URL** → User types content to encode
3. **Select styling options:**
   - **Color Theme**: Classic, Ocean, Sunset, Forest, Berry, Midnight
   - **Gradient Toggle**: Enable/disable gradient effect
   - **Logo Toggle**: Show/hide center logo
   - **Corner Style**: Square, Rounded, Smooth
4. **Tap "Generate"** → ZXing creates QR bitmap with custom styling
5. **Result displayed** → Shows QR with share/save options

### Technical Implementation

#### QR Generation Process
```kotlin
// 1. Configure QR encoding with high error correction
val hints = hashMapOf<EncodeHintType, Any>()
hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H // 30% redundancy
hints[EncodeHintType.MARGIN] = 2

// 2. Generate bit matrix
val writer = MultiFormatWriter()
val bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 768, 768, hints)

// 3. Apply custom styling (colors, gradient, rounded corners)
val bitmap = createStyledQR(bitMatrix, primaryColor, gradientColor, cornerRadius)

// 4. Add logo overlay if enabled
if (hasLogo) {
    canvas.drawBitmap(logo, centerX, centerY, null)
}
```

#### QR Scanning Process
```kotlin
// 1. Initialize CameraX with preview and analysis
val preview = Preview.Builder().build()
val imageAnalysis = ImageAnalysis.Builder()
    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
    .build()

// 2. Set up ML Kit barcode scanner
val scanner = BarcodeScanning.getClient()

// 3. Process camera frames
imageAnalysis.setAnalyzer(executor) { imageProxy ->
    scanner.process(imageProxy.image!!, imageProxy.imageInfo.rotationDegrees)
        .addOnSuccessListener { barcodes ->
            // Handle detected QR codes
        }
}
```

---

## 🔒 Pre-Merging Checks (Quality Gates)

> ⚠️ **IMPORTANT**: For checks to appear on PRs, the workflow file must be on the `main`/`master` branch first!  
> See [.github/WORKFLOW_SETUP.md](.github/WORKFLOW_SETUP.md) for activation instructions.

Every Pull Request **MUST PASS** all automated checks before merging.

### Workflow Diagram

```
┌─────────────────────────────────────────────────────────────────────┐
│                    PULL REQUEST CREATED                              │
└──────────────────────────┬──────────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────────────┐
│              GITHUB ACTIONS TRIGGERS AUTOMATICALLY                 │
└──────────────────────────┬──────────────────────────────────────────┘
                         │
         ┌───────────────┼───────────────┬───────────────┐
         ▼               ▼               ▼               ▼
   ┌──────────┐   ┌──────────┐   ┌──────────┐   ┌──────────┐
   │ Detekt   │   │ KtLint   │   │ Android  │   │ Build &  │
   │ Analysis │   │ Check    │   │ Lint     │   │ Test     │
   │ (1 min)  │   │ (30s)    │   │ (2 min)  │   │ (3 min)  │
   └────┬─────┘   └────┬─────┘   └────┬─────┘   └────┬─────┘
        │              │              │              │
        └──────────────┴──────────────┴──────────────┘
                         │
                         ▼
              ┌─────────────────────┐
              │   QUALITY GATE      │
              │   All checks pass?  │
              └──────────┬──────────┘
                         │
            ┌────────────┴────────────┐
            ▼                         ▼
    ┌──────────────┐         ┌──────────────┐
    │  ✅ PASS     │         │  ❌ FAIL     │
    │  Merge       │         │  Blocked     │
    │  Enabled     │         │  Fix issues  │
    └──────────────┘         └──────────────┘
```

### Required Checks

| Check | Purpose | What It Validates | Fail Condition |
|-------|---------|-------------------|----------------|
| **Detekt Analysis** | Kotlin code quality | Unused imports, unused parameters, unused private members, code complexity | Any unused code found → ❌ FAIL |
| **KtLint Check** | Code formatting | Kotlin style guide compliance | Formatting issues → ❌ FAIL |
| **Android Lint** | Android-specific issues | Unused resources, unused attributes, unused IDs, performance issues | Unused resources or errors → ❌ FAIL |
| **Build Verification** | Compilation & tests | Code compiles, unit tests pass | Build fails or tests fail → ❌ FAIL |
| **Kotlin Warnings** | Compiler checks | Unused import warnings | Warnings found → ❌ FAIL |

### What Gets Checked

#### ✅ Unused Import Detection
```kotlin
// ❌ This will FAIL the PR
import androidx.compose.material.icons.Icons  // Never used
import java.util.Date  // Never used

// ✅ This will PASS
import androidx.compose.material.icons.Icons  // Used below
Icon(imageVector = Icons.Default.QrCode, ...)
```

#### ✅ Unused Resource Detection
```xml
<!-- ❌ This will FAIL the PR -->
<color name="unused_color">#FF0000</color>  <!-- Not referenced anywhere -->
<string name="unused_string">Hello</string>  <!-- Not used -->

<!-- ✅ This will PASS -->
<color name="primary">#0066CC</color>  <!-- Used in layouts/code -->
```

#### ✅ Unused Variable/Parameter Detection
```kotlin
// ❌ This will FAIL the PR
fun processQR(text: String, unusedParam: Int) {  // unusedParam never used
    val result = text
    val unusedVar = "test"  // Never used
}

// ✅ This will PASS
fun processQR(text: String, retryCount: Int) {  // All parameters used
    val result = process(text, retryCount)  // All variables used
}
```

### How to Fix Failed Checks

#### If Detekt Fails (Unused Imports)
```bash
# Check what failed
./gradlew detekt

# Review the report
open app/build/reports/detekt/detekt.html

# Remove unused imports manually or use IDE optimize imports
```

#### If Android Lint Fails (Unused Resources)
```bash
# Check what failed
./gradlew lintDebug

# Review the report
open app/build/reports/lint-results-debug.html

# Remove unused resources from res/ folder
```

#### If KtLint Fails (Formatting)
```bash
# Auto-fix formatting issues
./gradlew ktlintFormat

# Verify fixes
./gradlew ktlintCheck
```

### Branch Protection Rules

The repository enforces these rules:

1. **No Direct Push** to `main`, `master`, or `develop`
2. **Pull Request Required** for all changes
3. **All Checks Must Pass** before merging
4. **Branch Must Be Up-to-Date** before merging (shows "Update branch" button)
5. **Conversation Resolution** required

### Setting Up Locally

Before creating a PR, run checks locally:

```bash
# Run all quality checks
./gradlew clean detekt ktlintCheck lintDebug assembleDebug testDebugUnitTest

# Or use the combined check script
./gradlew check
```

---

## 📋 Tech Stack

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

## 📁 Project Structure

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
│   │   ├── res/                        # Resources
│   │   └── AndroidManifest.xml
│   ├── config/                         # Detekt config
│   ├── lint.xml                        # Android lint rules
│   └── build.gradle                    # App-level build config
├── screenshots/                        # App screenshots
├── build.gradle                        # Project-level build config
└── README.md                           # This file
```

---

## 🚀 Getting Started

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

## 💡 Usage Guide

### Scanning QR Codes
1. Open the app
2. Tap **"Scan QR Code"**
3. Point camera at a QR code
4. The app automatically detects and displays the content
5. Tap the result to copy, share, or open URLs

### Generating QR Codes
1. Open the app
2. Tap **"Generate QR Code"**
3. Enter text or URL
4. Select styling options:
   - **Color Theme**: Choose from 6 modern themes
   - **Gradient**: Enable for stylish gradient effect
   - **Logo**: Toggle center logo overlay
   - **Corner Style**: Square, Rounded, or Smooth
5. Tap **"Generate QR Code"**
6. Share via social apps or save to gallery

---

## 🔐 16 KB Page Size Support

This app is fully compatible with Android 15+ devices requiring 16 KB page sizes:
- CameraX 1.4.1+ with 16 KB alignment
- All native libraries are 16 KB aligned
- AGP 8.5.0 with proper ELF alignment

---

## 🤝 Contributing

We welcome contributions! Please follow these steps:

1. **Fork the repository**
2. **Create a feature branch**
   ```bash
   git checkout -b feat/your-feature-name
   ```
3. **Make your changes**
4. **Run code quality checks** (MUST PASS before PR)
   ```bash
   ./gradlew detekt ktlintCheck lintDebug
   ```
5. **Commit with clear messages**
6. **Push and create a PR to `develop`**
7. **Wait for all checks to pass** (GitHub Actions will run automatically)

### Branch Strategy
- `main`/`master` - Production-ready code (protected)
- `develop` - Development branch (protected)
- `feat/*` or `feature/*` - Feature branches
- **PR required** for all changes to protected branches

---

## 📄 License

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

## 🙏 Acknowledgments

- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Modern Android UI toolkit
- [CameraX](https://developer.android.com/training/camerax) - Camera library
- [ML Kit](https://developers.google.com/ml-kit) - On-device machine learning
- [ZXing](https://github.com/zxing/zxing) - QR code generation

---

## 📞 Contact & Support

- **GitHub Issues**: [Report bugs or request features](../../issues)
- **Documentation**: See [.github/CONFLUENCE.md](.github/CONFLUENCE.md) for detailed docs
- **Branch Protection**: See [.github/BRANCH_PROTECTION.md](.github/BRANCH_PROTECTION.md) for PR setup

---

**Built with ❤️ using Jetpack Compose**
