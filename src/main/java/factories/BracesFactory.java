package factories;

import nodes.Braces;

public class BracesFactory implements Factory<Braces> {
    @Override
    public int is(String input, int pos) {
        char c = input.charAt(pos);
        return c == '(' || c == ')' || c == '[' || c == ']' ? pos + 1 : -1;
    }

    @Override
    public Braces makeInstance(String literal) {
        if(literal == null || literal.equals("") || literal.length() > 1){
            return null;
        }

        return new Braces(literal);
    }
}
