package nl.sogyo.webserver;

import java.util.*;

public class RequestParser implements Request{
	
	private HttpMethod reqHttpMethod;
	private String resourcePath; 
	private String httpVersion;
	
	private Map<String, String> reqHeader = new HashMap<String,String>();
	private Map<String, String> reqParam = new HashMap<String,String>();
	private ArrayList<String> reqBody;

	
	public List<String> getHeaderParameterNames() {
		List<String> headerNames = new ArrayList<String>();
    	for(Map.Entry<String, String> reqHTTPentry : reqHeader.entrySet()) {
    		headerNames.add(reqHTTPentry.getKey());
    	}
    	return headerNames;
    }
    
    public String getHeaderParameterValue(String name) {
    	return reqHeader.get(name);
    }
    
    public List<String> getParameterNames() {
    	List<String> headerParamNames = new ArrayList<String>();
    	for(Map.Entry<String, String> reqParamEntry : reqHeader.entrySet()) {
    		headerParamNames.add(reqParamEntry.getKey());
    	}
    	return headerParamNames;
    }
    
    public String getParameterValue(String name) {
    	return reqParam.get(name);
    };
	
	
    public HttpMethod getHTTPMethod() {
    	return reqHttpMethod;
    }
    public String getResourcePath() {
    	return resourcePath;
    }
    public String getHttpVersion() {
    	return httpVersion;
    }
	
	
    void input(String line, int lineNumber) {
		
		if(lineNumber==0 && !line.isEmpty()) {
			String[] line0args = line.split(" "); 
			if(line0args[0].equals("GET")) {
				reqHttpMethod = HttpMethod.GET;
			} else if (line0args[0].equals("POST")) {
				reqHttpMethod = HttpMethod.POST;
			}
			resourcePath = line0args[1];
			httpVersion = line0args[2];
		} else if (line != null){
			String param = line.substring(0, line.indexOf(": "));
			String paramValue = line.substring(line.indexOf(": ") + 2); 
			if(param != null & paramValue != null) {
				reqHeader.put(param, paramValue); 
			}
		}
	}
	
	
}
