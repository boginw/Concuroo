package nodes.expressions.atoms;

public class IntegerNode implements Atom {

    private final String literal;

    public IntegerNode(String literal){
        this.literal = literal;
    }

    @Override
    public String getLiteral() {
        return literal;
    }

    @Override
    public int getVal(){
        return Integer.parseInt(literal);
    }
}
