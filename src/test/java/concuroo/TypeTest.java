package concuroo;

import concuroo.nodes.FunctionDeclaration;
import concuroo.nodes.Node;
import concuroo.nodes.Program;
import concuroo.nodes.Statement;
import concuroo.nodes.Expression;
import concuroo.nodes.expression.binaryExpression.BinaryExpression;
import concuroo.nodes.expression.UnaryExpression;
import concuroo.nodes.statement.ExpressionStatement;

import concuroo.nodes.statement.VariableDeclaration;
import concuroo.nodes.statement.selectionStatement.IfStatement;
import java.util.ArrayList;
import java.util.List;

public class TypeTest {

  private Node input;
  public List<Node> operands = new ArrayList<>();


  public void findOperands() {
    Program program = (Program) input;
    FunctionDeclaration funcDef = (FunctionDeclaration) program.get(0);

    for (Node node : program) {
      FunctionDeclaration definition = (FunctionDeclaration) node;
      if (definition.getIdentifier().equals("main")) {
        funcDef = definition;
      }
    }

    List<Statement> stmList = funcDef.getBody();

    for (Statement stm : stmList) {
      if (stm instanceof ExpressionStatement) {
        ExpressionStatement eStm = (ExpressionStatement) stm;
        findOperandsForExpression(eStm.getExpression());
      }

      if (stm instanceof VariableDeclaration) {
        VariableDeclaration eStm = (VariableDeclaration) stm;
        operands.add(eStm);
        findOperandsForExpression(eStm.getInitializer());
      }

      if (stm instanceof IfStatement) {
        IfStatement iStm = (IfStatement) stm;
        operands.add(iStm);
      }
    }
  }

  private void findOperandsForExpression(Expression input) {
    operands.add(input);
    if (input instanceof BinaryExpression) {
      BinaryExpression bExpr = (BinaryExpression) input;

      findOperandsForExpression(bExpr.getFirstOperand());
      findOperandsForExpression(bExpr.getSecondOperand());
    }
    if (input instanceof UnaryExpression) {
      UnaryExpression uExpr = (UnaryExpression) input;

      findOperandsForExpression(uExpr.getFirstOperand());
    }
  }

  public void setInput(Node input) {
    this.input = input;
    findOperands();
  }
}
