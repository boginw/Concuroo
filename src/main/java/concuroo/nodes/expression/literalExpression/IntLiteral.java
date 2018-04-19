package concuroo.nodes.expression.literalExpression;

import concuroo.nodes.expression.LiteralExpression;

public class IntLiteral implements LiteralExpression {

    private int value;

    public IntLiteral(int value){
        this.value = value;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = Integer.valueOf(value.toString());
    }

    @Override
    public String getLiteral() {
        return String.valueOf(value);
    }
}