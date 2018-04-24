package concuroo.nodes.statement;

import concuroo.nodes.Statement;
import concuroo.nodes.expression.Expression;

public interface IterationStatement extends Statement{
  Expression getCondition();
  void setCondition(Expression condition);

  Statement getConsequence();
  void setConsequence(Statement consequence);
}
