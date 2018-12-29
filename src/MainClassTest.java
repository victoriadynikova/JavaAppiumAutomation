import com.sun.source.tree.AssertTree;
import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {

    @Test
    public void testGetClassString(){
        MainClass mainClass = new MainClass();
        Assert.assertTrue("We didn't find any hello", mainClass.getClassString().contains("hello")
                                                                || mainClass.getClassString().contains("Hello"));
    }
}
