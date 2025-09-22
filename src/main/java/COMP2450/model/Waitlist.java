import java.util.LinkedList;
import java.util.Queue;

public class Waitlist {
    private final Queue<User> queue = new LinkedList<>();

    public void addToWaitlist(User user) {
        if (!queue.contains(user)) {
            queue.offer(user);
        }
    }

    public User getNextInWaitlist() {
        return queue.peek();
    }

    public User removeNext() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
