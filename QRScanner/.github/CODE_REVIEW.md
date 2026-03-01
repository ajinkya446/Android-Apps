# Code Review Process

This document outlines the automated and manual code review process for the QR Scanner project.

---

## 🤖 Automated Code Review

GitHub Actions automatically performs the following checks on every PR:

### 1. Code Quality Checks (`code-quality.yml`)

| Check | Purpose | Time | Blocks Merge? |
|-------|---------|------|---------------|
| **Detekt** | Unused imports, code style | 2 min | ✅ Yes |
| **KtLint** | Code formatting | 1 min | ✅ Yes |
| **Android Lint** | Unused resources | 3 min | ✅ Yes |
| **Build** | Compilation | 3 min | ✅ Yes |
| **Unit Tests** | Test execution | 2 min | ✅ Yes |
| **Kotlin Warnings** | Compiler checks | 1 min | ✅ Yes |
| **APK Size** | Size limits | 1 min | ✅ Yes |
| **Quality Gate** | Final blocker | Instant | ✅ Yes |

### 2. PR Review Automation (`code-review.yml`)

| Check | Purpose |
|-------|---------|
| **PR Size Check** | Warns if PR is too large (>500 lines) |
| **Review Checklist** | Posts checklist for reviewers |
| **Welcome Message** | Greets first-time contributors |
| **Auto Labeling** | Labels PRs based on content |
| **Stale Review Check** | Notifies if new commits after approval |
| **Merge Conflict Check** | Detects merge conflicts |

---

## 👤 Manual Code Review

### Required Approvals

- **Minimum 1 approval** from a maintainer is required
- **CODEOWNERS** are automatically requested for review
- **All conversations** must be resolved before merge

### Review Checklist (for Reviewers)

#### Functionality
- [ ] Code works as described in PR
- [ ] Edge cases are handled
- [ ] Error handling is appropriate
- [ ] No obvious bugs or issues

#### Code Quality
- [ ] Code is readable and maintainable
- [ ] Naming conventions are followed
- [ ] No unnecessary complexity
- [ ] Proper separation of concerns
- [ ] Comments where needed

#### Testing
- [ ] Unit tests cover the changes
- [ ] Tests are meaningful (not just for coverage)
- [ ] Edge cases are tested
- [ ] UI tests if applicable

#### Performance
- [ ] No obvious performance issues
- [ ] Efficient algorithms used
- [ ] Resource usage is reasonable

#### Security
- [ ] No hardcoded secrets
- [ ] Input validation in place
- [ ] Proper permission handling

#### Documentation
- [ ] PR description is clear
- [ ] Code is self-documenting
- [ ] README updated if needed
- [ ] Breaking changes documented

---

## 🏷️ PR Labeling System

Labels are automatically applied based on:

### By Branch Name
| Branch Prefix | Label |
|--------------|-------|
| `feat/` | `feature` |
| `fix/` | `bugfix` |
| `hotfix/` | `hotfix` |
| `docs/` | `documentation` |
| `refactor/` | `refactoring` |

### By Size
| Lines Changed | Label |
|---------------|-------|
| < 50 | `size: small` |
| 50-300 | `size: medium` |
| > 300 | `size: large` |

### By Content
| Keywords | Label |
|----------|-------|
| test, testing | `tests` |
| dependencies, bump | `dependencies` |
| workflow, ci/cd | `ci/cd` |

---

## 📏 PR Size Guidelines

### Recommended Sizes

- **Small** (< 50 lines): Documentation fixes, single bug fix, small refactor
- **Medium** (50-300 lines): Feature additions, moderate refactors, test additions
- **Large** (300-500 lines): Complex features, architectural changes
- **Too Large** (> 500 lines): Should be split into smaller PRs

### Why Small PRs?

1. **Faster Reviews** - Easier to understand
2. **Fewer Bugs** - Less chance of missing issues
3. **Easier Rollback** - Can revert specific changes
4. **Better Discussion** - Focused feedback

---

## 🔄 Review Process Flow

```
Developer pushes branch
        │
        ▼
┌─────────────────────────────────────────┐
│  Create Pull Request                    │
│  - Fill out template                    │
│  - Link issues                          │
└──────────────────┬──────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────┐
│  GitHub Actions Trigger                 │
│  - Run all code quality checks          │
│  - Post PR size analysis                │
│  - Add labels                           │
│  - Post review checklist                │
└──────────────────┬──────────────────────┘
                   │
        ┌─────────┴─────────┐
        ▼                   ▼
   ❌ Checks Fail      ✅ Checks Pass
        │                   │
        ▼                   ▼
   Fix Issues         Request Review
        │                   │
        └─────────┬─────────┘
                  ▼
        ┌─────────────────────────────────┐
        │  Reviewer Assigned              │
        │  - Receives notification        │
        │  - Reviews checklist          │
        └────────────┬────────────────────┘
                     │
        ┌────────────┼────────────┐
        ▼            ▼            ▼
    ✅ Approve   ❌ Request   💬 Comment
        │         Changes      │
        │            │         │
        └────────────┴─────────┘
                     │
                     ▼
        ┌─────────────────────────────────┐
        │  All Requirements Met?          │
        │  - All checks passing?          │
        │  - Review approved?           │
        │  - No conflicts?              │
        └────────────┬────────────────────┘
                     │
        ┌────────────┴────────────┐
        ▼                        ▼
   ✅ Ready to Merge         ❌ Needs Work
        │                        │
        ▼                        │
   [Merge Button]                 │
   Enabled                        │
                                  │
                           Developer fixes
                                  │
                                  ▼
                           Push new commits
                                  │
                                  ▼
                           Return to "Checks"
```

---

## 🚨 Review Outcomes

### Approve ✅

**When to approve:**
- All automated checks pass
- Code meets quality standards
- Functionality works as expected
- Documentation is adequate

**What to write:**
```
✅ **Approved**

Code looks good! All checks pass and functionality works as expected.

One small suggestion: [optional feedback]

Ready to merge! 🚀
```

### Request Changes ❌

**When to request changes:**
- Automated checks failing
- Code has bugs or issues
- Design doesn't meet requirements
- Missing tests
- Documentation unclear

**What to write:**
```
❌ **Changes Requested**

Great start! Found a few issues:

1. [Specific issue with line reference]
2. [Another issue]
3. [Missing test case]

Please address these and I'll re-review.
```

### Comment 💬

**When to comment:**
- Questions about implementation
- Suggestions for improvement
- Non-blocking concerns
- Need clarification

**What to write:**
```
💬 **Comment**

Had a few questions:

1. Why did you choose approach X over Y?
2. Could we simplify this section?
3. Is this edge case handled?

Not blocking approval, just want to understand.
```

---

## ⏱️ Review Timeline

### For Reviewers

- **Small PRs** (< 50 lines): Review within 24 hours
- **Medium PRs** (50-300 lines): Review within 48 hours
- **Large PRs** (> 300 lines): Review within 72 hours

### For Authors

- **Respond to feedback** within 24 hours
- **Update PR** promptly after changes
- **Resolve conversations** when fixed
- **Re-request review** when ready

---

## 🔧 Common Review Scenarios

### Scenario 1: First-Time Contributor

1. Welcome message posted automatically
2. Extra guidance provided
3. Patient, helpful review
4. Offer to pair if needed

### Scenario 2: Large PR

1. Size warning posted
2. Suggest splitting if possible
3. If can't split, schedule dedicated review time
4. Review in sections

### Scenario 3: Emergency Fix

1. Hotfix branch prefix triggers fast-track
2. Expedited review
3. Skip non-critical checks if needed
4. Post-merge review

### Scenario 4: Stale Review

1. New commits after approval
2. Automatic notification sent
3. Previous approval invalidated
4. Re-review required

---

## 🎯 Best Practices

### For Authors

1. **Keep PRs small** - Under 300 lines when possible
2. **Write clear descriptions** - Explain what and why
3. **Self-review first** - Check your own code
4. **Respond promptly** - Don't let PRs stagnate
5. **Test thoroughly** - Don't rely only on automated tests
6. **Link issues** - Connect PRs to issues

### For Reviewers

1. **Review promptly** - Don't keep authors waiting
2. **Be specific** - Reference lines, suggest code
3. **Be constructive** - Explain why, not just what
4. **Ask questions** - Clarify intent
5. **Approve when ready** - Don't nitpick unnecessarily
6. **Learn from reviews** - Both directions

---

## 🛠️ Troubleshooting

### "Checks aren't running"

**Solution:**
1. Workflow must be on `main` branch
2. Check Actions tab for errors
3. Verify workflow YAML syntax

### "Can't merge even though approved"

**Solution:**
1. Check if all required checks pass
2. Verify no merge conflicts
3. Ensure branch is up to date
4. Check branch protection rules

### "Reviewer not assigned"

**Solution:**
1. CODEOWNERS file may be missing
2. Request review manually
3. Check repository settings

---

## 📚 Related Documentation

- [WORKFLOW_SETUP.md](./WORKFLOW_SETUP.md) - Workflow activation
- [BRANCH_PROTECTION.md](./BRANCH_PROTECTION.md) - Branch setup
- [CONFLUENCE.md](./CONFLUENCE.md) - Project documentation
- [PULL_REQUEST_TEMPLATE.md](../PULL_REQUEST_TEMPLATE.md) - PR template

---

**Maintained by:** @ajinkyaaher  
**Last Updated:** March 2024
