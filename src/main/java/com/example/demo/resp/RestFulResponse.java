package com.example.demo.resp;

import lombok.Data;

@Data
public class RestFulResponse {
//	private Integer code;
	private String msg ;
	private Long timestamp;
	private Object data;

	public RestFulResponse(){
		this("success");
	}

	public RestFulResponse(Object data){
		this();
		this.data=data;
	}

	public RestFulResponse(String message){
		this.msg = message;
		this.timestamp = System.currentTimeMillis();
	}

	public RestFulResponse(String message, Object data){
		this.msg = message;
		this.data=data;
		this.timestamp = System.currentTimeMillis();
	}

	public static RestFulResponse build( String message){
		return new RestFulResponse(message);
	}

	public static RestFulResponse ok(){
		return new RestFulResponse();
	}

	public static RestFulResponse ok(Object data){
		return new RestFulResponse(data);
	}

	public static RestFulResponse ok(String message){
		return new RestFulResponse(message);
	}

	public static RestFulResponse badReq(String message){
		return new RestFulResponse(message);
	}

	public static RestFulResponse tooManyReqs(){
		return new RestFulResponse("Too Many Requests");
	}


}
