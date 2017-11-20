package be.howest.sooa.o11.socket;

import be.howest.sooa.o11.ex.ServerSocketException;
import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author hayk
 */
public class EchoServer extends Thread {

    private ServerSocket serverSocket;

    public EchoServer(int port) {
        try {
            System.out.println("Creating server socket...");
            serverSocket = new ServerSocket(port);
            System.out.println("Server socket created...");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            throw new ServerSocketException(ex);
        }
    }
    
    public void closeSocket() {
        try {
            System.out.println("Closing server socket...");
            serverSocket.close();
            System.out.println("Server socket closed...");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            throw new ServerSocketException(ex);
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("Accepting connection...");
            serverSocket.accept();
            System.out.println("Connection accepted...");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            throw new ServerSocketException(ex);
        }
    }
}
