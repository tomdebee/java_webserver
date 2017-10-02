package nl.sogyo.webserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.format.DateTimeFormatter;

public class ConnectionHandler implements Runnable{
    private Socket socket;

    public ConnectionHandler(Socket toHandle) {
        this.socket = toHandle;
    }
    
    
    
    
    
    public void run()  {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = reader.readLine();
            System.out.println();
                       
            RequestParser requestParse = new RequestParser();
            ResponseMessage response = new ResponseMessage();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            //writer.write("Thank you for connecting!\r\n");
            
            int lineCount = 0;
            while (line !=null && !line.isEmpty()) {
                System.out.println(line);
                requestParse.input(line, lineCount);
                line = reader.readLine();
                lineCount++;
            }
//            writer.write("You did an HTTP " + requestParse.getHTTPMethod() + " request and you requested the following resource: "
//            		+ requestParse.getResourcePath());          
//            for(String output : requestParse.getHeaderParameterNames()) {
//            	writer.write("\n");
//            	writer.write(output);
//            	writer.write(": ");
//            	writer.write(requestParse.getHeaderParameterValue(output));
//            }          
//            writer.newLine();
            
            response.setStatus(HttpStatusCode.ServerError);
            response.setContent("<html>\r\n" + 
            		"<body>\r\n" + 
            		"You did an HTTP " + requestParse.getHTTPMethod() + " request.<br/>\r\n" + 
            		"Requested resource: " + requestParse.getResourcePath() + "\r\n" + 
            		"</body>\r\n" + 
            		"</html>");

            writer.write(
            		requestParse.getHttpVersion() + " "
            		+ response.getStatus().getCode()
            		+ response.getStatus().getDescription() + "\r\n"
            		+ "Date: " + response.getDate().format(DateTimeFormatter.RFC_1123_DATE_TIME) + "\r\n"
            		+ "Server: " + "minneolaserver/0.0.15" + "\r\n"
            		+ "Connection: " + "close" + "\r\n"
            		+ "Content-Type: " + "text/html; charset=UTF-8" + "\r\n"
            		+ "Content-Length: " + response.getContent().length() + "\r\n"
            		+ "\r\n" 
            		+ response.getContent()
            		);
            writer.flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String... args) {
        try {
            ServerSocket socket = new ServerSocket(9090);
            while(true) {
                Socket newConnection = socket.accept();
                Thread t = new Thread(new ConnectionHandler(newConnection));
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}