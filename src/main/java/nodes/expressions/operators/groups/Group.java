package nodes.expressions.operators.groups;

import nodes.expressions.operators.Operator;

public interface Group extends Operator{
    boolean isStart();
    void setStart(boolean start);
    void setLiteral(String literal);
}
