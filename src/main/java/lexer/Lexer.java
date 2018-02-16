package lexer;

import nodes.Node;
import symbol.*;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final SymbolTable symtable;
    private final LG lg;
    private String input;
    private int pos; // points to current char
    private int readPos; // after current char
    private char ch; // current char under examination

    public Lexer(SymbolTable symtable, LG lg){
        this.symtable = symtable;
        this.lg = lg;
    }

    public Node[] lex(String input){
        this.input = input;
        pos = 0;
        readPos = 0;
        ch = 0;
        readChar();

        List<Node> symbols = new ArrayList<>();
        for (Node t = nextNode(); t instanceof EOF; t = nextNode()) {
            symbols.add(t);
        }

        return symbols.toArray(new Node[symbols.size()]);
    }

    private void readChar() {
        if(readPos >= input.length()){
            ch = 0;
        } else {
            ch = input.charAt(readPos);
        }
        pos = readPos;
        readPos++;
    }

    private Node nextNode() {
        skipWhitespace();

        if(pos == input.length()){
            return new EOF();
        }

        Node t = lg.lookupToken(input.substring(pos));

        readPos = t.toString().length();
        readChar();

        return t;
    }

    private void skipWhitespace() {
        while(Character.isWhitespace(ch)){
            readChar();
        }
    }
}
