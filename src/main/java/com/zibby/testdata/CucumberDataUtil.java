package com.zibby.testdata;

import java.util.HashMap;

import cucumber.api.DataTable;
import gherkin.formatter.model.DataTableRow;

public class CucumberDataUtil {

	public static HashMap<String, String> readCucumberDataTable(DataTable table) {

		HashMap<String, String> dataMap = new HashMap<>();

		for (DataTableRow row : table.getGherkinRows()) {
			dataMap.put(row.getCells().get(0).trim(), row.getCells().get(1).trim());
		}

		return dataMap;
	}

	public static HashMap<String, String> getDataFromCucumberTable(DataTable table) {

		HashMap<String, String> dataMap = new HashMap<>();

		for (DataTableRow row : table.getGherkinRows()) {

			dataMap.put(row.getCells().get(0).trim(), row.getCells().get(1).trim());
		}

		return dataMap;
	}
}
