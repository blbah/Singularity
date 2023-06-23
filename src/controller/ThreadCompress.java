package controller;

import compressor.Compressor;

import java.io.File;
import java.io.IOException;

public class ThreadCompress extends Thread{

    private final File inFile;
    private final File outFile;
    private IOException result;

    public ThreadCompress(File inFile, File outFile) {
        this.inFile = inFile;
        this.outFile = outFile;
    }

    public IOException getResult() {
        return result;
    }

    @Override
    public void run() {
        try {
            Compressor.compress(inFile, outFile);
        } catch (IOException e) {
            result = e;
        }
    }
}
