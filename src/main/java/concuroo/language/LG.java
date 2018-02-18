package concuroo.language;

import concuroo.factories.*;
import concuroo.factories.statement.StatementFactory;
import concuroo.nodes.Node;
import concuroo.nodes.TokenNode;
import concuroo.nodes.expressions.Expression;
import concuroo.nodes.expressions.operators.groups.Curly;
import concuroo.nodes.expressions.operators.groups.Parenthesis;
import concuroo.nodes.statements.Statement;
import concuroo.symbol.SymbolTable;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LG {
    private final HashMap<String, Object[]> tokens;
    private final List<Pair<Object[][], StatementFactory<?>>> statements;

    public LG(){
        tokens = new HashMap<>();
        statements = new ArrayList<>();
    }

    public void registerToken(String key, Factory<?> factory){
        tokens.put(key, new Object[]{factory});
    }

    public void registerToken(String key, String pattern, TokenType type){
        tokens.put(key, new Object[]{pattern, type});
    }

    public void registerStatement(Factory<?> factory, Object[][] objects){
        statements.add(new ImmutablePair<>(objects, (StatementFactory<?>) factory));
    }

    public Node lookupToken(String input){
        String found;
        for (Map.Entry<String, Object[]> t: tokens.entrySet()) {
            Object[] def = t.getValue();
            if(def.length == 2){
                String pattern = (String) def[0];
                Pattern p = Pattern.compile(pattern);
                Matcher m = p.matcher(input);
                if(m.find()){
                    found = m.group();
                    return new TokenNode(t.getKey(), (TokenType) def[1], found);
                }
            } else if(def.length == 1) {
                Factory<?> f = (Factory<?>) def[0];
                int newPos = f.is(input);
                if(newPos != -1){
                    found = input.substring(0, newPos);
                    return f.makeNode(found);
                }
            }
        }

        // TODO: throw
        return null;
    }

    // TODO: clean this algorithm
    // TODO: make this method return the Node and an int offset
    public Node lookupStatement(Node[] tokens){
        for (Pair<Object[][], StatementFactory<?>> pair: statements) {
            Object[][] perms = pair.getLeft();
            for (Object[] perm: perms) {
                boolean hit = true;
                for (int i = 0, j = 0; i < perm.length && j < tokens.length; i++) {
                    // It's a string, which means it's a token
                    if(perm[i] instanceof String) {
                        Object[] p = this.tokens.get(perm[i].toString());
                        if ((p.length == 1 && ((Factory<?>) p[0]).is(tokens[j])) ||
                                (p.length == 2 && (tokens[j] instanceof TokenNode) &&
                                        ((TokenNode) tokens[j]).getKey().equals(perm[i].toString()))) {
                            j++;
                            continue;
                        }
                        hit = false;
                        break;
                    } else if(perm[i] instanceof Class && perm[i] == Expression.class){
                        // The stuff need to be expressions
                        if(!(tokens[j] instanceof Expression)){
                            hit = false;
                            break;
                        }

                        // If the last thing we saw was a '(' then we expect a ')'
                        if(i > 0 && perm[i - 1] instanceof String && perm[i - 1].equals("(")){
                            j = Utilities.findClosingToken(tokens, j, Parenthesis.class);
                        } else {
                            // If not, we have no other choice than to do this.
                            while(j < tokens.length && tokens[j] instanceof Expression){
                                j++;
                            }
                        }
                    } else if(perm[i] instanceof Class && perm[i] == Statement.class){
                        // If the last thing we saw was a '{' then we expect a '}'
                        if(i > 0 && perm[i - 1] instanceof String && perm[i - 1].equals("{")){
                            j = Utilities.findClosingToken(tokens, j, Curly.class);
                        } else {
                            // Recursively look for statement
                            Statement st = (Statement) lookupStatement(Arrays.copyOfRange(tokens, j, tokens.length));
                            if(st == null) {
                                hit = false;
                                break;
                            }
                        }
                    } else {
                        hit = false;
                        break;
                    }
                }
                if(hit){
                    return pair.getValue().makeInstance(tokens, new SymbolTable());
                }
            }
        }
        return null;
    }
}
