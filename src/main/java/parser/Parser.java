package parser;

import factories.StatementFactory;
import lexer.Lexer;
import nodes.Node;
import nodes.expressions.Expression;
import nodes.statements.ExpressionStatement;
import nodes.statements.Statement;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import symbol.LG;
import symbol.Symbol;
import symbol.SymbolTable;
import symbol.SymbolType;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Parser {

    public static Statement AST(String input, SymbolTable st, Symbol[] symbols) {
        return StatementAST(symbols, st);
    }

    public static Statement StatementAST(Symbol[] symbols, SymbolTable st) {
        List<Pair<Integer, Statement>> apply = new ArrayList<>();
        for (StatementFactory<?> factory: LG.statements) {
            int newPos = factory.is(symbols);
            if(newPos != -1) {
                apply.add(new ImmutablePair<>(newPos,
                        factory.makeInstance(symbols, st)));
            }
        }

        Statement ret;

        if(apply.size() == 1) {
            ret = apply.get(0).getValue();
        } else if(apply.size() > 1) {
            // TODO: handle this
            return null;
        } else {
            ret = new ExpressionStatement(ExpressionAST(symbols, st));
        }

        return ret;
    }

    public static Node ExpressionAST(Symbol[] symbols, SymbolTable st) {
        // Shutting-yard algorithm
        // https://en.wikipedia.org/wiki/Shunting-yard_algorithm#The_algorithm_in_detail
        Stack<Symbol> operators = new Stack<>();
        Stack<Node> outputs = new Stack<>();

        for(Symbol t : symbols) {
            if(t.type == SymbolType.CONTROL && t.literal.equals("(")) {
                operators.push(t);
            } else if((t.type.getValue() & SymbolType.OPERATOR.getValue()) > 0) {
                if(!operators.empty()) {
                    Symbol op = operators.peek();

                    while(!operators.empty() && op.precedence <= t.precedence &&
                            !op.literal.equals("(")){
                        Symbol operator = operators.pop();
                        expressionFromOperator(operator, outputs);
                        op = operators.peek();
                    }
                }
                operators.push(t);
            } else if(t.type == SymbolType.CONTROL && t.literal.equals(")")) {
                while (!operators.empty() && !operators.peek().literal.equals("(")) {
                    Symbol operator = operators.pop();
                    expressionFromOperator(operator, outputs);
                }
                operators.pop();
            } else if(t.type == SymbolType.IDENT) {
                outputs.push((Node) st.lookup(t.literal));
            } else {
                outputs.push((Node) t);
            }
        }

        while (!operators.empty()) {
            Symbol operator = operators.pop();
            expressionFromOperator(operator, outputs);
        }

        return outputs.pop();
    }

    private static void expressionFromOperator(Symbol operator, Stack<Node> outputs) {
        Expression exp;
        if(operator instanceof Expression) {
            exp = ((Expression) operator);
        } else {
            // TODO: handle error
            return;
        }

        Node e2 = outputs.pop();
        Node e1 = outputs.pop();
        exp.setLeft(e1);
        exp.setRight(e2);
        outputs.push(exp);
    }
}
