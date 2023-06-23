package controller;

import decompressor.Decompressor;

import java.io.File;
import java.io.IOException;

public class ThreadDecompress extends Thread {

    private final File inFile;
    private final File outFile;
    private IOException result;

    public ThreadDecompress(File inFile, File outFile) {
        this.inFile = inFile;
        this.outFile = outFile;
    }

    public IOException getResult() {
        return result;
    }

    @Override
    public void run() {
        try {
            Decompressor.decompress(inFile, outFile);
        } catch (IOException e) {
            result = e;
        }
    }

    public void clean() {
        inFile.delete();
        outFile.renameTo(new File(outFile.getAbsolutePath()
                .replace(".lol", "")));
    }
}
