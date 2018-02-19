package concuroo.factories.statement;

import concuroo.nodes.Node;
import concuroo.nodes.statements.BlockStatement;
import concuroo.nodes.statements.Statement;

import java.util.List;

public class BlockFactory implements StatementFactory<BlockStatement> {

    @Override
    public BlockStatement makeInstance(Node[] symbols, List<Node> st) {
        BlockStatement stat = new BlockStatement();
        for (Node t: st) {
            stat.addStatement((Statement) t);
        }
        return stat;
    }

    @Override
    public int is(String input) {
        char c = input.charAt(0);
        return c == '{' || c == '}' ? 1 : -1;
    }

    @Override
    public boolean is(Node node) {
        return node instanceof BlockStatement;
    }

    @Override
    public Node makeNode(String literal) {
        return new BlockStatement();
    }
}
