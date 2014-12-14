package mware_lib;

import java.io.Serializable;

public class Message implements Serializable {
	public enum MessageReason {
		UNDEFINED, REBIND, RESOLVE, RESOLVE_REPLY, METHOD_CALL, METHOD_RETURN, EXCEPTION, ERROR
	}
	
	private static final long serialVersionUID = 10L;
	private static int message_id_counter = 0;
	private int message_id;
	private MessageReason reason;
	private String method_name;
	private Object[] method_params;
	private String objName;
	private Object method_return;
	private Object payload;
	
	public Message() {
		message_id_counter++;
		message_id = message_id_counter;
		reason = MessageReason.UNDEFINED;
		method_name = null;
		method_params = null;
		method_return = null;
		payload = null;
	}

	public MessageReason getReason() {
		return reason;
	}

	public void setReason(MessageReason reason) {
		this.reason = reason;
	}

	public String getMethod_name() {
		return method_name;
	}

	public void setMethod_name(String method_name) {
		this.method_name = method_name;
	}

	public Object[] getMethod_params() {
		return method_params;
	}

	public void setMethod_params(Object[] method_params) {
		this.method_params = method_params;
	}
	
	public String getObjName() {
		return objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

	public Object getMethod_return() {
		return method_return;
	}

	public void setMethod_return(Object method_return) {
		this.method_return = method_return;
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
		
		sb.append("Message: #" + message_id 
				+ " reason: " + reason
				+ " method_name: " + method_name 
				+ " method_params: ");
		
		if(method_params != null) {
			for(int i=0; i<method_params.length; i++) {
				sb.append(method_params[i] + " ");
			}
		} else {
			sb.append("null ");
		}
		
		sb.append("method_return: " + method_return
				+ " payload: " + payload);
				
		return sb.toString();
	}
}
