package com.network.chat;

public class TalkTeacher {
    public static void main(String[] args) {
        new Thread(new TalkSend("localhost", 9999)).start();
        new Thread(new TalkReceive(7777, "学生")).start();
    }
}
