package concuroo.nodes;

import java.util.ArrayList;
import java.util.List;

public class TranslationUnit implements Node {

  private List<Node> edc = new ArrayList<>();

  public void addUnit(Node edc){
    this.edc.add(edc);
  }

  @Override
  public String getLiteral() {
    StringBuilder sb = new StringBuilder();
    for (Node node : edc) {
      sb.append(node.getLiteral());
    }
    return sb.toString();
  }

  public List<Node> getUnits(){
    return edc;
  }
}
