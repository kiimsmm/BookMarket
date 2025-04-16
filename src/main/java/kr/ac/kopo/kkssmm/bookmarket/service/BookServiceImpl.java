package kr.ac.kopo.kkssmm.bookmarket.service;

import kr.ac.kopo.kkssmm.bookmarket.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookService bookService;

    @Override
    public List<Book> getAllBookList() {
        return bookService.getAllBookList();
    }

    @Override
    public Book getBookById(String BookId) {
        Book book = bookService.getBookById(BookId);
        return book;
    }

    @Override
    public List<Book> getBookByCategory(String category) {
        List<Book> booksByCategory = bookService.getBookByCategory(category);
        return booksByCategory;
    }

    @GetMapping("/{category}")
    public String requestBookByCategory(@PathVariable("category") String category, Model model) {
        List<Book> booksByCategory = bookService.getBookByCategory(category);
        model.addAttribute("books", booksByCategory);
        return "books";
    }
}