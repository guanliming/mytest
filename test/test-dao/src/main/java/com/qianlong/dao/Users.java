package com.qianlong.dao;

public enum Users {
	PETER("wfw323");
	private String userName;

	private Users(final String name) {
		this.userName = name;
	}

	@Override
	public String toString() {
		return userName;
	}


}
