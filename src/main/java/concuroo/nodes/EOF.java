package concuroo.nodes;

/**
 * EOF (End-of-File) is a token that denotes the
 * end of a file
 */
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
