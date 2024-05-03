package com.example.travelexperts.Model;

public class Messages {
    private int messageId;
    private String msgDate;
    private String msgContent;
    private int agentId;
    private int customerId;

    public Messages (int messageId, String msgDate, String msgContent, int agentId, int customerId){
        this.messageId = messageId;
        this.msgDate = msgDate;
        this.msgContent = msgContent;
        this.agentId = agentId;
        this.customerId = customerId;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getMsgDate() {
        return msgDate;
    }
    public void setMsgDate(String msgDate) {
        this.msgDate = msgDate;
    }
    public String getMsgContent(){
        return msgContent;
    }
    public void setMsgContent(String msgContent){
        this.msgContent = msgContent;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}