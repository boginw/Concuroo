package lexer;

import factories.Factory;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import symbol.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Lexer {
    private final SymbolTable symtable;
    private String input;
    private int pos; // points to current char
    private int readPos; // after current char
    private char ch; // current char under examination

    public Lexer(SymbolTable symtable){
        this.symtable = symtable;
    }

    public Symbol[] lex(String input){
        this.input = input;
        pos = 0;
        readPos = 0;
        ch = 0;
        readChar();

        List<Symbol> symbols = new ArrayList<>();
        for (Symbol t = nextToken(); t.type != SymbolType.EOF; t = nextToken()) {
            symbols.add(t);
        }

        return symbols.toArray(new Symbol[symbols.size()]);
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

    private Symbol nextToken() {
        skipWhitespace();

        if(pos == input.length()){
            return new EOF();
        }

        List<Pair<Integer, Symbol>> apply = new ArrayList<>();
        for (Factory<?> factory: LG.symbols) {
            int newPos = factory.is(input, pos);
            if(newPos != -1){
                apply.add(new ImmutablePair<>(newPos,
                        factory.makeInstance(input.substring(pos, newPos))));
            }
        }

        Pair<Integer, Symbol> ret;

        switch (apply.size()) {
            case 0:
                ret = new ImmutablePair<>(0, null);
                break;
            case 1:
                ret = apply.get(0);
                break;
            default:
                apply.sort(Comparator.comparingInt(Pair::getKey));
                ret = apply.get(apply.size() - 1);
                break;
        }

        readPos = ret.getKey();
        readChar();

        return ret.getValue();
    }

    private void skipWhitespace() {
        while(Character.isWhitespace(ch)){
            readChar();
        }
    }
}
