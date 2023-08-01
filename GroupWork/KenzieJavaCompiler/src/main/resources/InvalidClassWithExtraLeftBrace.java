public class InvalidClassWithExtraLeftBrace {{
    private String s;
    public InvalidClassWithExtraLeftBrace() {
        s = "valid";
    }
    @Override
    public String toString() {
        return s;
    }
}