package com.example.datamodel;

import java.io.Serializable;

/**
 * ��ǰ�û����͵���Ϣ
 */
public class CurrentUserMes implements Serializable,CommonMes{

	private static final long serialVersionUID = 1L;
	private String time;
	private String author;
	private String content;
	
	private boolean isSending;//�Ƿ����ڷ���
	private boolean isSuccess;//�Ƿ��ͳɹ�
	
	public CurrentUserMes(){}
	public CurrentUserMes(String time,String author,String content){
		this.time=time;
		this.author=author;
		this.content=content;
		
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String getAuthor() {
		return author;
	}

	@Override
	public String getTime() {
		return time;
	}

	@Override
	public String getContent() {
		return content;
	}

	@Override
	public boolean isSender() {
		return true;
	}
	@Override
	public boolean getIsSending() {
		return isSending;
	}
	@Override
	public void setSending(boolean isSending) {
		this.isSending=isSending;
		
	}
	@Override
	public boolean isSuccess() {
		// TODO Auto-generated method stub
		return isSuccess;
	}
	@Override
	public void setSuccess(boolean isSuccess) {
		this.isSuccess=isSuccess;
	}
	
	
	
	

	
}
