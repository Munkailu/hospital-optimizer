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