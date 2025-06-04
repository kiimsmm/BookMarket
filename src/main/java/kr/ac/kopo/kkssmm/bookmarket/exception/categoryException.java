package kr.ac.kopo.kkssmm.bookmarket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 카테고리를 찾을 수 없을 때 처리하기 위한 예외처리
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class categoryException extends RuntimeException{
    private String errMessage;
    public categoryException() {
        super();
        errMessage = "요청한 도서 분야를 찾을 수 없습니다.";
    }
}
