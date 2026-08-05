# GitHub Desktop — Beginner Cheat Sheet
### Hospital Smart Service Optimizer Team

You don't need to know Git commands. GitHub Desktop is a program with buttons — this sheet covers everything you'll actually need this week.

---

## 1. One-Time Setup (do this before Day 1)

1. **Create a GitHub account** (if you don't have one): https://github.com/join
2. **Send your username** to your group leader so you can be added to the repo.
3. **Accept the email invite** you'll get once you're added.
4. **Download GitHub Desktop**: https://desktop.github.com — install it like any normal app.
5. **Sign in** to GitHub Desktop with the same GitHub account.

---

## 2. Getting the Project Onto Your Computer (do this once)

1. Open GitHub Desktop.
2. Click **File → Clone Repository**.
3. Find `hospital-optimizer` in the list (it'll show up since you're a collaborator) and click **Clone**.
4. Choose a folder on your computer you'll remember (e.g. Desktop or Documents).

You now have the whole project on your laptop. You won't need to do this again — from now on you just "pull" updates.

---

## 3. Your Daily Routine (4 steps, every time you sit down to work)

### Step 1 — Get the latest version
Open GitHub Desktop → click **Fetch origin**, then **Pull origin** (if it appears).
This makes sure you're not working on an outdated copy.

### Step 2 — Create your own branch
Top bar → **Current Branch → New Branch**.
Name it like this: `pod<yournumber>-<what-youre-building>-<yourname>`

Examples:
- `pod1-wraparound-queue-nicole`
- `pod5-linkedlist-radiyatu`

Click **Create Branch**. You're now working safely on your own copy — you cannot break anyone else's work from here.

### Step 3 — Do your work, then save it ("commit")
- Save your code files normally in your pod's folder (e.g. `pod1_queues_priority/`), using whatever editor you're using (VS Code, etc.)
- Go back to GitHub Desktop — it will automatically show every file you changed on the left.
- At the bottom left, write a short summary, e.g. `Add wraparound queue with tests`
- Click **Commit to `pod1-wraparound-queue-nicole`**

Do this often — after finishing a small piece, not just once at the very end of the day.

### Step 4 — Push your work online
Click **Push origin** (top bar). This uploads your commits to GitHub so the team can see them.

---

## 4. Submitting Your Work for Review (Pull Request)

Once your piece is working and tested:

1. In GitHub Desktop, click **Create Pull Request** (or go to github.com, open the repo, you'll see a yellow banner — click **Compare & pull request**).
2. Your browser opens on GitHub. Write a short title and description of what you built.
3. Paste in the PR checklist (ask your pod lead if you don't have it).
4. Tag one reviewer from a **different pod**.
5. Click **Create Pull Request**.

Once someone approves it, your pod lead (or the reviewer) will click **Merge**. Your work is now officially part of the project.

---

## 5. If You Get Stuck

- **"I don't see my branch"** → make sure you clicked Create Branch in Step 2 before making changes.
- **Red/yellow warning about conflicts** → stop, don't click anything else, message your pod lead or Munkailu/Lucius (data/tree pods) — this just means two people edited the same lines and needs a quick manual fix.
- **Anything else** → ask in the group chat. No question is too basic this week — a 2-minute question beats a lost afternoon.

---

## 6. What You Never Need to Do

- You will never need to type a Git command in a terminal.
- You will never push directly to `main` — it's locked, so you genuinely cannot break the main project by mistake.
- You don't need to understand merging or rebasing — your pod lead handles that if it comes up.

**That's it. Pull → Branch → Commit → Push → Pull Request. Everything else is optional.**
