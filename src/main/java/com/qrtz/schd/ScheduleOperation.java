package com.qrtz.schd;

public enum ScheduleOperation {
	
	RUN("R"), RUN_ONCE("O"), PAUSE("P");
	
	final private String code;
	
	private ScheduleOperation(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
}
