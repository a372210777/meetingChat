package com.example.datamodel;

/**
 * 消息接口 派生我的消息和其他人的消息
 * */
public interface CommonMes {

	public boolean getIsSending();
	public void setSending(boolean isSending);
	public boolean isSuccess();
	public void setSuccess(boolean isSuccess);
	
	public String getAuthor();
	public String getTime();
	public String getContent();
	public boolean isSender();
}
