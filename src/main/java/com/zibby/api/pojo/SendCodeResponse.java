package com.zibby.api.pojo;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.zibby.api.managers.SerializableObject;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"new_user"
})
public class SendCodeResponse extends SerializableObject{

@JsonProperty("new_user")
private Boolean newUser;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("new_user")
public Boolean getNewUser() {
return newUser;
}

@JsonProperty("new_user")
public void setNewUser(Boolean newUser) {
this.newUser = newUser;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

@Override
public String toString() {
return new ToStringBuilder(this).append("newUser", newUser).append("additionalProperties", additionalProperties).toString();
}

}