package priority;

public class QueueTreeNode<T> {

    private T value;
    private long priority;
    private QueueTreeNode<T> next;
    private QueueTreeNode<T> left;
    private QueueTreeNode<T> right;

    public QueueTreeNode(T value, long priority) {
        this.value = value;
        this.priority = priority;
    }

    public QueueTreeNode(long priority) {
        this.priority = priority;
    }

    public boolean hasNext() {
        return next != null;
    }

    public boolean hasValue() {
        return value != null;
    }

    public void setNext(QueueTreeNode<T> next) {
        this.next = next;
    }

    public void setLeft(QueueTreeNode<T> left) {
        this.left = left;
    }

    public void setRight(QueueTreeNode<T> right) {
        this.right = right;
    }

    public long getPriority() {
        return priority;
    }

    public T getValue() {
        return value;
    }

    public QueueTreeNode<T> getNext() {
        return next;
    }

    public QueueTreeNode<T> getLeft() {
        return left;
    }

    public QueueTreeNode<T> getRight() {
        return right;
    }

    public int compareTo(QueueTreeNode<T> node) {
        return Long.compare(priority, node.priority);
    }
}
