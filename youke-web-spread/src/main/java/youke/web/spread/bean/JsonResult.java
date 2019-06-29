package youke.web.spread.bean;

import youke.web.spread.common.conts.ApiCodeConstant;

import java.io.Serializable;

public class JsonResult implements Serializable{

	/**
	 * 状态 1000成功，1002失败，参考ApiCodeConstant
	 */
	private int code;
	/**
	 * 提示信息， 当code＝1000时， 返回  成功，，
	 */
	private String message;
	/**
	 * 数据封装
	 */
	private Object data;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
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
	public void setData(Object data) {
		this.data = data;
	}
	public JsonResult(int code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
	public JsonResult(int code, String message) {
		this.code = code;
		this.message = message;
		this.data = null;
	}

	public JsonResult(Object data) {
		this.code = ApiCodeConstant.COMMON_SUCCESS;
		this.message = "成功";
		this.data = data;
	}

	public JsonResult() {
		this.code = ApiCodeConstant.COMMON_SUCCESS;
		this.message = "成功";
	}


}
