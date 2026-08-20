# Pod 1 — Data, Database & Delivery

Builds the SQLite database (locked schema from `docs/interfaces.md`) from the CSV
dataset, validates the data, and publishes the two log tables.

**Members:** Ninson Abigail (L300), Akuffo Samuel (L200), Dogbe Nicole Eyram (L200)

## Layout

- `day1/` — Day 1 deliverables (system description, ERD, locked schema, student-ID numbers)
- `day2/data/` — the four CSVs: 50 locations, 100 roads, 30 resources, 300 requests
- `day2/docs/` — dataset note
- `day2/database/` — generated `hospital_optimizer.db` (gitignored, created by the loader)
- `src/main/java/` — `app.Main`, `database.*`, `loader.CSVLoader`, `validation.Validator`, `model.*`
- `src/main/resources/database/schema.sql` — table-creation SQL (locked schema + log tables)
- `src/test/java/` — 20 JUnit 5 tests

## Build, Test, Run

Java 17 + Maven required.

```bash
# from the repo root
mvn -f pod1_data_database/pom.xml test          # run the 20 tests
mvn -f pod1_data_database/pom.xml exec:java     # create DB, validate + load CSVs
```

Running `exec:java` creates the tables, validates every CSV (plus foreign-key
references), loads all 480 records into the locked schema and writes one
`Activity_Log` row per step plus a `Test_Results` PASS row.

## Locked Schema (summary)

Tables: `Locations`, `Roads`, `Resources`, `Requests`, plus the log tables
`Activity_Log` and `Test_Results`. The exact column list is in
`docs/interfaces.md` — no `Patients` table, no `floor` / `estimated_time` /
`resource_name` / `patient_id` / `resource_id` columns. Those extras exist only
in the CSVs and are dropped on load.