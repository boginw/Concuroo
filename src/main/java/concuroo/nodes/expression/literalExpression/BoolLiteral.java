package concuroo.nodes.expression.literalExpression;

import concuroo.nodes.expression.LiteralExpression;

public class BoolLiteral implements LiteralExpression {

    private boolean value;

    public BoolLiteral(boolean value){
        this.value = value;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        if (value instanceof Boolean) {
            this.value = (boolean) value;
        }
        else throw new IllegalArgumentException("Wrong data type passed");
    }

    @Override
    public String getLiteral() {
        return String.valueOf(value);
    }
}
