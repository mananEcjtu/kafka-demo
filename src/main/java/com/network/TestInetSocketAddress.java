package com.network;

import java.net.InetSocketAddress;

public class TestInetSocketAddress {
    public static void main(String[] args) {
        test01();
    }

    private static void test01() {
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 8080);
        InetSocketAddress socketAddress1 = new InetSocketAddress("localhost", 8080);

        System.out.println(socketAddress1.getHostName());
        System.out.println(socketAddress1.getAddress().getHostAddress());
        System.out.println(socketAddress1.getPort());
    }
}
