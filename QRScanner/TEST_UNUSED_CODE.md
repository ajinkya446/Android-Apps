# Unused Code Added for Testing Code Review Checks

This branch contains intentionally added unused code to test the GitHub Actions code review automation.

## What Was Added

### 1. Unused Imports (Detekt Check)
**File:** `app/src/main/java/com/ajinkya/qrscanner/GenerateQRActivity.kt`

Added unused imports at the top of the file:
- `java.util.Date` - Not used anywhere
- `java.text.SimpleDateFormat` - Not used
- `android.util.Log` - Not used
- `androidx.compose.material.icons.filled.Home` - Not used
- `androidx.compose.material.icons.filled.Settings` - Not used

### 2. Unused Parameters (Detekt Check)
**File:** `app/src/main/java/com/ajinkya/qrscanner/GenerateQRActivity.kt`

Added to `generateModernQRCode()` function:
- `unusedParameter: String = "test"`
- `anotherUnusedParam: Int = 0`

### 3. Unused Variables (Detekt Check)
**File:** `app/src/main/java/com/ajinkya/qrscanner/GenerateQRActivity.kt`

Added inside `generateModernQRCode()`:
- `val unusedVariable = "This is never used"`
- `val anotherUnusedVariable = 12345`
- `val unusedDate = Date()`

### 4. Unused Resources (Android Lint Check)

#### Colors (`res/values/colors.xml`)
- `unusedColor1` through `unusedColor5` - 5 unused colors

#### Strings (`res/values/strings.xml`)
- `unused_string_1` through `unused_string_5` - 5 unused strings

#### Dimensions (`res/values/dimens.xml`)
- 10 unused dimension values including:
  - `unused_dimen_small`, `unused_dimen_medium`, `unused_dimen_large`
  - `unused_text_small`, `unused_text_medium`, `unused_text_large`
  - `unused_padding`, `unused_margin`, `unused_elevation`, `unused_corner_radius`

#### Integers (`res/values/integers.xml`)
- 6 unused integer values

#### Drawables
- `unused_background.xml` - Shape drawable never referenced
- `unused_icon.xml` - Vector drawable never referenced

### 5. Unused Class File (Comprehensive Detekt Check)
**File:** `app/src/main/java/com/ajinkya/qrscanner/UnusedCodeForTesting.kt`

Complete class with multiple unused elements:
- Unused property
- Unused companion object property
- Unused functions with unused parameters
- Unused local variables
- Unused lambda expressions
- Unused collections and operations

## Expected Check Results

When you create a PR with this branch, you should see these checks FAIL:

| Check | Expected Result | Issue Count |
|-------|-----------------|-------------|
| **Detekt Analysis** | ❌ FAIL | 10+ unused imports, parameters, variables |
| **KtLint** | ✅ PASS | Formatting should be OK |
| **Android Lint** | ❌ FAIL | 20+ unused resources |
| **Build** | ✅ PASS | Code should compile |
| **Quality Gate** | ❌ FAIL | Overall blocker |

## After Testing

Once you've verified the code review checks work:

1. **Revert the changes**:
   ```bash
   git checkout main -- .
   git commit -m "Remove test unused code"
   ```

2. **Or delete this branch** and create a new clean PR

3. **Keep the workflows** - They're working correctly!

## How to See the Checks

1. Push this branch to GitHub
2. Create a PR to `main`
3. Wait 1-2 minutes
4. Check the PR page - you should see:
   - ❌ "Checks" tab with failing checks
   - 💬 Comments from GitHub Actions
   - ⛔ Blocked merge button

## Cleanup Commands

```bash
# Delete the test files
git rm app/src/main/java/com/ajinkya/qrscanner/UnusedCodeForTesting.kt
git rm app/src/main/res/values/dimens.xml
git rm app/src/main/res/values/integers.xml
git rm app/src/main/res/drawable/unused_background.xml
git rm app/src/main/res/drawable/unused_icon.xml

# Revert changes to modified files
git checkout main -- app/src/main/java/com/ajinkya/qrscanner/GenerateQRActivity.kt
git checkout main -- app/src/main/res/values/colors.xml
git checkout main -- app/src/main/res/values/strings.xml
git rm TEST_UNUSED_CODE.md

git commit -m "Remove test unused code after verifying checks work"
```

---

**Remember:** This branch is for TESTING ONLY. Do not merge it to production!
