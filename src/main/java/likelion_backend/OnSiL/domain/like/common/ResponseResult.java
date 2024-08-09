package likelion_backend.OnSiL.domain.like.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseResult<T> {
    private boolean success;
    private T data;
    private String error;

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(true, data, null);
    }

    public static <T> ResponseResult<T> failure(String error) {
        return new ResponseResult<>(false, null, error);
    }
}
