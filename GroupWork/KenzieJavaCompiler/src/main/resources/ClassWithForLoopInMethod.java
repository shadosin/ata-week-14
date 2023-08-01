public class ClassWithForLoopInMethod {
    private String s;
    public ClassWithForLoopInMethod() {
        s = "valid";
    }
    public String printString() {
        for (char c : s.toCharArray()) {
            System.out.println(c);
        }
    }
}