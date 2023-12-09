package pt.ulisboa.tecnico.rnl.dei.deixmas.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	private static Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

	// Handle failed argument validation
	@Override
   	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new DeixmasExceptionDto(ex), HttpStatus.BAD_REQUEST);
   	}

	@ExceptionHandler(DeixmasException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public DeixmasExceptionDto deixmasException(DeixmasException e) {
		return new DeixmasExceptionDto(e);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public DeixmasExceptionDto unexpectedException(Exception e) {
		logger.error(e.getMessage(), e);
		return new DeixmasExceptionDto(e);
	}
}
