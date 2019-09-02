package com.zibby.api.request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;

public class XObjectMapper implements ObjectMapper {

	protected final com.fasterxml.jackson.databind.ObjectMapper jsonSerializer;
	protected final com.fasterxml.jackson.databind.ObjectMapper urlencoderSerializer;
	protected final com.fasterxml.jackson.databind.ObjectMapper jsonDeserializer;
	protected final com.fasterxml.jackson.databind.ObjectMapper urlencoderDeserializer;

	public XObjectMapper() {
		jsonSerializer = new com.fasterxml.jackson.databind.ObjectMapper();
		jsonDeserializer = new com.fasterxml.jackson.databind.ObjectMapper();
		urlencoderSerializer = new com.fasterxml.jackson.databind.ObjectMapper();
		urlencoderDeserializer = new com.fasterxml.jackson.databind.ObjectMapper();
	}

	public XObjectMapper(JsonInclude.Include include, PropertyNamingStrategy strategy) {
		jsonSerializer = new com.fasterxml.jackson.databind.ObjectMapper();
		jsonDeserializer = new com.fasterxml.jackson.databind.ObjectMapper();
		urlencoderSerializer = new com.fasterxml.jackson.databind.ObjectMapper();
		urlencoderDeserializer = new com.fasterxml.jackson.databind.ObjectMapper();
		jsonSerializer.setSerializationInclusion(include);
		jsonDeserializer.setSerializationInclusion(include);
		jsonDeserializer.setPropertyNamingStrategy(strategy);
		urlencoderSerializer.setSerializationInclusion(include);
		urlencoderDeserializer.setSerializationInclusion(include);
		urlencoderDeserializer.setPropertyNamingStrategy(strategy);
	}

	public XObjectMapper(PropertyNamingStrategy strategy) {
		jsonSerializer = new com.fasterxml.jackson.databind.ObjectMapper();
		jsonDeserializer = new com.fasterxml.jackson.databind.ObjectMapper();
		urlencoderSerializer = new com.fasterxml.jackson.databind.ObjectMapper();
		urlencoderDeserializer = new com.fasterxml.jackson.databind.ObjectMapper();
		jsonDeserializer.setPropertyNamingStrategy(strategy);
		urlencoderDeserializer.setPropertyNamingStrategy(strategy);
	}

  @Override
  public Object deserialize(ObjectMapperDeserializationContext omdc) {
    return null;
}
		//		String data = omdc.getDataToDeserialize().asString();
//		/*
//		 * if (omdc.getType().equals(String.class)) { return data;
//		 *
//		 * }
//		 */// On commenting this code, script will execute when a service have text/plain
//			// response
//
//		if ((omdc.getContentType().contains("json")) && (omdc.getType() != null)) {
//			try {
//				return null;
//				//return jsonDeserializer.readValue(data, omdc.getType());
//			} catch (IOException ex) {
//				String msg = "Error reading as JSON: " + ex.getMessage() + "\nCaused by: " + data;
//				throw new RuntimeException(msg, ex);
//			}
//		} else if (omdc.getContentType().contains("xml")) {
//			try {
//				JAXBContext jaxbContext = JAXBContext.newInstance(omdc.getType());
//				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//				ByteArrayInputStream bais = new ByteArrayInputStream(data.getBytes(Charset.forName(omdc.getCharset())));
//				JAXBElement<?> element = jaxbUnmarshaller.unmarshal(new StreamSource(bais), omdc.getType());
//				bais.close();
//				return element.getValue();
//			} catch (JAXBException ex) {
//				String msg = "Error reading as XML: " + ex.getMessage() + "\nCaused by: " + data;
//				throw new RuntimeException(msg, ex);
//			} catch (IOException ex) {
//				throw new RuntimeException("Error closing byte array stream.", ex);
//			}
//		} else if (omdc.getContentType().contains("x-www-form-urlencoded")) {
//			try {
//				return jsonDeserializer.readValue(data, omdc.getType());
//			} catch (IOException ex) {
//				throw new RuntimeException("Error closing byte array stream.", ex);
//			}
//		} else if ((omdc.getContentType().contains("text/plain"))) {
//			try {
//				return null;
//			} catch (Exception ex) {
//				String msg = "Error reading as JSON: " + ex.getMessage() + "\nCaused by: " + data;
//				throw new RuntimeException(msg, ex);
//			}
//		} else if ((omdc.getContentType().contains("json")) && (omdc.getType() == null)) {
//			try {
//				return data;
//			} catch (Exception ex) {
//				String msg = "Error reading as JSON: " + ex.getMessage() + "\nCaused by: " + data;
//				throw new RuntimeException(msg, ex);
//			}
//		} else {
//			throw new RuntimeException(
//					"Don't know how to parse content type \"" + omdc.getContentType() + "\". Error reading " + data);
//		}
//	}

	@Override
	public Object serialize(ObjectMapperSerializationContext omsc) {
		if (omsc.getContentType().contains("json")) {
			try {
				return jsonSerializer.writeValueAsString(omsc.getObjectToSerialize());
			} catch (JsonProcessingException ex) {
				throw new RuntimeException("Error writing " + omsc.getObjectToSerialize().getClass().getCanonicalName()
						+ " as JSON. Reason: " + ex.getMessage(), ex);
			} catch (IOException ex) {
				throw new RuntimeException("Error writing " + omsc.getObjectToSerialize().getClass().getCanonicalName()
						+ " as JSON. Reason: " + ex.getMessage(), ex);
			}
		} else if (omsc.getContentType().contains("xml")) {
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(omsc.getObjectToSerialize().getClass());
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
				StringWriter sw = new StringWriter();
				if (jaxbContext.createJAXBIntrospector().isElement(omsc.getObjectToSerialize())) {
					jaxbMarshaller.marshal(omsc.getObjectToSerialize(), sw);
				} else {
					throw new RuntimeException("Need access to the dev apis in order to find the root element");
				}
				return sw.toString();
			} catch (JAXBException ex) {
				throw new RuntimeException("Error writing " + omsc.getObjectToSerialize().getClass().getCanonicalName()
						+ " as XML. Reason: " + ex.getMessage(), ex);
			}
		} else if (omsc.getContentType().contains("x-www-form-urlencoded")) {
			try {
				return urlencoderSerializer.writeValueAsString(omsc.getObjectToSerialize());
			} catch (IOException ex) {
				throw new RuntimeException("Error writing " + omsc.getObjectToSerialize().getClass().getCanonicalName()
						+ " as \"x-www-form-urlencoded\". Reason: " + ex.getMessage(), ex);
			}
		} else {
			throw new RuntimeException("Don't know how to parse content type \"" + omsc.getContentType()
					+ "\". Error writing " + omsc.getObjectToSerialize().getClass().getCanonicalName());
		}
	}

	public Object deserialize() {
		return null;

	}
}
