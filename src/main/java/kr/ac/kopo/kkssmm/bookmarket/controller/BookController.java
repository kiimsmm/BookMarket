package kr.ac.kopo.kkssmm.bookmarket.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kr.ac.kopo.kkssmm.bookmarket.domain.Book;
import kr.ac.kopo.kkssmm.bookmarket.exception.bookIdException;
import kr.ac.kopo.kkssmm.bookmarket.exception.categoryException;
import kr.ac.kopo.kkssmm.bookmarket.service.BookService;
import kr.ac.kopo.kkssmm.bookmarket.validator.BookValidator;
import kr.ac.kopo.kkssmm.bookmarket.validator.UnitsInStockValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping(value = "/books")
public class BookController {
    @Autowired
    private BookService bookService;

//    @Autowired
//    private UnitsInStockValidator unitsInStockValidator;
    @Autowired
    private BookValidator booksValidator;

    @Value("${file.uploadDir}")
    private String fileDir;

    @GetMapping
    public String requestBookList(Model model) {
        List<Book> bookList = bookService.getAllBookList();
        model.addAttribute("bookList", bookList);
        return "books";
    }

    @GetMapping("/all")
    public ModelAndView requestAllBookList(Model model) {
        ModelAndView modelV = new ModelAndView();
        modelV.setViewName("books");
        List<Book> bookList = bookService.getAllBookList();
        modelV.addObject("bookList", bookList);
        return modelV;
    }

    @GetMapping("/book")
    public String requestBookById(@RequestParam("id") String bookId, Model model) {
        Book book = bookService.getBookById(bookId);
        model.addAttribute("book", book);
        return "book";
    }

    @GetMapping("/{category}")
    public String requestBookListByCategory(@PathVariable("category")String category, Model model) {
        List<Book> bookByCategory = bookService.getBooksByCategory(category);
        if (bookByCategory != null || bookByCategory.isEmpty()) {
            throw new categoryException();
        }
        model.addAttribute("bookList", bookByCategory);
        return "books";
    }

    @GetMapping("/filter/{bookFilter}")
    public String requestBookListByFilter(@MatrixVariable(pathVar = "bookFilter")Map<String, List<String>> bookFilter, Model model) {
        Set<Book> bookByFilter = bookService.getBookListByFilter(bookFilter);
        model.addAttribute("bookList", bookByFilter);
        return "books";
    }

    @GetMapping("/add")
    public String requestAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "addbook";
    }

    @PostMapping("/add")
    public String requestSubmitNewBook(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addbook";
        }
        MultipartFile multipartFile = book.getBookImage();
        String saveName = multipartFile.getOriginalFilename();
        File saveFile = new File(fileDir + saveName);
        MultipartFile bookImage = null;
        if (bookImage != null && !bookImage.isEmpty()) {
            try {
                bookImage.transferTo(saveFile);
            } catch (IOException e){
                throw new RuntimeException("도서 이미지가 업로드가 되지 않았습니다.");
            }
        }
        book.setFileName(saveName);

        bookService.setNewBook(book);
        return "redirect:/books";
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("addTitle", "신규 도서 등록");
    }

    @GetMapping("/download")
    public void downloadBookImage(@RequestParam("fileName") String paramKey, HttpServletResponse response) throws IOException {
        File imageFile = new File(fileDir + paramKey);
        response.setContentType("application/download");
        response.setHeader("Content-Disposition","attachment; filename=\"" + paramKey + "\"");
        response.setContentLength((int) imageFile.length());
        OutputStream os = response.getOutputStream();
        FileInputStream fis = new FileInputStream(imageFile);
        FileCopyUtils.copy(fis, os);
        fis.close();
        os.close();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
//        binder.setValidator(UnitsInStockValidator);
        binder.setValidator(booksValidator);
        binder.setAllowedFields("bookID", "name", "author", "unitPrice", "description",
                    "publisher", "category", "unitsInStock", "releaseDate", "condition", "bookImage");
    }

    @ExceptionHandler(value = {bookIdException.class})
    public ModelAndView handleException(HttpServletRequest request, bookIdException e) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("invalidBookId", e.getBookId());
        mav.addObject("exception", e.toString());
        mav.addObject("url", request.getRequestURL() + "?" + request.getQueryString());
        mav.setViewName("errorBook");
        return mav;
    }
}
