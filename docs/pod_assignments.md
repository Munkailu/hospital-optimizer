# Per-Person Task Breakdown

Mapped from the project roadmap onto our actual pod structure.
"Owning" a task = you build the first working version, write its tests, prepare its proof/example, and answer questions about it on defense day.

---

## Pod 1 — Queues, Priority & Smart Assignment
**Members:** Ninson Abigail (L300), Akuffo Samuel (L200), Dogbe Nicole Eyram (L200)

| Person | Main thing they build & explain | Second thing |
|---|---|---|
| Ninson Abigail | Double-ended line and "best one first" priority tool | "Highest priority first" rule, **plus** "grab the best option now" (greedy) method |
| Akuffo Samuel | Stack (LIFO pile) used as undo/activity log | "First come, first served" rule for handling requests |
| Dogbe Nicole Eyram | Line/queue and a wraparound version of it | "Most urgent first" rule for handling requests |

> Note: whoever takes the extra greedy algorithm should coordinate closely with Pod 2 (Planning Ahead / DP) since both methods get compared in the report.

## Pod 2 — Trees, Fast Lookup & Planning Ahead
**Members:** Lucius Jackson (L300), Bartah Mohammed Arimeyaw (L200), Atta-Penkrah Nana Abena Boah (L200)

| Person | Main thing they build & explain | Second thing |
|---|---|---|
| Lucius Jackson | Multi-branch tree used for fast database lookup | Connecting the fast-lookup tool, **plus** "plan ahead for the best result" (DP) method |
| Bartah Mohammed Arimeyaw | Basic search tree | Search tool built on the basic search tree |
| Atta-Penkrah Nana Abena Boah | Self-balancing search tree (harder version) | Comparing basic tree vs self-balancing tree |

## Pod 3 — Data, Database & Delivery
**Members:** Munkailu Yakubu (L300), Okyere Oscar Oroe (L200), Lamptey Glykeria Odarkor (L200)

| Person | Main thing they build & explain | Second thing |
|---|---|---|
| Munkailu Yakubu | Plans the database layout (schema) and data list | Writes what the system should do in plain steps; later leads the report |
| Okyere Oscar Oroe | Builds the hospital data (patients/requests, staff/vehicles) | Checks the data is valid; later builds the on-screen menu |
| Lamptey Glykeria Odarkor | Connects the program to the database | Saves test-run results and logs; later leads testing and speed tests |

> This pod also owns testing, speed-checking, and the report skeleton later in the week.

## Pod 4 — Hashing, Grouping & Maps
**Members:** Irene Adu (L300), Ayeh-Kumi Ferdinand Koram (L200), Awortwe Benedict Kofi (L200)

| Person | Main thing they build & explain | Second thing |
|---|---|---|
| Irene Adu | Map of locations and routes (two different storage methods) | Finding paths and shortest routes across the map |
| Ayeh-Kumi Ferdinand Koram | Hash table (fast lookup) and how it handles clashes | A set/lookup tool built using the hash table |
| Awortwe Benedict Kofi | "Who's connected to whom" grouping tool | Kruskal's method for building a cheap connected network |

## Pod 5 — Lists & Search/Sort
**Members:** Yeboah Owusu Blessings (L300), Alidu Radiyatu (L300), Ibrahim Hidayat (L200)

| Person | Main thing they build & explain | Second thing |
|---|---|---|
| Yeboah Owusu Blessings | Growable list (array-backed list) | Simple step-by-step search |
| Alidu Radiyatu | Linked list and its "walk-through" tool | Two simple sorting methods |
| Ibrahim Hidayat | Speed-testing tool for comparing sort methods | Two faster, smarter sorting methods |

---

## Note on Day-by-Day Plan Mapping

The original 7-day roadmap numbers pods differently (Pod 1 = data pod there). Since our team renumbered pods, use this key when reading the day-by-day plan document:

| Roadmap's Pod # | Roadmap's Focus | = Our Pod |
|---|---|---|
| Pod 1 | Data, Database & Delivery | **Our Pod 3** |
| Pod 2 | Lists & Search/Sort | **Our Pod 5** |
| Pod 3 | Queues, Priority & Smart Assignment | **Our Pod 1** |
| Pod 4 | Trees, Fast Lookup & Planning Ahead | **Our Pod 2** |
| Pod 5 | Hashing, Grouping & Maps | **Our Pod 4** |

Everyone should read the day-by-day plan with this substitution in mind — the content and order of work stays exactly the same, only the labels differ.
