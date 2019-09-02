package com.zibby.api.request;


import com.zibby.api.mediatypes.BasicMediaTypes;

import io.restassured.internal.mapping.ObjectMapperDeserializationContextImpl;
import io.restassured.internal.mapping.ObjectMapperSerializationContextImpl;
import io.restassured.mapper.DataToDeserialize;

public class RequestProcessor {

	private final BasicMediaTypes mediaType;
	private final XObjectMapper mapper;

	public RequestProcessor(BasicMediaTypes mediaType, XObjectMapper mapper) {
		this.mediaType = mediaType;
		this.mapper = mapper;
	}

	public Object serialize(final Object obj) {
		ObjectMapperSerializationContextImpl omsc = new ObjectMapperSerializationContextImpl();
		omsc.setCharset("UTF-8");
		omsc.setContentType(this.mediaType.getContentType());
		omsc.setObject(obj);

		return this.mapper.serialize(omsc);
	}

	public <T> T deserialize(final Class<T> clazz, final String text) {
		DataToDeserialize dtd = new Deserializer(text);

		ObjectMapperDeserializationContextImpl omdc = new ObjectMapperDeserializationContextImpl();
		omdc.setCharset("UTF-8");
		omdc.setContentType(this.mediaType.getContentType());
		omdc.setType(clazz);
		omdc.setDataToDeserialize(dtd);

		return (T) this.mapper.deserialize(omdc);
	}

	public String getSerializationType() {
		return this.mediaType.getSerialType();
	}
}