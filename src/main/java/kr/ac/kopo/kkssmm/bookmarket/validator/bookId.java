package kr.ac.kopo.kkssmm.bookmarket.validator;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = BookIDValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface bookId {
    String message() default "{bookId.book.bookId}";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}