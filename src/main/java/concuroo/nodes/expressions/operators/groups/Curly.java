package concuroo.nodes.expressions.operators.groups;

import concuroo.language.Associativity;

public class Curly implements Group {
    private boolean isStart;
    private String literal;

    @Override
    public int getPrecedence() {
        return 0;
    }

    @Override
    public Associativity getAssociativity() {
        return Associativity.LeftToRight;
    }

    @Override
    public String getLiteral() {
        return literal;
    }

    @Override
    public int getVal() {
        return 0;
    }

    @Override
    public boolean isStart() {
        return isStart;
    }

    @Override
    public void setStart(boolean start) {
        isStart = start;
    }

    @Override
    public void setLiteral(String literal) {
        this.literal = literal;
        isStart = literal.equals("{");
    }
}
