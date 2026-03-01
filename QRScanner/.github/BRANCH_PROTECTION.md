# GitHub Branch Protection Setup

This document explains how to configure branch protection rules to enforce code quality checks and restrict direct pushes.

> 📚 For complete project documentation, see [CONFLUENCE.md](./CONFLUENCE.md)

---

## Quick Setup Guide

### 1. Navigate to Repository Settings
```
Repository → Settings → Branches → Add rule
```

### 2. Required Settings for `main`/`master`

#### Branch name pattern
```
main
```
*(or `master` if using master branch)*

#### Protection Settings

☑️ **Restrict pushes that create files larger than 100 MB**

☑️ **Require a pull request before merging**
- ☑️ Require approvals (minimum 1)
- ☑️ Dismiss stale PR approvals when new commits are pushed
- ☐ Require review from code owners (optional)

☑️ **Require status checks to pass before merging**
- ☑️ Require branches to be up to date before merging
- **Required status checks (search and select all):**
  - `Quality Gate` ⚠️ Most important - final summary
  - `Detekt Analysis` - Kotlin static analysis
  - `KtLint Check` - Code formatting
  - `Android Lint` - Android-specific linting
  - `Unused Code Check` - Dead code detection
  - `Build Verification` - Compilation & tests

☑️ **Require conversation resolution before merging**

☑️ **Do not allow bypassing the above settings** (for admins)

☑️ **Restrict who can push to matching branches**
- Leave empty to allow only maintainers

---

### 3. Settings for `develop` Branch

Create another rule with:

#### Branch name pattern
```
develop
```

Enable the **same settings** as main:
- ☑️ Require a pull request before merging
- ☑️ Require status checks to pass before merging
- ☑️ Restrict pushes to matching branches

---

## Using GitHub Rulesets (Recommended)

GitHub now recommends **Rulesets** instead of traditional branch protection:

### Setup Steps

1. Go to **Settings → Rules → Rulesets**
2. Click **"New ruleset" → "New branch ruleset"**
3. Configure:

#### Ruleset Name
```
Protected Branches
```

#### Enforcement
☑️ **Active** (enable immediately)

#### Targets
- ☑️ Include default branch (main/master)
- Add target → **Include by pattern** → `develop`

#### Branch Rules

☑️ **Restrict deletions**

☑️ **Require a pull request before merging**
- Required approvals: 1
- ☑️ Dismiss stale approvals when new commits are pushed

☑️ **Require status checks before merging**
- ☑️ Require branches to be up to date before merging
- **Search for checks to add:**
  - `Quality Gate`
  - `Detekt Analysis`
  - `KtLint Check`
  - `Android Lint`
  - `Unused Code Check`
  - `Build Verification`

☑️ **Require conversation resolution before merging**

☑️ **Block force pushes**

---

## Why Checks Don't Appear Yet

If you don't see the status checks when setting up protection:

1. **Workflow must be on default branch first**
   ```bash
   git add .github/workflows/code-quality.yml
   git commit -m "Add code quality workflow"
   git push origin main
   ```

2. **Workflow needs to run at least once**
   - Push the workflow file
   - It will run on the main branch
   - After that, checks appear in branch protection settings

---

## Workflow Triggers

The workflow runs on:

### Push Events
- `main`, `master` - Always runs
- `develop` - Always runs
- `feat/*`, `feature/*` - Always runs

### Pull Request Events
- PRs targeting `main`, `master`, `develop`

---

## What Happens on PR

### When You Create a PR:

```
┌─────────────────────────────────────────────┐
│  Developer creates PR to main/develop        │
└──────────────────┬──────────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────────┐
│  GitHub Actions triggers automatically       │
│  ┌─────────┐ ┌─────────┐ ┌─────────┐       │
│  │ Detekt  │ │ KtLint  │ │ Lint    │       │
│  │ (1m)    │ │ (30s)   │ │ (2m)    │       │
│  └─────────┘ └─────────┘ └─────────┘       │
│  ┌─────────┐ ┌─────────┐                    │
│  │ Unused  │ │ Build   │                    │
│  │ Code    │ │ (3m)    │                    │
│  └─────────┘ └─────────┘                    │
└──────────────────┬──────────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────────┐
│  Quality Gate checks all results            │
│  ┌───────────────────────────────────────┐   │
│  │ All checks passed? → Allow merge     │   │
│  │ Any check failed? → Block merge      │   │
│  └───────────────────────────────────────┘   │
└─────────────────────────────────────────────┘
```

---

## Required vs Recommended Checks

### Required (Blocks Merge)
| Check | Purpose | Time |
|-------|---------|------|
| `Quality Gate` | Overall pass/fail | Instant |
| `Detekt Analysis` | Kotlin code quality | ~1 min |
| `KtLint Check` | Code formatting | ~30 sec |
| `Android Lint` | Android issues | ~2 min |
| `Unused Code Check` | Dead code detection | ~1 min |
| `Build Verification` | Compile + tests | ~3 min |

### Recommended (Informational)
| Check | Purpose |
|-------|---------|
| `Security Vulnerability Scan` | Security issues |

---

## Branch Strategy

### Supported Workflow

```
main/master (production)
    │
    ├── PR #1 ──► feat/login ──► develop ──► main
    │
    ├── PR #2 ──► feat/qr-themes ──► develop ──► main
    │
    └── hotfix ──► hotfix/crash-fix ──► main
```

### Branch Naming

| Prefix | Purpose | Example |
|--------|---------|---------|
| `feat/` or `feature/` | New features | `feat/dark-mode` |
| `fix/` or `bugfix/` | Bug fixes | `fix/camera-crash` |
| `hotfix/` | Critical production fixes | `hotfix/security-patch` |
| `refactor/` | Code restructuring | `refactor/camera-module` |
| `docs/` | Documentation only | `docs/api-guide` |

---

## Troubleshooting

### Checks Not Running
**Problem**: Workflow doesn't trigger on PR
**Solution**: Ensure workflow file exists on the **target branch** of the PR

### Checks Not in Settings
**Problem**: Can't find status checks in branch protection
**Solution**: Push workflow to main first, wait for one run to complete

### User Can Still Push to Main
**Problem**: Direct pushes not blocked
**Solution**: 
1. Check "Do not allow bypassing the above settings"
2. Verify user is not an admin with bypass permissions
3. Confirm protection rule is "Active" (Rulesets) or "Enabled" (Branch protection)

### Checks Pass When They Should Fail
**Problem**: Quality issues not blocking merge
**Solution**: 
- Verify `abortOnError = true` in `app/build.gradle`
- Check that `Quality Gate` job is marked as **Required**
- Ensure no ignore annotations suppressing errors

---

## Verification Checklist

After setup, verify everything works:

- [ ] Push workflow file to main/master
- [ ] Workflow runs successfully on main
- [ ] Create test PR to develop
- [ ] All checks appear in PR
- [ ] Checks run and complete
- [ ] Try direct push to main (should fail)
- [ ] Try direct push to develop (should fail)
- [ ] Merge test PR after checks pass

---

## References

- [Project Documentation](./CONFLUENCE.md)
- [Workflow File](./workflows/code-quality.yml)
- [Main README](../README.md)
- [GitHub Docs: About protected branches](https://docs.github.com/en/repositories/configuring-branches-and-merges-in-your-repository/managing-protected-branches/about-protected-branches)
- [GitHub Docs: About rulesets](https://docs.github.com/en/repositories/configuring-branches-and-merges-in-your-repository/managing-rulesets/about-rulesets)

---

**Maintained by:** @ajinkyaaher  
**Last Updated:** March 2024
