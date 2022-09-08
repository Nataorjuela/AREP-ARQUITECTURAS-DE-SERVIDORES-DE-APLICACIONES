package arep.microspringboot;


public class WebService {


    @RequestMapping("/hello")
    public static String helloWorld(){
        return "Hello World";
    }
    @RequestMapping("/status")
    public static String serverStatus(){
        return "Runing";
    }

}
