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
    VariableDeclaration ds = new VariableDeclaration();
    assertNotNull(ds);
    assertTrue(ds instanceof Statement);
  }

  @Test
  public void shouldBeAbleToHoldDeclarationSpecifiers(){
    VariableDeclaration ds = new VariableDeclaration(new DeclarationSpecifierList(new ArrayList<>()));

    assertEquals(0, ds.getSpecifiers().size());

    ds.getSpecifiers().add("TypeA");

    assertEquals(1, ds.getSpecifiers().size());

    ds.getSpecifiers().add("TypeB");

    assertEquals(2, ds.getSpecifiers().size());
  }

  @Test
  public void shouldBeAbleToReceiveAPrePopulatedSpecifierListInConstructor(){
    List<String> prePopulatedList = new ArrayList<>();

    Collections.addAll(prePopulatedList, "TypeA", "TypeB", "TypeC");
    DeclarationSpecifierList specifiers = new DeclarationSpecifierList(prePopulatedList);

    VariableDeclaration ds = new VariableDeclaration(specifiers);

    assertEquals(3, ds.getSpecifiers().size());
  }

  @Test
  public void shouldBeAbleToReturnSpecifiersAsAList(){
    List<String> prePopulatedList = new ArrayList<>();

    Collections.addAll(prePopulatedList, "TypeA", "TypeB", "TypeC");

    DeclarationSpecifierList specifiers = new DeclarationSpecifierList(prePopulatedList);

    VariableDeclaration ds = new VariableDeclaration(specifiers);

    List<String> result = ds.getSpecifiers();

    assertEquals(3, result.size());

    assertEquals(prePopulatedList, result);

  }


}
