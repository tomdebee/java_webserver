package nl.sogyo.webserver;

import java.io.*;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
    private Socket socket;

    public ConnectionHandler(Socket toHandle) {
        this.socket = toHandle;
    }

    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = reader.readLine();
            System.out.println();

            while (!line.isEmpty()) {
                System.out.println(line);
                line = reader.readLine();
            }
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.write("Thank you for connecting!\r\n");
            writer.flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}