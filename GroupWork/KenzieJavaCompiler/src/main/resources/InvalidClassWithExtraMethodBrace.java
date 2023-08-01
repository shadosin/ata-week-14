public InvalidClassWithExtraMethodBrace {
    private String s;
    public InvalidClassWithExtraMethodBrace() {
        s = "valid";
    }

    @Override
    public String toString() }{
        return s;
    }
}
