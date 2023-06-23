package decompressor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import tools.BooleanConverters;

public class Decompressor {

    private Decompressor() {
    }

    public static void decompress(File input, File output) throws IOException {
        try (ReadBuffer buff = new ReadBuffer(input)) {
            rewrite(output, buff, codeTreeRec(sortCodes(readCodes(buff))));
        }
    }

    private static void rewrite(File output, ReadBuffer buff, CodeTree tree)
            throws IOException {
        try (Translator translator = new Translator(output, tree)) {
            while (!buff.isEmpty()) {
                ArrayList<Boolean> line = buff.readThis(11000000);
                for (Boolean bool : line) {
                    translator.read(bool);
                }
            }
        }
    }

    private static CodeTree codeTreeRec(ArrayList<TheCode> codes) {
        CodeTree tree = new CodeTree();
        if (codes.size() == 1) {
            tree.setValue((codes.get(0)).getKey());
        } else {
            int i;
            for(i = 0; i < codes.size() && !(codes.get(i)).getCode().get(0); ++i) {
            }
            codes.forEach((code) -> code.getCode().remove(0));
            CodeTree zero = codeTreeRec(new ArrayList(codes.subList(0, i)));
            CodeTree one = codeTreeRec(new ArrayList(codes.subList(i, codes.size())));
            tree.setZero(zero);
            tree.setOne(one);
        }
        return tree;
    }

    private static ArrayList<TheCode> sortCodes(ArrayList<TheCode> codes) {
        codes.removeIf(o -> o.getCode().isEmpty());
        for(int i = 0; i < codes.size() - 1; ++i) {
            for(int j = 0; j < codes.size() - i - 1; ++j) {
                if ((codes.get(j)).isBiggerThan(codes.get(j + 1))) {
                    TheCode temp = codes.get(j);
                    codes.set(j, codes.get(j + 1));
                    codes.set(j + 1, temp);
                }
            }
        }
        return codes;
    }

    private static ArrayList<TheCode> readCodes(ReadBuffer buff) throws IOException {
        ArrayList<TheCode> bytesData = new ArrayList();
        for(int i = 0; i < 256; ++i) {
            bytesData.add(new TheCode(i, buff.readThis(BooleanConverters
                    .boolArrayToInt(buff.readThis(8)))));
        }
        return bytesData;
    }
}
