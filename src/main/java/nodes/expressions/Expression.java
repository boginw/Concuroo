package nodes.expressions;

import nodes.Node;
import symbol.Symbol;
import symbol.SymbolType;

public abstract class Expression extends Symbol implements Node {
    Node left;
    Node right;

    public Expression(String literal, int precedence, SymbolType type) {
        super(literal, precedence, type);
    }

    public void setLeft(Node left){
        this.left = left;
    }

    public void setRight(Node right){
        this.right = right;
    }

    public boolean hasLeft(){
        return left == null;
    }

    public boolean hasRight(){
        return right == null;
    }
}
