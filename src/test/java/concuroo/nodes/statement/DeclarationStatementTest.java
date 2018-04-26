package concuroo.nodes.statement;

import static org.junit.Assert.*;

import concuroo.nodes.Statement;
import concuroo.nodes.TypeSpecifier;
import concuroo.nodes.declaration.InitDeclarator;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
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

    ds.addSpecifier(new TypeSpecifier());

    assertEquals(1, ds.getSpecifiersCount());

    ds.addSpecifier(new TypeSpecifier());

    assertEquals(2, ds.getSpecifiersCount());
  }

  @Test
  public void shouldBeAbleToReceiveAPrePopulatedSpecifierListInConstructor(){
    List<TypeSpecifier> prePopulatedList = new ArrayList<>();

    prePopulatedList.add(new TypeSpecifier());
    prePopulatedList.add(new TypeSpecifier());
    prePopulatedList.add(new TypeSpecifier());

    DeclarationStatement ds = new DeclarationStatement(prePopulatedList);

    assertEquals(3, ds.getSpecifiersCount());
  }

  @Test
  public void shouldBeAbleToReturnSpecifiersAsAList(){
    List<TypeSpecifier> prePopulatedList = new ArrayList<>();

    prePopulatedList.add(new TypeSpecifier());
    prePopulatedList.add(new TypeSpecifier());
    prePopulatedList.add(new TypeSpecifier());

    DeclarationStatement ds = new DeclarationStatement(prePopulatedList);

    List<TypeSpecifier> result = ds.getSpecifiers();

    assertEquals(3, result.size());

    assertEquals(prePopulatedList, result);

  }

  @Test
  public void shouldBeAbleToHoldAInitDeclarator() throws NoSuchFieldException, IllegalAccessException {
    DeclarationStatement ds = new DeclarationStatement();

    ds.setInitDeclarator(new InitDeclarator());

    Field field = ds.getClass().getDeclaredField("initDeclarator");
    field.setAccessible(true);

    assertNotNull("Set InitDeclarator dosent work", field.get(ds));
  }

  @Test
  public void shouldBeAbleToReturnDeclarator() throws NoSuchFieldException, IllegalAccessException {
    DeclarationStatement ds = new DeclarationStatement();

    InitDeclarator expected = new InitDeclarator();

    Field field = ds.getClass().getDeclaredField("initDeclarator");
    field.setAccessible(true);
    field.set(ds, expected);


    //then
    assertEquals("Get InitDeclarator dosent work", expected, ds.getInitDeclarator());
  }


}
