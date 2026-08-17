-- ============================================================
-- Pod 1 - Data, Database & Delivery
-- Locked data schema, from docs/interfaces.md (Day 1).
-- Tables exactly match the locked schema: no patients table,
-- no floor / estimated_time / resource_name / patient_id /
-- resource_id columns. Those live only in the CSV files as
-- Pod 1's internal extras and are dropped on load.
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

CREATE TABLE IF NOT EXISTS Requests (
    id                     INTEGER PRIMARY KEY,
    type                   TEXT NOT NULL,
    urgency_level          INTEGER NOT NULL, -- 1 = low, 5 = critical
    submitted_time         TEXT NOT NULL,
    origin_location_id     INTEGER NOT NULL,
    destination_location_id INTEGER NOT NULL,
    status                 TEXT NOT NULL,
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