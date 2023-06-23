package compressor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import tools.BooleanConverters;

public class Compressor {
    
    private Compressor() {
    }
    
    public static void compress(File input, File output) throws IOException {
        try(BufferedInputStream inCodes = new BufferedInputStream(
                new FileInputStream(input));
                BufferedInputStream inFile = new BufferedInputStream(
                        new FileInputStream(input));
                WriteBuffer buff = new WriteBuffer(output)) {
            rewrite(inFile, buff, code(count(inCodes)));
        }
    }
    
    private static void rewrite(BufferedInputStream inFile, WriteBuffer buff,
                                ArrayList<Boolean>[] codes) throws IOException {
        int i;
        for(i = 0; i < 256; i++) {
            buff.writeThis(BooleanConverters.intToBoolList(codes[i].size()));
            buff.writeThis(codes[i]);
        }
        while(true) {
            if((i = inFile.read()) != -1) {
                buff.writeThis(codes[i]);
            }
            else {
                if(!buff.isEmpty()) {
                    ArrayList<Boolean> end = new ArrayList<>(Arrays.asList(
                            true, true, true, true, true, true, true));
                    buff.writeThis(end);
                }
                break;
            }
        }
    }
    
    private static ArrayList<TheByte> count(BufferedInputStream inFile)
            throws IOException {
        ArrayList<TheByte> bytesData = new ArrayList<>();
        int i;
        for(i = 0; i < 256; i++) {
            bytesData.add(new TheByte(i));
        }
        while((i = inFile.read()) != -1) {
            bytesData.get(i).append();
        }
        return(bytesData);
    }
    
    private static ArrayList<Boolean>[] code(ArrayList<TheByte> bytesData) {
        return(Huffman.code(bytesData));
    }
}