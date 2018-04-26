package concuroo.nodes.declaration;

import static org.junit.Assert.*;

import concuroo.nodes.Node;
import java.lang.reflect.Field;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class InitDeclaratorTest {

  @Test
  public void shouldExistsAndImplementNode(){
    InitDeclarator id = new InitDeclarator();

    assertNotNull(id);
    assertTrue(id instanceof Node);
  }

  @Test
  public void shouldHoldADeclarator() throws NoSuchFieldException, IllegalAccessException{
    InitDeclarator id = new InitDeclarator();

    id.setDeclarator(new Declarator());

    Field field = id.getClass().getDeclaredField("declarator");
    field.setAccessible(true);
    assertNotNull("Set Declarator dosent work", field.get(id));
  }

  @Test
  public void shouldBeAbleToReturnDeclarator() throws NoSuchFieldException, IllegalAccessException {
    InitDeclarator id = new InitDeclarator();

    Declarator expected = new Declarator();

    Field field = id.getClass().getDeclaredField("declarator");
    field.setAccessible(true);
    field.set(id, expected);


    //then
    assertEquals("Get Declarator dosent work", expected, id.getDeclarator());
  }

  @Test
  public void shouldBeAbleToHoldAInitializer() throws NoSuchFieldException, IllegalAccessException {
    InitDeclarator id = new InitDeclarator();

    id.setInitializer(new Initializer());

    Field field = id.getClass().getDeclaredField("initializer");
    field.setAccessible(true);

    assertNotNull("Set Initializer dosent work", field.get(id));
  }

  @Test
  public void shouldBeAbleToReturnInitializer() throws NoSuchFieldException, IllegalAccessException {
    InitDeclarator id = new InitDeclarator();

    Initializer expected = new Initializer();

    Field field = id.getClass().getDeclaredField("initializer");
    field.setAccessible(true);
    field.set(id, expected);


    //then
    assertEquals("Get Initializer dosent work", expected, id.getInitializer());
  }

}
