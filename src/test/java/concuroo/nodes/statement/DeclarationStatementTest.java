package concuroo.nodes.statement;

import static org.junit.Assert.*;

import concuroo.nodes.DeclarationSpecifierList;
import concuroo.nodes.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;


public class DeclarationStatementTest {

  @Test
  public void shouldExistAndImplementStatement(){
    VariableDefinition ds = new VariableDefinition();
    assertNotNull(ds);
    assertTrue(ds instanceof Statement);
  }

  @Test
  public void shouldBeAbleToHoldDeclarationSpecifiers(){
    VariableDefinition ds = new VariableDefinition(new DeclarationSpecifierList(new ArrayList<>()));

    assertEquals(0, ds.getSpecifiers().getSpecifiersCount());

    ds.getSpecifiers().addSpecifier("TypeA");

    assertEquals(1, ds.getSpecifiers().getSpecifiersCount());

    ds.getSpecifiers().addSpecifier("TypeB");

    assertEquals(2, ds.getSpecifiers().getSpecifiersCount());
  }

  @Test
  public void shouldBeAbleToReceiveAPrePopulatedSpecifierListInConstructor(){
    List<String> prePopulatedList = new ArrayList<>();

    Collections.addAll(prePopulatedList, "TypeA", "TypeB", "TypeC");
    DeclarationSpecifierList specifiers = new DeclarationSpecifierList(prePopulatedList);

    VariableDefinition ds = new VariableDefinition(specifiers);

    assertEquals(3, ds.getSpecifiers().getSpecifiersCount());
  }

  @Test
  public void shouldBeAbleToReturnSpecifiersAsAList(){
    List<String> prePopulatedList = new ArrayList<>();

    Collections.addAll(prePopulatedList, "TypeA", "TypeB", "TypeC");

    DeclarationSpecifierList specifiers = new DeclarationSpecifierList(prePopulatedList);

    VariableDefinition ds = new VariableDefinition(specifiers);

    List<String> result = ds.getSpecifiers().getSpecifiers();

    assertEquals(3, result.size());

    assertEquals(prePopulatedList, result);

  }


}
