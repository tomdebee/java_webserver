package nl.sogyo.webserver;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ResponseMessage implements Response{
	
	private HttpStatusCode status;
	private String content = "";
	
    public void setStatus(HttpStatusCode status) {
    	this.status = status;
    };
    public void setContent(String content) {
    	this.content = content;
    };
    
    public HttpStatusCode getStatus() {
		return status;
	};
    
    public ZonedDateTime getDate() {
    	ZonedDateTime zdt = ZonedDateTime.now();
    	return zdt;
    };
    
    public String getContent() {
    	return content;
    };
    
}
