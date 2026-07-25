package com.shoppingService.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class Test30 {
	private static final Logger log = LoggerFactory.getLogger(Test30.class);

	public static void main(String[] args) throws UnsupportedEncodingException {
		String str1 = "https://qa-pbm-admin.waseel.com/patient-prescription?access_token=eyJ0eXBlIjoibGltaXRlZF9hY2Nlc3NfdG9rZW4iLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyMzkyMDE5NTY0IiwiZXhwIjoxNjkwNTQxMjk1LCJpYXQiOjE2ODk5MzY0OTUsInJvbCI6W3siYXV0aG9yaXR5IjoicHJlc2NyaXB0aW9uLXNlcnZpY2V8MjAyMy00Mzg3In1dfQ.V36tbxZAEOKxOlSXNZ4xhQTsIcj8Yv8wYGdyhn58UE7SvM-oMksRy8iNHh0E5NOQXg4bfSo6iVabqiX6-50uA";
		String str2 = "https://qa-pbm-admin.waseel.com/patient-prescription?access_token=eyJ0eXBlIjoibGltaXRlZF9hY2Nlc3NfdG9rZW4iLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyMzkyMDE5NTY0IiwiZXhwIjoxNjkwNTQwNTM0LCJpYXQiOjE2ODk5MzU3MzQsInJvbCI6W3siYXV0aG9yaXR5IjoicHJlc2NyaXB0aW9uLXNlcnZpY2V8MjAyMy00Mzg3In1dfQ.xhmWJhSwJe79RXVH7wZr8-RPFPmE4eqy3x_jVvSx6NV0u-hNhCebi7KEQ0VrwStc9Q4djpjDdbtSJkBlhmuGiw";
		log.info("{}", str1.equals(str2));
	}
}
