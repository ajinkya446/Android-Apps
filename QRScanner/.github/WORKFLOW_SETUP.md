# GitHub Actions Workflow Setup & Troubleshooting

## ⚠️ Problem: Checks Not Showing in PR

If you don't see checks when creating a PR, this is likely because **the workflow file is not yet on your default branch**.

### Why This Happens

GitHub only runs workflows that exist on the **target branch** (main/master) of the PR.

```
Your PR: feat/my-feature ───────► main (doesn't have workflow yet)
                                   │
                                   └── Workflow file not here = No checks run
```

### ✅ Solution: Push Workflow to Main First

**Step 1: Commit and push the workflow file directly to main**

```bash
# Add the workflow files
git add .github/workflows/code-quality.yml
git add app/lint.xml
git add app/config/detekt.yml

# Commit
git commit -m "Add code quality workflow for PR checks

- Detekt analysis for unused imports/code
- KtLint for code formatting
- Android Lint for unused resources
- Build verification with tests
- Quality gate to block PRs with issues"

# Push to main/master (direct push required this one time)
git push origin main
```

**Step 2: Verify workflow runs on main**
- Go to **Actions** tab in your GitHub repository
- You should see "Code Quality Checks" workflow running
- Wait for it to complete (first run takes ~10-15 minutes)

**Step 3: Create a test PR**
- Create a new branch: `git checkout -b test/workflow-check`
- Make any small change (add a comment)
- Push and create PR to `main`
- Checks should now appear!

---

## 🔍 All Checks That Run on PR

### 1. Detekt Analysis (Kotlin Static Analysis)
**Purpose:** Find code quality issues and unused code  
**Time:** ~2 minutes  
**Fails on:**
- ❌ Unused imports
- ❌ Unused parameters
- ❌ Unused private members
- ❌ Unused private properties
- ❌ Complex methods
- ❌ Style violations

**Example failure:**
```
❌❌❌ FOUND 3 UNUSED IMPORTS ❌❌❌
import androidx.compose.material.icons.Icons  // Never used
import java.util.Date  // Never used
import android.graphics.Color  // Never used
```

### 2. KtLint Check (Code Formatting)
**Purpose:** Ensure consistent code formatting  
**Time:** ~1 minute  
**Fails on:**
- ❌ Missing/extra spaces
- ❌ Incorrect indentation
- ❌ Line length > 120 chars
- ❌ Missing trailing commas

**Example failure:**
```
❌❌❌ KTLINT FAILED - Formatting issues found ❌❌❌
Run './gradlew ktlintFormat' locally to auto-fix these issues
```

### 3. Android Lint (Unused Resources)
**Purpose:** Find unused Android resources and potential issues  
**Time:** ~3 minutes  
**Fails on:**
- ❌ Unused drawables
- ❌ Unused strings
- ❌ Unused colors
- ❌ Unused layouts
- ❌ Unused IDs
- ❌ Unused attributes

**Example failure:**
```
❌❌❌ FOUND 5 UNUSED CODE/RESOURCE ISSUES ❌❌❌
Breakdown:
  - Unused Resources: 3
  - Unused Attributes: 2
```

### 4. Build Verification
**Purpose:** Ensure code compiles and tests pass  
**Time:** ~5 minutes  
**Fails on:**
- ❌ Compilation errors
- ❌ Test failures
- ❌ Missing dependencies

### 5. Kotlin Warnings Check
**Purpose:** Catch compiler warnings  
**Time:** ~2 minutes  
**Fails on:**
- ❌ Unused import warnings
- ❌ Deprecation warnings
- ❌ Other compiler warnings

### 6. Quality Gate (Final Check)
**Purpose:** Block merge if ANY check fails  
**Time:** Instant  
**Result:**
- ✅ All checks passed → PR can be merged
- ❌ Any check failed → PR blocked until fixed

---

## 📋 Checklist to Activate Workflows

- [ ] Workflow file pushed to `main`/`master`
- [ ] First workflow run completed on main
- [ ] Lint config file (`app/lint.xml`) on main
- [ ] Detekt config (`app/config/detekt.yml`) on main
- [ ] Create test PR to verify checks appear
- [ ] Verify branch protection rules configured

---

## 🔧 Common Issues & Fixes

### Issue 1: "No checks showing on PR"
**Cause:** Workflow not on main branch  
**Fix:** Push workflow to main first (see Solution above)

### Issue 2: "Checks stuck at 'Expected'"
**Cause:** Branch protection requires checks that don't exist  
**Fix:** Wait for first workflow run on main to complete

### Issue 3: "Workflow runs but PR not blocked"
**Cause:** Branch protection not configured  
**Fix:** See [BRANCH_PROTECTION.md](./BRANCH_PROTECTION.md)

### Issue 4: "Checks pass but should fail"
**Cause:** Using `--continue` or `|| true` in workflow  
**Fix:** Already fixed in current workflow - uses strict mode

---

## 🚀 Quick Commands

### Run all checks locally before PR:
```bash
./gradlew clean detekt ktlintCheck lintDebug assembleDebug testDebugUnitTest
```

### Fix auto-fixable issues:
```bash
./gradlew ktlintFormat
```

### Check specific issues:
```bash
# Unused imports
./gradlew detekt

# Unused resources  
./gradlew lintDebug

# Formatting
./gradlew ktlintCheck
```

---

## 📊 Workflow Status Summary

| Check | Status | Required |
|-------|--------|----------|
| Detekt Analysis | ✅ Ready | YES |
| KtLint Check | ✅ Ready | YES |
| Android Lint | ✅ Ready | YES |
| Build & Test | ✅ Ready | YES |
| Kotlin Warnings | ✅ Ready | YES |
| Quality Gate | ✅ Ready | YES |

**All checks are configured and ready to run once pushed to main!**

---

## 📝 Example: What a PR with Checks Looks Like

### Before (No workflow on main):
```
┌─────────────────────────────────────────────────┐
│  PR: feat/my-feature → main                     │
│                                                 │
│  [Merge pull request]  ←── Button enabled       │
│  (No checks - workflow not on main)             │
└─────────────────────────────────────────────────┘
```

### After (Workflow on main):
```
┌─────────────────────────────────────────────────┐
│  PR: feat/my-feature → main                     │
│                                                 │
│  Checks:                                        │
│  ✅ Detekt Analysis - Passed                    │
│  ✅ KtLint Check - Passed                       │
│  ✅ Android Lint - Passed                       │
│  ✅ Build & Test - Passed                       │
│  ✅ Quality Gate - Passed                       │
│                                                 │
│  [Merge pull request]  ←── Button enabled       │
└─────────────────────────────────────────────────┘
```

### With Issues:
```
┌─────────────────────────────────────────────────┐
│  PR: feat/my-feature → main                     │
│                                                 │
│  Checks:                                        │
│  ❌ Detekt Analysis - Failed                    │
│     Found 3 unused imports                      │
│  ✅ KtLint Check - Passed                       │
│  ⚠️ Android Lint - 2 warnings                   │
│  ❌ Build & Test - Failed                       │
│                                                 │
│  ❌ Quality Gate - Failed                       │
│                                                 │
│  [Merge pull request]  ←── Button DISABLED      │
│  (Required checks must pass before merging)     │
└─────────────────────────────────────────────────┘
```

---

## 🔗 Related Documentation

- [BRANCH_PROTECTION.md](./BRANCH_PROTECTION.md) - How to configure branch protection
- [CONFLUENCE.md](./CONFLUENCE.md) - Full project documentation
- [Main README](../README.md) - Project overview

---

**Need Help?**
- Check the Actions tab in your GitHub repo for workflow runs
- Review logs if a check fails
- Make sure workflow file is on main branch first!
