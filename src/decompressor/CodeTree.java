package decompressor;

class CodeTree {

    private CodeTree zero;
    private CodeTree one;
    private Integer value;

    CodeTree() {
    }

    Integer getValue() {
        return this.value;
    }

    CodeTree getZero() {
        return this.zero;
    }

    CodeTree getOne() {
        return this.one;
    }

    void setValue(int value) {
        this.value = value;
    }

    void setZero(CodeTree zero) {
        this.zero = zero;
    }

    void setOne(CodeTree one) {
        this.one = one;
    }
}
