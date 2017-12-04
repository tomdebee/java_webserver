# java webserver
As part of an assignment at Sogyo to learn about the HTTP-protocol, I made a webserver in java capable of parsing both GET and POST requests and subsequently replying with the parameters that were parsed from them.

## Description
As mentioned above, this repository contains a webserver written in java. 
The original skeletonproject was derived from the Sogyo Academy, but the implementation was conceived by me. The skeletonproject can be found in a different branch.

## Theory
Part of the HTTP Protocol is the interpretation of the parameters that are sent in the GET and POST requests, as well as the syntax that is necessary to produce a proper HTTP response. 
Basically, a web-application can receive the parameters from this webserver and use them to do application-y things.

## Usage

When running the server (after building the code yourself, possible with Maven. Or inside a GUI such as Eclipse/IntelliJ), the webserver can be found with any broswer on `localhost:9090`.
The webserver can reply to GET and POST requests and in essence produces a message as such:
```
HTTP/1.1 200 OK
Date: Thu, 10 Oct 2017 23:26:07 GMT
Server: minneolaserver/0.0.15
Connection: close
Content-Type: text/html
Content-Length: 12

Hello world!
```
However, instead of `Hello world!`, HTML code can be passed along as well.




You can send a GET request like this: `http://localhost:9090/somefolder?parameter=value&para2=val2`. The server will reply as such:
```
You did an HTTP GET request.
Requested resource: /somefolder

The following parameters were passed:
parameter: value
para2: val2
```

POST requests are also possible, possibly by use of an application such as [Postman](https://www.getpostman.com/), which provides an easy GUI to compile a POST request.


## Usage
Currently no real server-side scripting is implemented, so any and all browsers can locally open the index.html to view the website.
