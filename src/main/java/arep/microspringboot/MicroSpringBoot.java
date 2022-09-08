package arep.microspringboot;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MicroSpringBoot {

    static Map<String,Method> services=new HashMap<>();
    public static void main(String... args) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        String className=args[0];
        Class c=Class.forName(className);
        Method[] declareMethods= c.getDeclaredMethods();
        int numServic=0;


        for (Method m:declareMethods){
            if (m.isAnnotationPresent(RequestMapping.class)){

                    String servicePath=m.getAnnotation(RequestMapping.class).value();
                    m.invoke(null);
                    System.out.println("Invoking"+m.getName()+"in class:"+c.getName());
                    services.put(servicePath,m);
                    numServic = numServic+1;


            }
        }
        System.out.println("numero de servicios:" + numServic);
    }
    public static Boolean searchPath(String path) {
       return services.containsKey(path);
    }

    public static Method getMethod(String path){
        Method m=services.get(path);
        return m;
    }

}
