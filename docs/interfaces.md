# Who-Needs-What: Shared Interfaces

Before you start building something another pod will use, write it here in plain words:
what it does, what information it needs (inputs), and what it gives back (outputs).
If another pod needs your tool later, they read this instead of guessing.

Update this file in the **same PR** as the code change it describes.

---

## Template (copy this block for each new tool/interface)

### `tool_or_function_name`
- **Owned by:** Pod N — Name
- **What it does:** (one or two plain-English sentences)
- **Input:** (what you pass in, and its shape/type)
- **Output:** (what you get back, and its shape/type)
- **Edge cases handled:** (empty input, invalid input, etc.)
- **Used by:** (which pods/tools depend on this)

---

## Pod 3 Queues, Priority & Smart Assignment) — Core Data Shapes

### `Request` object
- **Owned by:** Pod 3
- **What it does:** Represents one service request (patient transfer, pharmacy run, equipment delivery)
- **Fields:** `id`, `type`, `urgency_level`, `submitted_time`, `origin_location`, `destination_location`, `status`
- **Used by:** Pod 1 (queues/priority), Pod 2 (search/sort), Pod 4 (hashing/maps)

### `Location` object
- **Owned by:** Pod 3
- **Fields:** `id`, `name`, `type` (ward/pharmacy/etc.), `coordinates_or_zone`
- **Used by:** Pod 4 (maps/routes)

### `Resource` object
- **Owned by:** Pod 3
- **Fields:** `id`, `type` (ambulance/porter/nurse), `availability_status`, `current_location`
- **Used by:** Pod 1 (assignment), Pod 4 (routing)

*(Pod 3: replace/expand these once your real schema is finalized on Day 1–2.)*

---

## Pod 1 ((Data, Database & Delivery)

### `PriorityQueue`
- **Owned by:** Pod 1
- **What it does:** Add a request; always remove the most urgent one first
- **Input:** `Request` objects
- **Output:** next `Request` to handle
- **Used by:** Pod 4 (route-finding / network-building needs a priority tool)

*(Add your queue, wraparound queue, and deque interfaces here as you build them.)*

---

## Pod 2 (Lists & Search/Sort)

*(Add basic tree, self-balancing tree, and multi-branch tree interfaces here.)*

---

## Pod 4 (Trees, Fast Lookup & Planning Ahead)

*(Add hash table, grouping/union-find, and map storage interfaces here.)*

---

## Pod 5 (Hashing, Grouping & Maps)

*(Add growable list, linked list, search, and sort interfaces here.)*
