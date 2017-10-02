package nl.sogyo.webserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.write("Thank you for connecting!\r\n");
            
            int lineCount = 0;
            while (!line.isEmpty()) {
                System.out.println(line);
                requestParse.input(line, lineCount);
                line = reader.readLine();
                lineCount++;
            }
            writer.write("You did an HTTP " + requestParse.getHTTPMethod() + " request and you requested the following resource: "
            		+ requestParse.getResourcePath() + ".\n");

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