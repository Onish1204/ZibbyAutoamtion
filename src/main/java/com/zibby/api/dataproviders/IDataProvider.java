package com.zibby.api.dataproviders;

import java.io.IOException;

public interface IDataProvider {

	public String[][] getData(String... columnNames) throws IOException;
}