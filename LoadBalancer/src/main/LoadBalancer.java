package main;

import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class LoadBalancer extends Thread {

    private ServerSocket serverSocket; // Creates a server socket to listen
    public static int[] ports = new int[]{5252,5353};
    private int counter;

    public LoadBalancer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }
    public void run() {
        while(true) {
            try {
                System.out.println("Waiting for client on port " +
                        serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept(); // If a requests come to server it accepts
                proxy(chooseBackendPort(),server); // with out routing strategy we route request to chosed backend servers

            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public int chooseBackendPort() {
        //Round Robin
        int port = ports[(counter%ports.length)];
        System.out.printf("Counter is: %d, Chosed localhost:%d\n",counter,port);
        counter++;
        return port;
    }

    public void proxy(int backendPort, Socket socket) throws IOException {
        Socket newSocket = new Socket("localhost", backendPort);
        Thread thread = new Thread(){
            public void run(){
                try {
                    IOUtils.copy(socket.getInputStream(),newSocket.getOutputStream()); // Send Request
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        Thread threadForOppositeDirection = new Thread(){
            public void run(){
                try {
                    IOUtils.copy(newSocket.getInputStream(),socket.getOutputStream()); // Read Response
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        threadForOppositeDirection.start();
    }
}

