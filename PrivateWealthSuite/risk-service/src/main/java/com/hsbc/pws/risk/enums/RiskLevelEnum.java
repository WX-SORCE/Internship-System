package com.hsbc.pws.risk.enums;


public enum RiskLevelEnum {
	/** 保守型 */
	CONSERVATIVE(0, "CONSERVATIVE"),
	/** 稳健型 */
	BALANCED(1, "BALANCED"),
	/** 进取型 */
	AGGRESSIVE(2, "AGGRESSIVE");

	private Integer status;
	private String msg;

	RiskLevelEnum(Integer status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getStatus(int status) {
		for (RiskLevelEnum r : RiskLevelEnum.values()) {
			if (r.getStatus() == status) {
				return status;
			}
		}
		return -1;
	}

	public String getMsg(int status) {
		for (RiskLevelEnum r : RiskLevelEnum.values()) {
			if (r.getStatus() == status) {
				return r.getMsg();
			}
		}
		return "undefined";
	}

	@Override
	public String toString() {
		return "{\"status\":" + status + ",\"msg\":\"" + msg + "\"}";
	}
}
