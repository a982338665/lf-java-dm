package org.zstacks.zbus.client.rpc;

import org.zstacks.zbus.client.ZbusException;
import org.zstacks.znet.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
 

public class JsonCodec implements Codec {  
	private static final String DEFAULT_ENCODING = "UTF-8"; 
	
	public Message encodeRequest(Request request) {
		Message msg = new Message(); 
		String encoding = request.getEncoding();
		if(encoding == null) encoding = DEFAULT_ENCODING;  
		msg.setBody(toJSONBytes(request, encoding,
				SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteClassName));
		return msg;
	}
	
	public Request decodeRequest(Message msg) {
		String encoding = msg.getEncoding();
		if(encoding == null){
			encoding = DEFAULT_ENCODING;
		}
		String jsonString = msg.getBodyString(encoding);
		Request req = JSON.parseObject(jsonString, Request.class);
		return req;
	}
	
	public Object normalize(Object param, Class<?> targetType) throws ClassNotFoundException {
		if(param instanceof JSON){ //转换为目标类型 
			try{
				return JSON.toJavaObject((JSON)param, targetType);
			}catch(JSONException jsonException){
				return param;
			}
		}
		if(targetType.getName().equals("java.lang.Class")){
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			return classLoader.loadClass(param.toString());
		}
		return param;
	}
	
	public Message encodeResponse(Response response) {
		Message msg = new Message(); 
		msg.setStatus("200"); 
		if(response.getError() != null){
			Throwable error = response.getError(); 
			if(error instanceof IllegalArgumentException){
				msg.setStatus("400");
			} else {
				msg.setStatus("500");
			} 
		}  
		String encoding = response.getEncoding();
		if(encoding == null) encoding = DEFAULT_ENCODING;  
		msg.setBody(toJSONBytes(response, encoding,
				SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteClassName)); 
		return msg; 
	}
	

	public Response decodeResponse(Message msg){ 
		String encoding = msg.getEncoding();
		if(encoding == null){
			encoding = DEFAULT_ENCODING;
		}
		String jsonString = msg.getBodyString(encoding);
		Response res = null;
		try{
			res = JSON.parseObject(jsonString, Response.class);
		} catch (Exception e){ //probably error can not be instantiated
			res = new Response(); 
			JSONObject json = null;
			try{
				jsonString = jsonString.replace("@type", "unknown-class"); //禁止掉实例化
				json = JSON.parseObject(jsonString); 
			} catch(Exception ex){
				String prefix = "";
				if(msg.isStatus200()){
					prefix = "JSON format invalid: ";
				}
				throw new ZbusException(prefix + jsonString);
			} 
			if(json != null){
				final String stackTrace = Response.KEY_STACK_TRACE;
				if(json.containsKey(stackTrace)){ 
					throw new ZbusException(json.getString(stackTrace));
				}
				res.setResult(json.get(Response.KEY_RESULT));
			}
		} 
		return res;
	} 
	
	
	private static final byte[] toJSONBytes(Object object, String charsetName,
			SerializerFeature... features) {
		SerializeWriter out = new SerializeWriter();

		try {
			JSONSerializer serializer = new JSONSerializer(out);
			for (SerializerFeature feature : features) {
				serializer.config(feature, true);
			}

			serializer.write(object);

			return out.toBytes(charsetName);
		} finally {
			out.close();
		}
	}
}
