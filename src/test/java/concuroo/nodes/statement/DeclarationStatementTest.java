package concuroo.nodes.statement;

import static org.junit.Assert.*;

import concuroo.nodes.Statement;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;


public class DeclarationStatementTest {

  @Test
  public void shouldExistAndImplementStatement(){
    DeclarationStatement ds = new DeclarationStatement();
    assertNotNull(ds);
    assertTrue(ds instanceof Statement);
  }

  @Test
  public void shouldBeAbleToHoldDeclarationSpecifiers(){
    DeclarationStatement ds = new DeclarationStatement();

    assertEquals(0, ds.getSpecifiersCount());

    ds.addSpecifier("TypeA");

    assertEquals(1, ds.getSpecifiersCount());

    ds.addSpecifier("TypeB");

    assertEquals(2, ds.getSpecifiersCount());
  }

  @Test
  public void shouldBeAbleToReceiveAPrePopulatedSpecifierListInConstructor(){
    List<String> prePopulatedList = new ArrayList<>();

    Collections.addAll(prePopulatedList, "TypeA", "TypeB", "TypeC");

    DeclarationStatement ds = new DeclarationStatement(prePopulatedList);

    assertEquals(3, ds.getSpecifiersCount());
  }

  @Test
  public void shouldBeAbleToReturnSpecifiersAsAList(){
    List<String> prePopulatedList = new ArrayList<>();

    Collections.addAll(prePopulatedList, "TypeA", "TypeB", "TypeC");

    DeclarationStatement ds = new DeclarationStatement(prePopulatedList);

    List<String> result = ds.getSpecifiers();

    assertEquals(3, result.size());

    assertEquals(prePopulatedList, result);

  }


}
