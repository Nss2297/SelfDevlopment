package com.waseel.pbm.pbmadminservice.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.waseel.pbm.pbmadminservice.model.CommonResponse;

@RestControllerAdvice
public class FileUploadExceptionHandler {

	private final Logger log = LoggerFactory.getLogger(FileUploadExceptionHandler.class);

	private static final String INVALID_FILE_SIZE_MESSAGE = "File size exceeds the maximum allowed size (5MB).";

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<CommonResponse> handleMaxSizeException(MaxUploadSizeExceededException ex) {
		log.info("MaxUploadSizeExceededException: Has Been Returned From PBM-Admin-Service Due To : {} ",
				ex.getMessage());
		List<String> errorDescList = new ArrayList<>();
		errorDescList.add(INVALID_FILE_SIZE_MESSAGE);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CommonResponse(errorDescList));
	}
}
