package nodes.statements;

import nodes.IdentifierNode;
import nodes.Node;
import nodes.expressions.Expression;
import symbol.SymbolType;

public class AssignmentStatement extends Statement {
    private IdentifierNode ident;
    private Node value;

    public AssignmentStatement(String literal){
        super(literal, 14, SymbolType.DECLARATION);
    }

    public IdentifierNode getIdent() {
        return ident;
    }

    public void setIdent(IdentifierNode ident) {
        this.ident = ident;
    }

    public Node getValue() {
        return value;
    }

    public void setValue(Node value) {
        this.value = value;
    }

    @Override
    public int getVal() {
        ident.setValue(value.getVal());
        return ident.getVal();
    }
}
