package pod3;

/**
 * Undo/activity log built on top of Stack — each recorded action can be
 * undone in reverse (most recent first), matching the project brief's
 * "stack used as an undo/activity log" requirement.
 */
public class ActivityLog {

    private final Stack<String> log = new Stack<>();

    public void record(String action) {
        log.push(action);
    }

    public String undoLast() {
        return log.pop();
    }

    public String peekLast() {
        return log.peek();
    }

    public boolean isEmpty() {
        return log.isEmpty();
    }

    public int size() {
        return log.size();
    }
}
