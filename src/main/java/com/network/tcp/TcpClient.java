package com.network.tcp;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TcpClient {
    public static void main(String[] args) throws UnknownHostException {
        int port = 9999;
        InetAddress serverIP = InetAddress.getByName("127.0.0.1");
        try(Socket socket = new Socket(serverIP, port); OutputStream os = socket.getOutputStream();) {
            os.write("你好".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
