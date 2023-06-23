package decompressor;

import java.io.*;
import java.util.ArrayList;
import tools.BooleanConverters;

class ReadBuffer implements AutoCloseable {

    private BufferedInputStream file;
    private ArrayList<Boolean> buff = new ArrayList();
    private boolean isEmpty;

    ReadBuffer(File file) throws FileNotFoundException {
        this.file = new BufferedInputStream(new FileInputStream(file));
    }

    boolean isEmpty() {
        return isEmpty;
    }

    public void close() throws IOException {
        this.file.close();
    }

    ArrayList<Boolean> readThis(int len) throws IOException {
        while(true) {
            if (this.buff.size() < len) {
                int i;
                if ((i = this.file.read()) != -1) {
                    this.buff.addAll(BooleanConverters.intToBoolList(i));
                    continue;
                }
                isEmpty = true;
                return this.buff;
            }
            ArrayList<Boolean> res = new ArrayList(this.buff.subList(0, len));
            this.buff = new ArrayList(this.buff.subList(len, this.buff.size()));
            return res;
        }
    }
}
