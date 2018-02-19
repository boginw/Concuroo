package concuroo.factories.statement;

import concuroo.nodes.Node;
import concuroo.nodes.statements.IfStatement;
import concuroo.nodes.statements.Statement;

import java.util.List;

public class IfFactory implements StatementFactory<IfStatement> {

    @Override
    public int is(String input) {
        return input.startsWith("if") ? 2 : -1;
    }

    @Override
    public boolean is(Node node) {
        return node instanceof IfStatement;
    }

    @Override
    public Node makeNode(String literal) {
        return new IfStatement();
    }

    @Override
    public IfStatement makeInstance(Node[] symbols, List<Node> st) {
        IfStatement stat = new IfStatement();
        if(st.size() > 0){
            stat.setCondition(st.get(0));
        }
        if(st.size() > 1){
            stat.setConsequence((Statement) st.get(1));
        }
        if(st.size() > 2){
            stat.setAlternative((Statement) st.get(2));
        }

        return stat;
    }
}
