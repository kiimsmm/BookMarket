package kr.ac.kopo.kkssmm.bookmarket.controller;

import kr.ac.kopo.kkssmm.bookmarket.domain.Book;
import kr.ac.kopo.kkssmm.bookmarket.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @RequestMapping(value="/books", method= RequestMethod.GET)
    public String requestBookList(Model model) {
     List<Book> bookList = bookRepository.getAllBookList();
     model.addAttribute("bookList", bookList);
     return "books";
    }
}
