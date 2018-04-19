package concuroo.nodes.expression.literalExpression;

import concuroo.nodes.expression.LiteralExpression;

public class FloatLiteral implements LiteralExpression {

    private double value;

    public FloatLiteral(double value){
        this.value = value;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = Double.valueOf(value.toString());

    }

    @Override
    public String getLiteral() {
        return String.valueOf(this.value);
    }
}