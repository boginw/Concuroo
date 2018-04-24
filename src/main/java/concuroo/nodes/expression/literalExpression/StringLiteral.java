package concuroo.nodes.expression.literalExpression;

import concuroo.nodes.expression.LiteralExpression;

public class StringLiteral implements LiteralExpression {

    private String value;

    public StringLiteral(String value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        if (value instanceof String) {
            this.value = value.toString();
        }
        else throw new IllegalArgumentException("Wrong data type passed");
    }

    @Override
    public String getLiteral() {
        return this.value;
    }
}