# Hospital Smart Service Operations Optimizer

DCIT 204/308 — Joint Data Structures & Algorithms Project
Team of 15, split into 5 pods of 3. Built over 7 days.

## Team & Pod Assignments

| Pod | Members | Owns |
|-----|---------|------|
| **Pod 1** | Ninson Abigail (L300), Akuffo Samuel (L200), Dogbe Nicole Eyram (L200) | Data, Database & Delivery |
| **Pod 2** | Lucius Jackson (L300), Bartah Mohammed Arimeyaw (L200), Atta-Penkrah Nana Abena Boah (L200) | Lists & Search/Sort |
| **Pod 3** | Munkailu Yakubu (L300), Okyere Oscar Oroe (L200), Lamptey Glykeria Odarkor (L200) | Queues, Priority & Smart Assignment |
| **Pod 4** | Irene Adu (L300), Ayeh-Kumi Ferdinand Koram (L200), Awortwe Benedict Kofi (L200) | Trees, Fast Lookup & Planning Ahead |
| **Pod 5** | Yeboah Owusu Blessings (L300), Alidu Radiyatu (L300), Ibrahim Hidayat (L200) | Hashing, Grouping & Maps |

Full per-person task breakdown: see `docs/pod_assignments.md`.

## Repository Structure

```
hospital-optimizer/
├── pod1_data_database/         # Pod 1's code
├── pod2_lists_search_sort/            # Pod 2's code
├── pod3_queues_priority/           # Pod 3's code (data, DB, pipeline)
├── pod4_trees_lookup/   # Pod 4's code
├── pod5_hashing_grouping_maps/       # Pod 5's code
├── shared/                       # Common data models / interfaces everyone imports
├── tests/                        # All test files (40+ required across the project)
├── docs/                         # Interfaces doc, pod assignments, report drafts, diagrams
└── README.md
```

**Rule of thumb:** work inside your own pod folder. Only touch `shared/` when you genuinely need to change a common interface — see `CONTRIBUTING.md` for how to do that safely.

## Branching Model

Keep it simple for a 7-day sprint:

- `main` — always working, always demo-able. **Protected: no direct pushes.**
- One branch per person per piece of work, named: `podN-feature-yourname`
  - e.g. `pod3-wraparound-queue-oscar`, `pod5-mergesort-arimeyaw`

No `develop` branch, no long-lived feature branches. Merge to `main` often, in small pieces.

## Getting Started (Day 1, everyone runs this once)

```bash
git clone https://github.com/<org-or-username>/hospital-optimizer.git
cd hospital-optimizer
git checkout -b pod<N>-<feature>-<yourname>
```

See `CONTRIBUTING.md` for the daily workflow, commit message format, and PR checklist.

## Status Tracking

We use GitHub Issues (one per row in `docs/pod_assignments.md`) and a GitHub Project board with columns: **To Do / In Progress / In Review / Done**. Update your issue/card at the end of each day alongside your two-sentence progress note (see `docs/progress_log.md`).

## Definition of Done

A task is only "done" when (see project brief Section 1.3):
1. It works on a normal case, an edge case, and a bad-input case.
2. Tests exist for all three and pass.
3. A first rough proof/example exists if the brief requires one.
4. Someone outside your pod has reviewed it and can describe it in one sentence (this happens via PR review).
