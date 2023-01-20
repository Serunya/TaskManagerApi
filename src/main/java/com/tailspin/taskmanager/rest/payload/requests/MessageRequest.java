package com.tailspin.taskmanager.rest.payload.requests;

public class MessageRequest {
    private String content;
    private int chatId;

    public MessageRequest(String content, int chatId) {
        this.content = content;
        this.chatId = chatId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }
}
