package com.zibby.auto;

public enum Environment {

	QA("https://qa.zibby.com/app/login");

	private final String baseUrl;

	Environment(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public static String getBaseUrl() {
		return getEnv().baseUrl;
	}

	public static Environment getEnv() {
		return Environment.valueOf((Configuration.EXECUTION_ENVIRONMENT.toUpperCase()));
	}

}
