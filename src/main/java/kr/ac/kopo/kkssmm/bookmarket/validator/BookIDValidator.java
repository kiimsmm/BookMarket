package kr.ac.kopo.kkssmm.bookmarket.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kr.ac.kopo.kkssmm.bookmarket.domain.Book;
import kr.ac.kopo.kkssmm.bookmarket.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;

public class BookIDValidator implements ConstraintValidator<bookId, String> {
    @Autowired
    private BookService bookService;

    @Override
    public void initialize(bookId constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String bookId, ConstraintValidatorContext constraintValidatorContext) {
        Book book = null;
        try{
            book = bookService.getBookById(bookId);
        } catch (RuntimeException e) {
            return true;
        }
        if(book != null) {
            return false;
        }
        return true;
    }
}