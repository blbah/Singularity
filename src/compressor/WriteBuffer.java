package compressor;

import java.io.*;
import java.util.ArrayList;
import tools.BooleanConverters;

class WriteBuffer implements AutoCloseable {

    private BufferedOutputStream file;
    private ArrayList<Boolean> buff = new ArrayList();

    WriteBuffer(File file) throws FileNotFoundException {
        this.file = new BufferedOutputStream(new FileOutputStream(file));
    }

    boolean isEmpty() {
        return this.buff.isEmpty();
    }

    public void close() throws IOException {
        this.file.close();
    }

    void writeThis(ArrayList<Boolean> bools) throws IOException {
        this.buff.addAll(bools);
        while (this.buff.size() >= 8) {
            this.file.write(BooleanConverters.boolArrayToInt(new ArrayList(this.buff.subList(0, 8))));
            this.buff.subList(0, 8).clear();
        }
    }
}
