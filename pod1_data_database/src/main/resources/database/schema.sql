-- ============================================================
-- Pod 1 - Data, Database & Delivery
-- Locked data schema, from docs/interfaces.md (Day 1), plus a
-- Patients table so Requests.patient_id resolves to a real row
-- (matches the original Day 1 ERD) instead of being dropped.
-- Other extra CSV columns (floor, estimated_time, resource_name,
-- resource_id) are still Pod 1-internal only and dropped on load.
--
-- Two log tables from the Day 1 design sketch:
--   Activity_Log  (user actions: logon, request created, ...)
--   Test_Results  (test name/module, runtime, pass/fail, date)
-- ============================================================

CREATE TABLE IF NOT EXISTS Locations (
    id   INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    type TEXT
);

CREATE TABLE IF NOT EXISTS Roads (
    id           INTEGER PRIMARY KEY,
    from_location_id INTEGER NOT NULL,
    to_location_id   INTEGER NOT NULL,
    distance     REAL NOT NULL,
    FOREIGN KEY (from_location_id) REFERENCES Locations (id),
    FOREIGN KEY (to_location_id)   REFERENCES Locations (id)
);

CREATE TABLE IF NOT EXISTS Resources (
    id                  INTEGER PRIMARY KEY,
    type                TEXT NOT NULL,
    availability_status TEXT NOT NULL,
    current_location_id INTEGER NOT NULL,
    FOREIGN KEY (current_location_id) REFERENCES Locations (id)
);

CREATE TABLE IF NOT EXISTS Patients (
    id         INTEGER PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name  TEXT NOT NULL,
    age        INTEGER NOT NULL,
    gender     TEXT NOT NULL,
    location_id INTEGER NOT NULL,
    condition  TEXT NOT NULL,
    FOREIGN KEY (location_id) REFERENCES Locations (id)
);

CREATE TABLE IF NOT EXISTS Requests (
    id                     INTEGER PRIMARY KEY,
    patient_id             INTEGER NOT NULL,
    type                   TEXT NOT NULL,
    urgency_level          INTEGER NOT NULL, -- 1 = low, 5 = critical
    submitted_time         TEXT NOT NULL,
    origin_location_id     INTEGER NOT NULL,
    destination_location_id INTEGER NOT NULL,
    status                 TEXT NOT NULL,
    FOREIGN KEY (patient_id)             REFERENCES Patients (id),
    FOREIGN KEY (origin_location_id)     REFERENCES Locations (id),
    FOREIGN KEY (destination_location_id) REFERENCES Locations (id)
);

CREATE TABLE IF NOT EXISTS Activity_Log (
    id        INTEGER PRIMARY KEY AUTOINCREMENT,
    action    TEXT NOT NULL,
    user      TEXT,
    timestamp TEXT NOT NULL DEFAULT (datetime('now', 'localtime'))
);

CREATE TABLE IF NOT EXISTS Test_Results (
    id      INTEGER PRIMARY KEY AUTOINCREMENT,
    module  TEXT NOT NULL,
    runtime REAL,
    result  TEXT NOT NULL,
    date    TEXT NOT NULL DEFAULT (datetime('now', 'localtime'))
);