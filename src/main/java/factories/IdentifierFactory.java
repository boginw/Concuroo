package factories;

import nodes.IdentifierNode;

public class IdentifierFactory implements Factory<IdentifierNode> {
    @Override
    public int is(String input, int pos) {
        String identifier = readIdentifier(input, pos);
        return identifier.equals("") ? -1 : identifier.length();
    }

    @Override
    public IdentifierNode makeInstance(String literal) {
        return new IdentifierNode();
    }

    private String readIdentifier(String input, int pos) {
        int startPos = pos;
        while(Character.isLetter(input.charAt(pos))){
            pos++;
        }
        return input.substring(startPos, pos);
    }
}
