package kr.ac.kopo.kkssmm.bookmarket.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kr.ac.kopo.kkssmm.bookmarket.domain.Book;
import kr.ac.kopo.kkssmm.bookmarket.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;

public class BookIDValidator implements ConstraintValidator<BookID, String> {
    @Autowired
    private BookService bookService;

    @Override
    public void initialize(BookID constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String bookID, ConstraintValidatorContext constraintValidatorContext) {
        Book book = null;
        try{
            book = bookService.getBookById(bookID);
        } catch (RuntimeException e) {
            return true;
        }
        if(book != null) {
            return false;
        }
        return true;
    }
}