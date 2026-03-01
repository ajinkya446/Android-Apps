# GitHub Branch Protection Setup

This document explains how to configure branch protection rules to enforce code quality checks and restrict direct pushes.

## Branch Protection Rules (Required)

You need to manually configure these rules in GitHub repository settings:

### 1. Navigate to Settings
Go to: **Repository** → **Settings** → **Branches**

### 2. Add Protection Rules for `main`/`master`

Click **"Add rule"** and configure:

#### Branch name pattern
```
main
```
Or if using master:
```
master
```

#### Enable these settings:

☑️ **Restrict pushes that create files larger than 100 MB**

☑️ **Require a pull request before merging**
- ☐ Require approvals (1 recommended)
- ☐ Dismiss stale PR approvals when new commits are pushed
- ☐ Require review from code owners
- ☑️ Allow specified actors to bypass PR requirements (for admins)

☑️ **Require status checks to pass before merging**
- ☑️ **Require branches to be up to date before merging**
- Status checks that are required:
  - `Quality Gate` (this is the final summary job from code-quality.yml)
  - `Detekt Analysis`
  - `KtLint Check`
  - `Android Lint`
  - `Unused Code Check`
  - `Build Verification`

☑️ **Require conversation resolution before merging**

☑️ **Require signed commits** (optional, but recommended)

☑️ **Require linear history** (optional)

☑️ **Require deployments to succeed before merging** (if using GitHub deployments)

☑️ **Lock branch** (optional, for maintenance)

☑️ **Do not allow bypassing the above settings** (for admins)

☑️ **Restrict who can push to matching branches**
- Leave empty to allow only maintainers

---

### 3. Add Protection Rules for `develop`

Click **"Add rule"** again:

#### Branch name pattern
```
develop
```

#### Enable the same settings as `main`:

☑️ **Require a pull request before merging**

☑️ **Require status checks to pass before merging**
- Same status checks as main branch

☑️ **Restrict who can push to matching branches**

---

## Required Status Checks (from workflow)

Make sure these check names match exactly in GitHub settings:

1. `Quality Gate` - Overall quality gate (required)
2. `Detekt Analysis` - Kotlin static analysis
3. `KtLint Check` - Code formatting
4. `Android Lint` - Android-specific linting
5. `Unused Code Check` - Dead code detection
6. `Build Verification` - Compilation and tests
7. `Security Vulnerability Scan` - Security checks

---

## Rulesets (Alternative - Recommended)

GitHub now recommends using **Rulesets** instead of branch protection rules:

### Setup via Rulesets

1. Go to **Repository** → **Settings** → **Rules** → **Rulesets**
2. Click **"New ruleset"** → **"Import a ruleset"** or **"New branch ruleset"**

### Create Ruleset for Protected Branches

**Ruleset Name:** `Protected Branches`

**Enforcement:** Active

**Targets:**
- Add target → **Include default branch** (main/master)
- Add target → **Include by pattern** → `develop`

**Rules:**
☑️ **Restrict deletions**
☑️ **Require linear history** (optional)
☑️ **Require deployment environment** (if applicable)
☑️ **Require signed commits** (optional)
☑️ **Require a pull request before merging**
   - Required approvals: 1
   - Dismiss stale approvals: Yes
☑️ **Require status checks before merging**
   - Checks:
     - `Quality Gate`
     - `Detekt Analysis`
     - `KtLint Check`
     - `Android Lint`
     - `Build Verification`
☑️ **Require conversation resolution before merging**
☑️ **Block force pushes**

---

## Testing the Setup

After configuration, test by:

1. **Try direct push to main/develop** → Should be rejected
2. **Create a PR with code issues** → Checks should fail
3. **Create a PR with clean code** → All checks should pass

---

## Troubleshooting

### Status checks not appearing?
- Workflow must run at least once on the default branch
- Push the workflow file to main first

### Checks passing when they should fail?
- Verify `abortOnError` is set in `build.gradle`
- Check Detekt and KtLint configurations

### Workflow not triggering?
- Check the `on:` trigger conditions in workflow file
- Ensure PR targets correct branch

---

## Quick Reference

| Setting | main/master | develop |
|---------|-------------|---------|
| PR Required | ✅ Yes | ✅ Yes |
| Approvals | 1 | 1 |
| Status Checks | All | All |
| Direct Push | ❌ No | ❌ No |
| Force Push | ❌ Blocked | ❌ Blocked |

---

**Note:** These settings require GitHub admin access to configure.
