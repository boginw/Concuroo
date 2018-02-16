package nodes;

public class EOF implements Node {

    @Override
    public String getLiteral() {
        return "EOF";
    }

    @Override
    public int getVal() {
        return 0;
    }
}
