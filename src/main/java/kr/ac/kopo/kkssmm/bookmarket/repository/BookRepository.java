package kr.ac.kopo.kkssmm.bookmarket.repository;

import java.util.List;
import kr.ac.kopo.kkssmm.bookmarket.domain.Book;

public interface BookRepository {
    List<Book> getAllBookList();
}
