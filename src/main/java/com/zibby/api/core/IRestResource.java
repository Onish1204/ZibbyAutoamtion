package com.zibby.api.core;

import java.util.List;

public interface IRestResource<T> {

    public T get(String id);

    public List<T> list();

    public String post(T content);

    public void put(T content, String id);

    public void delete(String id);
}