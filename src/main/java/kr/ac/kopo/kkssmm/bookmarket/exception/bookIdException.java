package kr.ac.kopo.kkssmm.bookmarket.exception;

public class bookIdException extends RuntimeException{
    private String bookId;
    public bookIdException(String bookId) {
        super();
        this.bookId = bookId;
    }

    public String getBookId() {
        return bookId;
    }
}
