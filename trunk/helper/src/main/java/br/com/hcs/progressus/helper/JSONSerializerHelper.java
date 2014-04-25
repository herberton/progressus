package br.com.hcs.progressus.helper;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collection;

import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.to.ProgressusTO;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

@Slf4j
public class JSONSerializerHelper<T extends ProgressusTO<T>> implements JsonSerializer<T> {

	@Override
	public JsonElement serialize(T to, Type type, JsonSerializationContext jsonSerializationContext) { 
		
		JsonObject jsonObject = new JsonObject();
		
		try {

			for (Field field : to.getPublicFieldList()) {

				Object value = null;
				
				try {
					
					value = to.get(field.getName());
					
					if (ObjectHelper.isNullOrEmpty(value)) {
						continue;
					}
					
				} catch (Exception e) {
					continue;
				}
				
				JsonElement jsonElement = null;
				
				try {
					
					jsonElement = this.getJsonElement(jsonSerializationContext, value);
					
				} catch (Exception e) {
					continue;
				}
				
				if (jsonElement == null) {
					continue;
				}

				jsonObject.add(field.getName(), jsonElement);
			}
			
		} catch (Exception e) {
			JSONSerializerHelper.log.error(e.getMessage(), e);
		}
		
		return jsonObject;
	}

	
		
	private JsonElement getJsonElement(JsonSerializationContext jsonSerializationContext, Object value) {
		
		JsonElement jsonElement = null;
		
		try {
			
			if (value.getClass().isPrimitive() || value instanceof Number || value instanceof String) {
				
				jsonElement = jsonSerializationContext.serialize(value);
				
			} else  if (value instanceof Collection) {
				
				jsonElement = new JsonArray();
				
				for (Object item : ((Collection<?>)value)) {
					
					((JsonArray)jsonElement).add(this.getJsonElement(jsonSerializationContext, item));
					
				}
				
			} else  if (value.getClass().isArray()) {
				
				jsonElement = new JsonArray();
				
				for (int i = 0; i < Array.getLength(value); i++) {
					
					((JsonArray)jsonElement).add(this.getJsonElement(jsonSerializationContext, Array.get(value, i)));
					
				}
				
			} else if (value instanceof ProgressusTO<?>) {
				
				jsonElement = ((ProgressusTO<?>)value).getGson().toJsonTree(value);
				
			} else {
				
				jsonElement = jsonSerializationContext.serialize(value);
				
			}
			
		} catch (Exception e) {
			JSONSerializerHelper.log.error(e.getMessage(), e);
		}
		
		return jsonElement;
	}

}