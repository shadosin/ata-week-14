public class InvalidClassWithExtraRightBrace {
    private String s;
    public InvalidClassWithExtraRightBrace() {
        s = "valid";
    }
    @Override
    public String toString() {
        return s;
    }
}
}