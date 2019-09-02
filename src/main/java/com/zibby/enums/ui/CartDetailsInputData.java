package com.zibby.enums.ui;

import java.util.HashMap;

public enum CartDetailsInputData implements SampleData<String>{
	
	Sample1{

		@Override
		public HashMap<String, String> getSampleData() {
			HashMap<String,String> cartMap = new HashMap<String, String>();
			cartMap.put("Sales Rep Name", "Jane Doe");
			cartMap.put("OrderNumber", "ABC1234");
			cartMap.put("DeliveryMethod", "In-Store Pickup");
			cartMap.put("ItemType", "new");
			cartMap.put("Quantity", "1");
			cartMap.put("CashPrice", "550.00");
			cartMap.put("ModelNumber", "19902019");
			cartMap.put("ItemDescription", "Something of Nothing");
			return cartMap;
		}
		
	}

}
