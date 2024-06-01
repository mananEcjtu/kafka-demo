package com.network.udp;

import java.io.IOException;
import java.net.*;

// 不需要连接服务器
public class UdpClient {
    public static void main(String[] args) throws IOException {
        // 1. 建立socket
        DatagramSocket socket = new DatagramSocket();

        // 2. 建包
        String msg = "你好，服务器";
        InetAddress localhost = InetAddress.getByName("localhost");
        int port = 9090;
        // 数据，数据长度，发给谁
        DatagramPacket datagramPacket = new DatagramPacket(msg.getBytes(), 0, msg.getBytes().length, localhost, port);

        // 3.发包
        socket.send(datagramPacket);
        socket.close();
    }
}
