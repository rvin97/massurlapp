package com.massurl.url;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Url Not Found")
public class UrlNotFoundException extends RuntimeException{

}
