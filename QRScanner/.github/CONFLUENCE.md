# QR Scanner - Project Documentation

---

## 1. Project Overview

### 1.1 Purpose
QR Scanner is a modern Android application that provides QR code scanning and generation capabilities with a focus on:
- **User Experience**: Intuitive Material Design 3 interface
- **Performance**: Real-time scanning with minimal latency
- **Customization**: Modern QR code styling options

### 1.2 Key Features
| Feature | Description | Status |
|---------|-------------|--------|
| QR Scanning | ML Kit powered real-time scanning | ✅ Live |
| QR Generation | ZXing with custom themes | ✅ Live |
| Gradient QR | Linear gradient QR codes | ✅ Live |
| Logo Overlay | Center logo embedding | ✅ Live |
| 16 KB Support | Android 15+ compatibility | ✅ Live |
| CI/CD | GitHub Actions automation | ✅ Live |

### 1.3 Technology Decisions

#### Why Jetpack Compose?
- Declarative UI reduces boilerplate by 40%
- Built-in theming with Material Design 3
- State management simplified with Compose State

#### Why CameraX + ML Kit?
- CameraX provides consistent camera behavior across devices
- ML Kit offers on-device ML without network dependencies
- Both are officially supported by Google

#### Why ZXing for Generation?
- Industry standard for QR generation
- Customizable encoding parameters
- High error correction support (H level = 30%)

---

## 2. Architecture

### 2.1 System Architecture Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                        UI Layer                              │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐       │
│  │ MainScreen   │  │ ScanScreen   │  │ GenerateScreen│       │
│  │  (Compose)   │  │  (Compose)   │  │  (Compose)    │       │
│  └──────┬───────┘  └──────┬───────┘  └──────┬───────┘       │
└─────────┼────────────────┼────────────────┼────────────────┘
          │                │                │
          ▼                ▼                ▼
┌─────────────────────────────────────────────────────────────┐
│                    Presentation Layer                        │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐       │
│  │ State Hoisting│  │ Camera State │  │ QR Bitmap   │       │
│  │ ViewModel     │  │ Controller   │  │ Generator   │       │
│  └──────────────┘  └──────────────┘  └──────────────┘       │
└─────────────────────────────────────────────────────────────┘
          │                │                │
          ▼                ▼                ▼
┌─────────────────────────────────────────────────────────────┐
│                      Domain Layer                            │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐       │
│  │ QR Analysis  │  │ Barcode      │  │ QR Styling  │       │
│  │ Result Model │  │ Scanning     │  │ Options     │       │
│  └──────────────┘  └──────────────┘  └──────────────┘       │
└─────────────────────────────────────────────────────────────┘
          │                │                │
          ▼                ▼                ▼
┌─────────────────────────────────────────────────────────────┐
│                      Data Layer                              │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐       │
│  │ CameraX      │  │ ML Kit       │  │ ZXing       │       │
│  │ Preview      │  │ Barcode      │  │ Encoder     │       │
│  └──────────────┘  └──────────────┘  └──────────────┘       │
└─────────────────────────────────────────────────────────────┘
```

### 2.2 Module Dependencies

```
App Module
├── Compose UI (androidx.compose)
├── Navigation (androidx.navigation)
├── CameraX (androidx.camera)
├── ML Kit (com.google.mlkit)
├── ZXing (com.google.zxing)
└── Material 3 (androidx.compose.material3)
```

---

## 3. Development Guidelines

### 3.1 Code Style

#### Kotlin Style Guide
```kotlin
// ✅ Correct - Explicit types for public APIs
fun generateQRCode(text: String): Bitmap? { }

// ✅ Correct - Trailing commas
val colorSchemes = listOf(
    ColorScheme("Classic", Color.Black, Color.White),
    ColorScheme("Ocean", Color.Blue, Color.Cyan),
)

// ✅ Correct - Named parameters for clarity
qrBitmap = generateModernQRCode(
    text = text,
    primaryColor = scheme.primary.toArgb(),
    hasGradient = hasGradient,
)

// ❌ Avoid - Implicit types in public APIs
fun generateQRCode(text) = ...
```

#### Compose UI Guidelines
```kotlin
// ✅ Correct - State hoisting
@Composable
fun GenerateQRScreen(
    onBackClick: () -> Unit,  // Event callback
) {
    var text by remember { mutableStateOf("") }
    // State managed internally
}

// ✅ Correct - Preview annotation
@Preview(showBackground = true)
@Composable
fun GenerateQRScreenPreview() {
    QRScannerTheme {
        GenerateQRScreen(onBackClick = {})
    }
}
```

### 3.2 Naming Conventions

| Component | Convention | Example |
|-----------|------------|---------|
| Composables | PascalCase + Screen/Suffix | `GenerateQRScreen`, `ScanQRScreen` |
| ViewModels | PascalCase + ViewModel | `QRViewModel` |
| State | camelCase | `qrBitmap`, `textInput` |
| Constants | UPPER_SNAKE_CASE | `MAX_QR_SIZE`, `DEFAULT_COLOR` |
| Theme | PascalCase + Theme | `QRScannerTheme` |

### 3.3 File Organization

```
com.ajinkya.qrscanner/
├── MainActivity.kt           # Entry point
├── ScanQRActivity.kt         # Feature: Scanning
├── GenerateQRActivity.kt     # Feature: Generation
├── ui/
│   ├── theme/
│   │   ├── Color.kt         # Theme colors
│   │   ├── Theme.kt         # Theme definition
│   │   └── Type.kt          # Typography
│   └── components/           # Reusable UI components
└── utils/
    ├── QRCodeUtils.kt       # QR generation helpers
    └── Extensions.kt        # Kotlin extensions
```

---

## 4. CI/CD Pipeline

### 4.1 Workflow Architecture

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│    Push     │────▶│ Code Quality │────▶│ Build & Test │
│     or      │     │   Checks     │     │              │
│     PR      │     │              │     │              │
└─────────────┘     └─────────────┘     └─────────────┘
                                              │
                                              ▼
                                        ┌─────────────┐
                                        │ Quality Gate│
                                        │   Passed?   │
                                        └──────┬──────┘
                                               │
                                    ┌─────────┴─────────┐
                                    ▼                   ▼
                              ┌─────────┐         ┌─────────┐
                              │  Merge  │         │  Block  │
                              │ Allowed │         │  Merge  │
                              └─────────┘         └─────────┘
```

### 4.2 Quality Gates

| Gate | Tool | Severity | Action on Failure |
|------|------|----------|-------------------|
| Static Analysis | Detekt | Error | Block PR |
| Formatting | KtLint | Error | Block PR |
| Android Lint | lintDebug | Fatal/Error | Block PR |
| Unused Code | Lint + Detekt | Warning | Block PR |
| Build | Gradle | Error | Block PR |
| Tests | JUnit | Failure | Block PR |

### 4.3 Deployment Flow

```
Feature Branch
       │
       ▼
┌──────────────┐
│  Code Review │
│  + CI Checks │
└──────┬───────┘
       │
       ▼
   develop
       │
       ▼
┌──────────────┐
│   Release    │
│   Testing    │
└──────┬───────┘
       │
       ▼
 main/master
       │
       ▼
┌──────────────┐
│   Deploy to  │
│   Play Store │
└──────────────┘
```

---

## 5. API Documentation

### 5.1 QR Generation API

```kotlin
/**
 * Generates a modern styled QR code with customization options
 *
 * @param text Content to encode
 * @param primaryColor Primary color of QR code
 * @param backgroundColor Background color
 * @param gradientColor Secondary color for gradient
 * @param hasGradient Enable gradient effect
 * @param hasLogo Embed center logo
 * @param cornerRadius Corner radius (0f-1f)
 * @return Generated QR bitmap or null on failure
 */
fun generateModernQRCode(
    text: String,
    primaryColor: Int,
    backgroundColor: Int,
    gradientColor: Int,
    hasGradient: Boolean,
    hasLogo: Boolean,
    cornerRadius: Float
): Bitmap?
```

### 5.2 Color Schemes

| Scheme | Primary | Background | Gradient |
|--------|---------|------------|----------|
| Classic | #000000 | #FFFFFF | #333333 |
| Ocean | #0066CC | #F0F8FF | #00CCFF |
| Sunset | #FF6B35 | #FFF5F0 | #FFA500 |
| Forest | #2E7D32 | #F1F8E9 | #66BB6A |
| Berry | #8E24AA | #F3E5F5 | #E040FB |
| Midnight | #1A237E | #E8EAF6 | #3949AB |

---

## 6. Troubleshooting

### 6.1 Common Issues

#### Camera not working
```
Symptom: Black screen or permission denied
Solution:
1. Check Camera permission in AndroidManifest.xml
2. Verify runtime permission handling with Accompanist
3. Ensure device has camera hardware
```

#### QR Code not scanning
```
Symptom: QR codes not detected
Solution:
1. Ensure good lighting conditions
2. Hold device steady
3. Check ML Kit initialization
4. Verify camera preview is active
```

#### Build failures
```
Symptom: Compilation errors
Solution:
1. Run ./gradlew clean
2. Sync project with Gradle files
3. Check for unused imports (Detekt)
4. Verify Kotlin version compatibility
```

### 6.2 Debug Commands

```bash
# Clean build
./gradlew clean

# Run all checks
./gradlew detekt ktlintCheck lintDebug

# Auto-format code
./gradlew ktlintFormat

# Build debug APK
./gradlew assembleDebug

# Run tests
./gradlew testDebugUnitTest

# Generate lint report
./gradlew lintDebug
```

---

## 7. Release Notes

### Version 1.0.0 (Current)
- ✅ QR code scanning with ML Kit
- ✅ QR code generation with 6 themes
- ✅ Gradient and logo customization
- ✅ 16 KB page size support (Android 15+)
- ✅ Material Design 3 UI
- ✅ CI/CD with GitHub Actions

### Upcoming Features
- 📋 QR code history
- 📋 Barcode support (Code 128, EAN, UPC)
- 📋 Cloud sync for generated codes
- 📋 Widget support

---

## 8. Resources

### Internal Links
- [README.md](../README.md) - Main project documentation
- [BRANCH_PROTECTION.md](./BRANCH_PROTECTION.md) - Branch setup guide
- [Code Quality Workflow](./workflows/code-quality.yml) - CI/CD configuration

### External Resources
- [Jetpack Compose Docs](https://developer.android.com/jetpack/compose)
- [CameraX Documentation](https://developer.android.com/training/camerax)
- [ML Kit Barcode Scanning](https://developers.google.com/ml-kit/vision/barcode-scanning)
- [ZXing GitHub](https://github.com/zxing/zxing)

### Training Materials
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [Compose State Management](https://developer.android.com/jetpack/compose/state)
- [Material Design 3](https://m3.material.io/)

---

## 9. Team & Contacts

| Role | Name | GitHub |
|------|------|--------|
| Developer | Ajinkya Aher | @ajinkyaaher |

### Communication Channels
- **GitHub Issues**: Bug reports and feature requests
- **Pull Requests**: Code review and discussions
- **Wiki**: Additional documentation

---

## 10. Appendix

### A. Environment Variables

| Variable | Description | Required |
|----------|-------------|----------|
| `ANDROID_HOME` | Android SDK location | Yes |
| `JAVA_HOME` | JDK location | Yes |

### B. Supported Devices

| Specification | Minimum | Target |
|-------------|---------|--------|
| Android API | 21 (5.0) | 35 (15) |
| RAM | 2 GB | 4 GB |
| Camera | Required | Required |
| Storage | 50 MB | 100 MB |

### C. Compliance

- ✅ Material Design 3 Guidelines
- ✅ Android Accessibility Guidelines
- ✅ Google Play Store Policies
- ✅ 16 KB Page Size (Android 15+)

---

**Last Updated:** March 2024  
**Document Version:** 1.0  
**Maintained by:** @ajinkyaaher
