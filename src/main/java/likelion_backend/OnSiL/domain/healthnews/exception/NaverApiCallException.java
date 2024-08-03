package likelion_backend.OnSiL.domain.healthnews.exception;

public class NaverApiCallException extends RuntimeException {
    public NaverApiCallException() {
        super("Failed to call Naver API.");
    }
}
