package nl.sogyo.webserver;

import java.util.*;

public class RequestParser implements Request{
	
	
	private HttpMethod httpMethod;
	private String resourcePath; 
	private String httpVersion;
	
	Map<String, String> requestParams = new HashMap<String, String>();
	
	void input(String line, int lineNumber) {
		
		System.out.print("[" + lineNumber + "]");
		
		if(lineNumber==0 && !line.isEmpty()) {
			String[] line0args = line.split(" "); 
			if(line0args[0].equals("GET")) {
				httpMethod = httpMethod.GET;
			} else if (line0args[0].equals("POST")) {
				httpMethod = httpMethod.POST;
			}
			resourcePath = line0args[1];
			System.out.println(line0args[0]);
		}
	}
	
	public HttpMethod getHTTPMethod() {
    	return httpMethod;
    }
    public String getResourcePath() {
    	return resourcePath;
    }
}
