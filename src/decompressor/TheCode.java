package decompressor;

import java.util.ArrayList;

class TheCode {

    private final int key;
    private final ArrayList<Boolean> code;

    TheCode(int key, ArrayList<Boolean> code) {
        this.key = key;
        this.code = code;
    }

    int getKey() {
        return this.key;
    }

    ArrayList<Boolean> getCode() {
        return this.code;
    }

    boolean isBiggerThan(TheCode another) {
        int minSize = this.code.size() < another.code.size() ? this.code.size() : another.code.size();
        for(int i = 0; i < minSize; ++i) {
            if (this.code.get(i) && !another.code.get(i)) {
                return true;
            }
            if (!this.code.get(i) && another.code.get(i)) {
                return false;
            }
        }
        return true;
    }
}
