package mware_lib;

import java.io.Serializable;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	private String command;
	private Object ret_val;
	private Object payload;
	
	public Message() {
	}
	
	public String getCommand() {
		return command;
	}
	
	public void setCommand(String command) {
		this.command = command;
	}
	
	public Object getRet_val() {
		return ret_val;
	}
	
	public void setRet_val(Object return_code) {
		this.ret_val = return_code;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Object:" + this.hashCode() + " ");
		
		if(command == null) {
			sb.append("Command: NULL");
		} else {
			sb.append("Command: " + command);
		}

		if(ret_val == null) {
			sb.append("Return-Value: NULL");
		} else {
			sb.append("Return-Value: " + ret_val);
		}
		
		if(payload == null) {
			sb.append("Payload: NULL");
		} else {
			sb.append("Payload: " + payload);
		}
		
		//sb.append("\n");
		
		return sb.toString();
	}
}
