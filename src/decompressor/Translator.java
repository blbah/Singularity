package decompressor;

import java.io.*;

class Translator implements AutoCloseable {

    private CodeTree start;
    private CodeTree point;
    private BufferedOutputStream file;

    Translator(File file, CodeTree tree) throws FileNotFoundException {
        this.start = tree;
        this.point = tree;
        this.file = new BufferedOutputStream(new FileOutputStream(file));
    }

    public void close() throws IOException {
        this.file.close();
    }

    void read(boolean side) throws IOException {
        if (side) {
            this.point = this.point.getOne();
        } else {
            this.point = this.point.getZero();
        }
        if (this.point.getValue() != null) {
            this.file.write(this.point.getValue());
            this.point = this.start;
        }
    }
}
