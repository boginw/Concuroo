package concuroo.nodes;

import java.util.ArrayList;
import java.util.List;

public class Program implements Node {

  private List<Node> edc = new ArrayList<>();

  @Override
  public String getLiteral() {
    StringBuilder sb = new StringBuilder();
    for (Node node : edc) {
      sb.append(node.getLiteral());
    }
    return sb.toString();
  }

  public void addUnit(Node edc){
    this.edc.add(edc);
  }

  public List<Node> getUnits(){
    return edc;
  }
}
