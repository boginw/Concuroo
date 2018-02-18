import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class RunnerTest {

    @Test
    public void testAdd(){
        Runner printer = new Runner();
        String result = printer.printed();
        assertTrue(result.equals("yes"));
    }
}
