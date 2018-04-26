package concuroo.nodes.declaration;

import static org.junit.Assert.*;

import concuroo.nodes.Node;
import concuroo.nodes.Pointer;
import java.lang.reflect.Field;
import org.junit.Test;

public class DeclaratorTest {

  @Test
  public void shouldExistAndImplementNode(){
    Declarator d = new Declarator();
    assertNotNull(d);
    assertTrue(d instanceof Node);
  }

  @Test
  public void shouldBeAbleToHoldAPointer() throws NoSuchFieldException, IllegalAccessException {
    Declarator d = new Declarator();

    d.setPointer(new Pointer());

    Field field = d.getClass().getDeclaredField("pointer");
    field.setAccessible(true);

    assertNotNull("Set Pointer dosent work", field.get(d));
  }

  @Test
  public void shouldBeAbleToReturnAPointer() throws NoSuchFieldException, IllegalAccessException {
    Pointer expected = new Pointer();

    Declarator d = new Declarator();

    Field field = d.getClass().getDeclaredField("pointer");
    field.setAccessible(true);
    field.set(d, expected);


    //then
    assertEquals("Get Pointer dosent work", expected, d.getPointer());
  }

  @Test
  public void shouldBeAbleToHoldADirectDeclarator() throws NoSuchFieldException, IllegalAccessException {
    Declarator d = new Declarator();

    d.setDirectDeclarator(new DirectDeclarator());

    Field field = d.getClass().getDeclaredField("directDeclarator");
    field.setAccessible(true);

    assertNotNull("Set Direct Declarator dosent work", field.get(d));
  }

  @Test
  public void shouldBeAbleToReturnADirectDeclarator() throws NoSuchFieldException, IllegalAccessException {
    DirectDeclarator expected = new DirectDeclarator();

    Declarator d = new Declarator();

    Field field = d.getClass().getDeclaredField("directDeclarator");
    field.setAccessible(true);
    field.set(d, expected);


    //then
    assertEquals("Get Direct Declarator dosent work", expected, d.getDirectDeclarator());

  }


}
