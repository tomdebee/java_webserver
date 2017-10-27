package nl.sogyo.webserver;

import java.util.*;

public class RequestParser implements Request{
	
	private HttpMethod reqHttpMethod;
	private String resourcePath; 
	private String httpVersion;
	
	private Map<String, String> reqHeader = new HashMap<String,String>();
	private Map<String, String> reqParam = new HashMap<String,String>();

	
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
    	List<String> paramNames = new ArrayList<String>();
    	for(Map.Entry<String, String> paramEntry : reqParam.entrySet()) {
    		paramNames.add(paramEntry.getKey());
    	}
    	return paramNames;
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
	
    void parseHTTPargs(String httpArgs) {
    	
    	String params = "";
    	if(httpArgs.indexOf("?") >= 0) {
			params = httpArgs.substring(httpArgs.indexOf("?") + 1); 
		}
		
		while(params.length() > 0) {
			String paramName = params.substring(0,params.indexOf("="));
			String paramValue = "";
			if(params.indexOf("&")>0) {
				paramValue = params.substring(params.indexOf("=") + 1,params.indexOf("&"));
				reqParam.put(paramName, paramValue);
			} else {
				paramValue = params.substring(params.indexOf("=") + 1);
				reqParam.put(paramName, paramValue);
				break;
			}
			params = params.substring(params.indexOf("&") + 1);
		}
		
		
    }
	
    
    void input(String line, int lineNumber) {
		try {
				if(lineNumber==0 && !line.isEmpty()) {
				String[] line0args = line.split(" ");
				resourcePath = line0args[1];
				
				if(line0args[1].indexOf("?")>0) {
					resourcePath = line0args[1].substring(0, line0args[1].indexOf("?"));
				}
				httpVersion = line0args[2];
				
				if(line0args[0].equals("GET")) {
					reqHttpMethod = HttpMethod.GET;
					parseHTTPargs(line0args[1]);
				} else if (line0args[0].equals("POST")) {
					reqHttpMethod = HttpMethod.POST;

				}
			} else if (line != null){
				String headerParam = line.substring(0, line.indexOf(": "));
				String headerParamValue = line.substring(line.indexOf(": ") + 2); 
				
				if(headerParam != null & headerParamValue != null) {
					reqHeader.put(headerParam, headerParamValue); 
				}
			}
		} catch (ArrayIndexOutOfBoundsException aoobe) {
			System.out.println(aoobe);
		}
	}
	
	
}
