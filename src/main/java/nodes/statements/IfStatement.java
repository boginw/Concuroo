package nodes.statements;

import nodes.Node;
import symbol.SymbolType;

public class IfStatement extends Statement {
    private Node condition;
    private Statement consequence;
    private Statement alternative;

    public IfStatement() {
        super("IF", 0, SymbolType.CONTROL);
    }

    public void setCondition(Node condition){
        this.condition = condition;
    }

    public void setConsequence(Statement consequence){
        this.consequence = consequence;
    }

    public void setAlternative(Statement alternative){
        this.alternative = alternative;
    }

    @Override
    public int getVal() {
        if(condition.getVal() != 0){
            return consequence.getVal();
        }
        return alternative.getVal();
    }
}
