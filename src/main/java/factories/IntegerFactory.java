package factories;

import nodes.IntegerNode;

public class IntegerFactory implements Factory<IntegerNode> {
    @Override
    public int is(String input, int pos) {
        String number = readNumber(input, pos);
        return number.equals("") ? -1 : pos + number.length();
    }

    @Override
    public IntegerNode makeInstance(String literal) {
        return new IntegerNode(literal);
    }

    private String readNumber(String input, int pos) {
        int startPos = pos;
        while(input.length() > pos && Character.isDigit(input.charAt(pos))){
            pos++;
        }
        return input.substring(startPos, pos);
    }
}
