package concuroo.nodes.expression.literalExpression;

import concuroo.nodes.expression.LiteralExpression;

public class CharLiteral implements LiteralExpression {

    private char value;

    public CharLiteral(char value){
        this.value = value;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        if (value instanceof Character) {
            this.value = (char) value;
        }
        else throw new IllegalStateException("Wrong dataType");
    }

    @Override
    public String getLiteral() {
        return "\"" + String.valueOf(this.value) + "\"";
    }
}
