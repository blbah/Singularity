package tools;

import java.util.ArrayList;

public class BooleanConverters {

    private BooleanConverters() {
    }

    public static int boolArrayToInt(ArrayList<Boolean> bools) {
        int res = 0;
        for(int i = 0; i < 8; ++i) {
            if (bools.get(7 - i)) {
                res = (int)((double)res + Math.pow(2.0D, i));
            }
        }
        return res;
    }

    public static ArrayList<Boolean> intToBoolList(int num) {
        ArrayList<Boolean> bools = new ArrayList();
        for(int i = 7; i >= 0; --i) {
            if ((double)num >= Math.pow(2.0D, i)) {
                bools.add(true);
                num = (int)((double)num - Math.pow(2.0D, i));
            } else {
                bools.add(false);
            }
        }
        return bools;
    }
}
