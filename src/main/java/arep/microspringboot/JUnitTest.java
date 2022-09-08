package arep.microspringboot;

public class JUnitTest {

    @Test
    public static void n1(){}

    @Test
    public static void n2(){}

    @Test
    public static void n3(){}

    @Test
    public static void n4()throws Exception{
        throw (new Exception("Error n4"));
    }
}
