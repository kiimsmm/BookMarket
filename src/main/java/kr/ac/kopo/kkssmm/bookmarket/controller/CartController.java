package kr.ac.kopo.kkssmm.bookmarket.controller;


import jakarta.servlet.http.HttpServletRequest;
import kr.ac.kopo.kkssmm.bookmarket.domain.Book;
import kr.ac.kopo.kkssmm.bookmarket.domain.Cart;
import kr.ac.kopo.kkssmm.bookmarket.domain.cartItem;
import kr.ac.kopo.kkssmm.bookmarket.exception.bookIdException;
import kr.ac.kopo.kkssmm.bookmarket.service.BookService;
import kr.ac.kopo.kkssmm.bookmarket.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private BookService bookService;

    @GetMapping
    public String requestCartId(HttpServletRequest request, Model model) {
        System.out.println("Call requestCartId()");
        String sessionId = request.getSession().getId();
        return "redirect:/cart/" + sessionId;
    }

    @PostMapping
    public @ResponseBody Cart create(@RequestBody Cart cart) {
        System.out.println("Call create()");
        return cartService.create(cart);
    }

    @GetMapping("/{cartId}")
    public String requestCartList(@PathVariable String cartId, Model model) {
        System.out.println("Call requestCartList()");
        Cart cart = cartService.read(cartId);
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PutMapping("/{cartId}")
    public @ResponseBody Cart read(@PathVariable(value = "cartId") String cartId) {
        System.out.println("Call requestCartList()");
        return cartService.read(cartId);
    }

    @PutMapping("/book/{bookID}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void addCartByNewItem(@PathVariable("bookID") String bookId, HttpServletRequest request) {
        String sessionId = request.getSession(true).getId();

        Cart cart = cartService.read(sessionId); // bookId → sessionId로 수정

        if (cart == null) {
            try {
                cart = cartService.create(new Cart(sessionId));
            } catch (IllegalArgumentException e) {
                // 이미 존재하면 무시하거나 로그만 출력 (필요에 따라 로직 선택)
                System.out.println("Cart already exists: " + sessionId);
                cart = cartService.read(sessionId); // 다시 읽기 시도
            }
        }

        Book book = bookService.getBookById(bookId);
        if (book == null) {
            throw new IllegalArgumentException(new bookIdException(bookId));
        }

        cart.addCartItem(new cartItem(book));
        cartService.update(sessionId, cart);
    }

    @DeleteMapping("/book/{bookID}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeCartByNewItem(@PathVariable("bookID") String bookId, HttpServletRequest request) {
        String sessionId = request.getSession(true).getId();
        Cart cart = cartService.read(sessionId);

        if (cart == null) {
            cart = cartService.create(new Cart(sessionId));
        }

        Book book = bookService.getBookById(bookId);
        if (book == null) {
            throw new IllegalArgumentException(new bookIdException(bookId));
        }
        cart.removeCartItem(new cartItem(book));
        cartService.update(sessionId, cart);
    }

    @DeleteMapping("/{cartId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeCart(@PathVariable(value = "cartId") String cartId) {
        cartService.delete(cartId);
    }
}
