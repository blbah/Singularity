package priority;

import java.util.ArrayList;

public class QueueTree<T> {

    private QueueTreeNode<T> root;

    public QueueTree() {
    }

    public void put(T value, long priority) {
        QueueTreeNode<T> node = new QueueTreeNode<T>(value, priority);
        put(node);
    }

    private void put(QueueTreeNode<T> node) {
        if (root == null) {
            root = node;
        }
        else if (root.compareTo(node) > 0) {
            node.setNext(root);
            root = node;
        }
        else {
            QueueTreeNode<T> curr = root;
            while (curr.compareTo(node) < 0 && curr.hasNext()
                    && curr.getNext().compareTo(node) < 0) {
                curr = curr.getNext();
            }
            node.setNext(curr.getNext());
            curr.setNext(node);
        }
    }

    public void stick() {
        boolean res;
        while ((res = step())) {
        }
    }

    private boolean step() {
        if (!root.hasNext()) {
            return false;
        }
        QueueTreeNode<T> node = new QueueTreeNode<>(root.getPriority() + root.getNext().getPriority());
        node.setLeft(root);
        node.setRight(root.getNext());
        root = root.getNext().getNext();
        if (root != null) {
            put(node);
        }
        else {
            root = node;
        }
        return true;
    }

    public void walk() {
        walkIt(new ArrayList<Boolean>(), root);
    }

    private void walkIt(ArrayList<Boolean> way, QueueTreeNode<T> node) {
        if (node.hasValue()) {
            doIt(way, node);
        }
        if (node.getLeft() != null) {
            way.add(false);
            walkIt(way, node.getLeft());
            way.remove(way.size() - 1);
        }
        if (node.getRight() != null) {
            way.add(true);
            walkIt(way, node.getRight());
            way.remove(way.size() - 1);
        }
    }

    public void doIt(ArrayList<Boolean> way, QueueTreeNode<T> node) {
        System.out.println(node.getValue().toString());
    }
}
