package com.luv2code.springsecurity.demo.dto;

public class ResponseDTO<T> {
	private String message;
	private T data;
	private int error;
	private String time;
	
	public ResponseDTO(String message, T data, String time) {
        this.message = message;
        this.data = data;
        this.time = time;
        this.error = 0;  // No error in case of success
    }
	
	public ResponseDTO(String message, T data, int error, String time) {
		this.message = message;
		this.data = data;
		this.error = error;
		this.time = time;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public int getError() {
		return error;
	}
	public void setError(int error) {
		this.error = error;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
}
