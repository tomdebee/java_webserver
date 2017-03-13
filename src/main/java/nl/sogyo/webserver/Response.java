package nl.sogyo.webserver;

import java.time.LocalDate;

public interface Response {
    HttpStatusCode getStatus();
    void setStatus(HttpStatusCode status);
    ZonedDateTime getDate();
    String getContent();
    void setContent(String content);
}