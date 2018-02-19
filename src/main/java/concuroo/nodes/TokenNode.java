package concuroo.nodes;

import concuroo.language.TokenType;

public class TokenNode implements Node {

    private final String key;
    private final TokenType tokenType;
    private final String literal;

    public TokenNode(String key, TokenType tokenType, String literal) {
        this.key = key;
        this.tokenType = tokenType;
        this.literal = literal;
    }

    @Override
    public String getLiteral() {
        return literal;
    }

    @Override
    public int getVal() {
        return 0;
    }

    public String getKey() {
        return key;
    }

    public TokenType getTokenType() {
        return tokenType;
    }
}
