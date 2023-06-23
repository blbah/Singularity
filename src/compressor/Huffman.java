package compressor;

import priority.QueueTreeNode;
import priority.QueueTree;

import java.util.ArrayList;

public class Huffman {

    static ArrayList<Boolean>[] code(ArrayList<TheByte> bytesData) {
        var queue = new QueueTree<>() {
            public ArrayList[] codes = new ArrayList[256];

            @Override
            public void doIt(ArrayList<Boolean> way, QueueTreeNode<Object> node) {
                codes[(Integer) node.getValue()] = (ArrayList<Boolean>) way.clone();
            }
        };
        for (TheByte theByte : bytesData) {
            if (theByte.getPriority() != 0) {
                queue.put(theByte.getKey(), theByte.getPriority());
            }
        }
        for (int i = 0; i < 256; i++) {
            if (queue.codes[i] == null) {
                queue.codes[i] = new ArrayList<>();
            }
        }
        queue.stick();
        queue.walk();
        return queue.codes;
    }
}
