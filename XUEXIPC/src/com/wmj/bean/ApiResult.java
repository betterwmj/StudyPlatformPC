package com.wmj.bean;

public class ApiResult {
   private int code;
   private Object data;
	private String message;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
   public static ApiResult success(Object data){
	   ApiResult result = new ApiResult();
	   result.setCode(0);
	   result.setData(data);
	   return result;
   }
   
   public static ApiResult fail(String msg){
	   ApiResult result = new ApiResult();
	   result.setCode(-1);
	   result.setMessage(msg);
	   return result;
   }
}
