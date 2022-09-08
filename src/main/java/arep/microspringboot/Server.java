package arep.microspringboot;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        MicroSpringBoot.main(args);
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        Socket clientSocket = null;
        boolean flag=true;
        while (flag) {

            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine;
            String servicePath="";

            while ((inputLine = in.readLine()) != null) {
                if(inputLine.startsWith("GET")){
                    servicePath=inputLine.split(" ")[1];

                }

                boolean found=MicroSpringBoot.searchPath(servicePath);
                if(found){
                    outputLine = "HTTP/1.1 200 OK\r\n"
                            + "Content-Type: text/html\r\n"
                            + "\r\n"
                            +getService(servicePath);
                    out.println(outputLine);
                }

                System.out.println("Received: " + inputLine);

                if (!in.ready()) {
                    break;
                }

            }


            out.close();
            in.close();
            clientSocket.close();

        }
        serverSocket.close();
    }

    public static String getService(String servicePath) throws InvocationTargetException, IllegalAccessException {
        Method m=MicroSpringBoot.getMethod(servicePath);
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>Form Example</title>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <h1>Form with GET</h1>\n" +
                "        <form action=\"/hello\">\n" +
                "            <label for=\"name\">Name:</label><br>\n" +
                "            <input type=\"text\" id=\"name\" name=\"name\" value=\"Nata\"><br><br>\n" +
                "            <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n" +
                "        </form> \n" +m.invoke(null) +

                "\n" +
                "    </body>\n" +
                "</html>";
    }
}