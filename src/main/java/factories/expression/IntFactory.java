package factories.expression;

import nodes.expressions.atoms.IntegerNode;
import language.TokenType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntFactory implements ExpressionFactory<IntegerNode> {
    @Override
    public String getPattern() {
        return "^[0-9]+";
    }

    @Override
    public int is(String input) {
        Pattern p = Pattern.compile(getPattern());
        Matcher matcher = p.matcher(input);
        if(matcher.find()){
            return matcher.group().length();
        }

        return -1;
    }

    @Override
    public IntegerNode makeNode(String literal) {
        return null;
    }

    @Override
    public TokenType getType() {
        return TokenType.LITERAL;
    }
}
