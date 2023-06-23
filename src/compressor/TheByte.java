package compressor;

class TheByte {

    private final int key;
    private long priority;

    TheByte(int key) {
        this.key = key;
        this.priority = 0;
    }

    int getKey() {
        return this.key;
    }

    long getPriority() {
        return this.priority;
    }

    void append() {
        this.priority++;
    }
}