import com.sun.source.tree.AssertTree;
import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {

    @Test
    public void getClassNumber(){
        MainClass mainClass = new MainClass();
        Assert.assertTrue("class_number less than 45",mainClass.getClassNumber() > 45);
    }
}
