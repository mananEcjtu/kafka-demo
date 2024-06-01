package com.network.chat;

public class TalkStudent {
    public static void main(String[] args) {
        // 开启两个线程
        new Thread(new TalkSend("localhost", 7777)).start();
        new Thread(new TalkReceive(9999, "老师")).start();
    }
}
