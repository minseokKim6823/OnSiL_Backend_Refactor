package likelion_backend.OnSiL.domain.healthnews.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NewsControllerAdvice {

    @ExceptionHandler(NewsNullException.class)
    public ResponseEntity<String> handleNewsNullException(NewsNullException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(NaverApiCallException.class)
    public ResponseEntity<String> handleNaverApiCallException(NaverApiCallException e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
    }
}
