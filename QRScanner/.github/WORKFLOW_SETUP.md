# 🚨 WORKFLOW CHECKS NOT SHOWING? READ THIS FIRST!

## The #1 Reason: Workflow Not on Main Branch

**GitHub Actions only runs workflows that exist on the target branch of a PR.**

If you don't see checks on your PR, it's because the workflow file hasn't been pushed to `main`/`master` yet.

---

## ✅ QUICK FIX: 3-Step Activation

### Step 1: Push Workflow to Main (REQUIRED)

```bash
# 1. Make sure you're on the branch with the workflow
git checkout feat/your-feature-branch

# 2. Switch to main and merge the workflow
git checkout main
git merge feat/your-feature-branch --no-ff -m "Add code quality workflow

This adds:
- Detekt analysis for unused imports/code
- KtLint for formatting
- Android Lint for unused resources
- Build verification
- Unit tests
- PR blocking for failed checks"

# 3. Push to main (this one-time direct push is required)
git push origin main
```

### Step 2: Verify First Run

1. Go to **GitHub → Your Repo → Actions tab**
2. You should see "Code Quality Checks" workflow running
3. Wait for it to complete (5-10 minutes first run)

### Step 3: Create Test PR

```bash
# Create a test branch
git checkout -b test/checks-verification

# Make any small change
echo "# Test" >> README.md
git add . && git commit -m "Test workflow checks"

# Push and create PR
git push origin test/checks-verification
```

Then on GitHub:
1. Create PR from `test/checks-verification` → `main`
2. You should NOW see all checks running!

---

## 📋 Checklist: Before You Complain

- [ ] Workflow file is on `main`/`master` branch
- [ ] First workflow run completed on main
- [ ] `app/lint.xml` is on main
- [ ] `app/config/detekt.yml` is on main
- [ ] Created a NEW PR after pushing workflow to main
- [ ] Checks are visible in the PR (may take 1-2 minutes to appear)

**If all above are checked and you still don't see checks, then there's a real issue.**

---

## 🔍 What You Should See

### BEFORE (Workflow not on main):
```
┌─────────────────────────────────────────────┐
│  PR: feat/my-feature → main                 │
│                                             │
│  ❌ NO CHECKS TAB                           │
│  ❌ NO "Checks" section                     │
│  ❌ [Merge pull request] button enabled     │
│     (no checks to block it)                 │
└─────────────────────────────────────────────┘
```

### AFTER (Workflow on main):
```
┌─────────────────────────────────────────────┐
│  PR: feat/my-feature → main                 │
│                                             │
│  ✅ Checks tab visible                      │
│  ✅ 8 checks running...                     │
│     ⏳ Detekt Analysis                      │
│     ⏳ KtLint Check                          │
│     ⏳ Android Lint                          │
│     ⏳ Build & Test                          │
│     ...                                     │
│                                             │
│  ⛔ [Merge pull request] disabled           │
│     (waiting for required checks)           │
└─────────────────────────────────────────────┘
```

### WHEN CHECKS COMPLETE:
```
┌─────────────────────────────────────────────┐
│  PR: feat/my-feature → main                 │
│                                             │
│  ✅ All checks passed                       │
│     ✅ Detekt Analysis                      │
│     ✅ KtLint Check                         │
│     ✅ Android Lint                         │
│     ✅ Build & Test                         │
│     ...                                     │
│                                             │
│  ✅ [Merge pull request] enabled            │
└─────────────────────────────────────────────┘
```

---

## 🧪 Test: Verify Checks Are Working

Create this test PR to verify everything:

```bash
git checkout -b test/verify-checks
echo "test" >> test.txt
git add . && git commit -m "Test: Verify workflow checks"
git push origin test/verify-checks
```

Then create PR on GitHub. You should see:
- ✅ "Checks" tab at the top of the PR
- ✅ 8 workflow jobs running
- ✅ Comment posted by GitHub Actions with results

---

## 🛠️ All Checks That Run

| # | Check | Purpose | Time |
|---|-------|---------|------|
| 1 | **Detekt Analysis** | Unused imports, code quality | 2 min |
| 2 | **KtLint Check** | Code formatting | 1 min |
| 3 | **Android Lint** | Unused resources | 3 min |
| 4 | **Build Verification** | Compilation | 3 min |
| 5 | **Unit Tests** | Run all tests | 2 min |
| 6 | **Kotlin Warnings** | Compiler warnings | 1 min |
| 7 | **APK Size** | Size limits | 1 min |
| 8 | **Quality Gate** | Final blocker | Instant |

---

## 📝 What Each Check Does

### 1. Detekt Analysis
```kotlin
// ❌ FAILS PR - Unused import
import androidx.compose.material.icons.Icons  // Never used

// ❌ FAILS PR - Unused parameter
fun process(text: String, unused: Int) { }  // unused never used

// ❌ FAILS PR - Unused private member
private val neverUsed = "test"
```

### 2. KtLint Check
```kotlin
// ❌ FAILS PR - Wrong formatting
fun process( text:String ) { }

// ✅ PASSES
fun process(text: String) { }
```

### 3. Android Lint
```xml
<!-- ❌ FAILS PR - Unused resource -->
<color name="unused">#FF0000</color>  <!-- Never referenced -->

<!-- ❌ FAILS PR - Unused string -->
<string name="unused">Hello</string>  <!-- Never used -->
```

### 4. Unit Tests
```kotlin
@Test
fun `my test`() {
    // If this fails, PR is blocked
}
```

---

## 🔧 Common Issues

### "I pushed workflow but checks still don't show"

**Cause**: You're looking at an OLD PR created BEFORE workflow was on main

**Solution**: 
1. Create a NEW PR (not the old one)
2. The new PR will have checks

### "Checks run but don't block merge"

**Cause**: Branch protection not configured

**Solution**: See [BRANCH_PROTECTION.md](./BRANCH_PROTECTION.md)

### "One check failed but I can still merge"

**Cause**: Check not marked as "Required" in branch protection

**Solution**: 
1. Go to Settings → Branches
2. Edit protection rule
3. Check "Require status checks to pass"
4. Select all checks as required

---

## 📊 Debug: Check Your Setup

Run this locally:

```bash
# 1. Are workflow files committed?
git ls-files | grep -E "\.github/workflows|lint\.xml|detekt"

# 2. Is workflow on main?
git log main --oneline -- .github/workflows/

# 3. Do tests pass locally?
./gradlew testDebugUnitTest

# 4. Does Detekt pass locally?
./gradlew detekt

# 5. Does Lint pass locally?
./gradlew lintDebug
```

---

## 🎯 One-Liner Fix

If you just want it to work, run this:

```bash
git checkout main && \
git merge --no-ff feat/your-branch-with-workflow -m "Add workflow" && \
git push origin main && \
echo "✅ Workflow pushed to main. Now create a NEW PR to see checks!"
```

---

## 📞 Still Not Working?

Check these in order:

1. **Is Actions enabled?** → Settings → Actions → General → Allow all actions
2. **Is the workflow file valid?** → Check for YAML syntax errors
3. **Are permissions correct?** → Settings → Actions → General → Workflow permissions → Read and write
4. **Try a manual trigger** → Actions tab → Code Quality Checks → Run workflow

---

## ✅ Success Criteria

You've successfully activated checks when:
- [ ] New PRs show "Checks" tab
- [ ] 8 checks run automatically on PR
- [ ] Failed checks block the merge button
- [ ] PR comment shows check results
- [ ] All green = merge enabled

---

**TL;DR: Push workflow to main first. Then create new PR. Done.**
