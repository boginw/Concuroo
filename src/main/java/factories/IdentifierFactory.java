package factories;

import nodes.IdentiferNode;

public class IdentifierFactory implements Factory<IdentiferNode> {
    @Override
    public int is(String input, int pos) {
        String identifier = readIdentifier(input, pos);
        return identifier.equals("") ? -1 : identifier.length();
    }

    @Override
    public IdentiferNode makeInstance(String literal) {
        return new IdentiferNode();
    }

    private String readIdentifier(String input, int pos) {
        int startPos = pos;
        while(Character.isLetter(input.charAt(pos))){
            pos++;
        }
        return input.substring(startPos, pos);
    }
}
