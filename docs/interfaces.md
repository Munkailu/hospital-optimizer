# docs/interfaces.md

## Hospital Smart Service Operations Optimizer — Data Schema (LOCKED, Day 1)

Published by: Pod 1 — Data, Database & Delivery (Ninson Abigail, Akuffo Samuel, Dogbe Nicole Eyram)
Status: **LOCKED** — do not change field names without notifying Pod 1 first.

### Request

Request {
id: integer
type: string
urgency_level: integer // 1 = low, 5 = critical
submitted_time: string
origin_location_id: integer
destination_location_id: integer
status: string
}


### Location

Location {
id: integer
name: string
type: string
}


### Road

Road {
id: integer
from_location_id: integer
to_location_id: integer
distance: number
}


### Resource

Resource {
id: integer
type: string
availability_status: string
current_location_id: integer
}


### Notes

- Foreign keys: `Request.origin_location_id`, `Request.destination_location_id`, `Road.from_location_id`, `Road.to_location_id`, and `Resource.current_location_id` all reference `Location.id`.
- Output files: `locations.csv`, `roads.csv`, `requests.csv`, `resources.csv`, plus `hospital.db`.
- **CSV extras (Pod 1 internal only, NOT part of the locked schema):** the CSV files also carry `location.floor`, `road.estimated_time`, `resource.resource_name`, and `request.patient_id`/`request.resource_id`. These are dropped when loading into the database (`hospital_optimizer.db`), whose tables match the locked schema above exactly. Other pods may ignore them or read them by column position from the CSVs, but must not depend on them in the database.
- The `hospital_optimizer.db` file is built by Pod 1's loader (`pod1_data_database` Maven project). From the repo root: `mvn -f pod1_data_database/pom.xml exec:java`. It creates the tables, validates and loads the CSVs on a fresh checkout with no external setup. 20 JUnit tests cover the schema, validator, loader and DB manager: `mvn -f pod1_data_database/pom.xml test`.