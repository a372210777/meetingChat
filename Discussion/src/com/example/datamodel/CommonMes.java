package com.example.datamodel;

/**
 * ��Ϣ�ӿ� �����ҵ���Ϣ�������˵���Ϣ
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
