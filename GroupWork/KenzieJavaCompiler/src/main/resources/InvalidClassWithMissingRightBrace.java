public class InvalidClassWithMissingRightBrace {
    private String s;
    public InvalidClassWithMissingRightBrace() {
        s = "valid";

    @Override
    public String toString() {
        return s;
    }
}