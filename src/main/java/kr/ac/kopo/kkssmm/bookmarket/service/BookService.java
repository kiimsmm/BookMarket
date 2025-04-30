package kr.ac.kopo.kkssmm.bookmarket.service;

import kr.ac.kopo.kkssmm.bookmarket.domain.Book;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public interface BookService {
    List<Book> getAllBookList();
    Book getBookById(String bookId);
    List<Book> getBooksByCategory(String category);
    Set<Book> getBookListByFilter(Map<String, List<String>> filter);
}