package com.network.tcp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {
    public static void main(String[] args) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); ServerSocket serverSocket = new ServerSocket(9999); Socket socket = serverSocket.accept(); InputStream is = socket.getInputStream()) {
            // 管道流
            // 等待客户端连接
            // 读取客户端消息
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            System.out.println(bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
