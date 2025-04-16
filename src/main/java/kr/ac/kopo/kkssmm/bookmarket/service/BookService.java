package kr.ac.kopo.kkssmm.bookmarket.service;

import kr.ac.kopo.kkssmm.bookmarket.domain.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    List<Book> getAllBookList();
    Book getBookById(String BookId);
}