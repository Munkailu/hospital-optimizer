# Contributing Workflow

## Daily Routine

1. **Start of day** — pull the latest `main`:
   ```bash
   git checkout main
   git pull origin main
   ```
2. **Create a branch** for today's task:
   ```bash
   git checkout -b pod<N>-<short-feature-name>-<yourname>
   ```
   Example: `pod2-binary-search-jackson`

3. **Work, commit often** — small commits, not one giant dump at the end:
   ```bash
   git add .
   git commit -m "[Pod2] Add binary search with sorted-input check"
   ```

4. **Push your branch**:
   ```bash
   git push origin pod<N>-<short-feature-name>-<yourname>
   ```

5. **Open a Pull Request** into `main` on GitHub.
   - Fill in the PR checklist (below).
   - Tag **one reviewer from a different pod**.
   - Link the related GitHub Issue.

6. **Reviewer** (different pod) reads the code + tests, leaves comments or approves. This is the "someone outside your pod checks it" rule from the project brief — it's enforced by GitHub, not memory.

7. **Merge** once approved. Delete the branch after merging.

8. **End of day** — write your two-sentence progress note in `docs/progress_log.md` and update your GitHub Issue/card.

## Commit Message Convention

```
[PodN] Short description of what changed
```
Examples:
```
[Pod1] Add urgent-jump-the-line rule with tests
[Pod3] Fix duplicate ID check in data validator
[Pod4] Add self-balancing tree rebalance example
```

## Pull Request Checklist

Copy this into every PR description:

- [ ] Code works on a normal case, an edge case, and a bad-input case
- [ ] Tests written for all three, and they pass
- [ ] If required by the brief: a first draft of the proof/example exists
- [ ] Any changed interface is updated in `docs/interfaces.md`
- [ ] Reviewed by someone from a different pod

## Working in `shared/`

`shared/` holds common data models (e.g. what a "request" or "location" object looks like) that every pod imports. Because everyone depends on it:

- Keep changes here small and announce them in the WhatsApp/Slack group before opening the PR.
- Get a review fast — don't let a `shared/` PR sit overnight.
- If you need a new field or a new shared helper, check `docs/interfaces.md` first — someone else may already be about to add it.

## Avoiding Merge Conflicts

- Stay inside your own pod folder as much as possible — this alone prevents most conflicts across 15 people.
- Pull `main` before starting new work each day.
- If two people must touch the same file, talk first — don't just push and hope.
