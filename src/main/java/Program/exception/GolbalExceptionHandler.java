package Program.exception;

import java.io.IOException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GolbalExceptionHandler {
	@ExceptionHandler(IOException.class)
	public String handleException() {
		return "index";
	}
}
