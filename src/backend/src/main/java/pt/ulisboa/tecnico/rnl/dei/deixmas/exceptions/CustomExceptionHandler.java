package pt.ulisboa.tecnico.rnl.dei.deixmas.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	private static Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

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
