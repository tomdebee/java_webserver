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
            
            int lineCount = 0;

            
            while (line !=null && !line.isEmpty()) {
                System.out.println(line);
                requestParse.input(line, lineCount);
                line = reader.readLine();
                lineCount++;
            }
            
            StringBuilder httpBody = new StringBuilder("?");
            while(reader.ready()) {
            	httpBody.append((char) reader.read());
            }
            if(httpBody.toString().length()>1) {
            	requestParse.parseHTTPargs(httpBody.toString());;
            }
            
            StringBuilder paramContent = new StringBuilder("");
            for(String value : requestParse.getParameterNames()) {
            	paramContent = paramContent.append(value).append(": ");
            	paramContent = paramContent.append(requestParse.getParameterValue(value));
        		paramContent = paramContent.append("<br/>");
            }
            
            response.setStatus(HttpStatusCode.OK);
            response.setContent("<html>\r\n" + 
            		"<body>\r\n" + 
            		"You did an HTTP " + requestParse.getHTTPMethod() + " request.<br/>\r\n" + 
            		"Requested resource: " + requestParse.getResourcePath() + "<br/><br/>\r\n" + 
            		"The following parameters were passed: " + "<br/>\r\n" +
            		paramContent.toString() + "<br/>\r\n" +
            		"</body>\r\n" + 
            		"</html>");


            writer.write(
            		requestParse.getHttpVersion() + " "
            		+ response.getStatus().getCode() + " "
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