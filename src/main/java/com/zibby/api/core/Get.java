package com.zibby.api.core;

import java.util.List;

public interface Get<T> {

	public T get(String id);

	public List<T> list();
}