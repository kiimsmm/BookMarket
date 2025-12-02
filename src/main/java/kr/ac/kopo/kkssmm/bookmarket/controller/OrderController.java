package kr.ac.kopo.kkssmm.bookmarket.controller;

import kr.ac.kopo.kkssmm.bookmarket.domain.*;
import kr.ac.kopo.kkssmm.bookmarket.service.BookService;
import kr.ac.kopo.kkssmm.bookmarket.service.CartService;
import kr.ac.kopo.kkssmm.bookmarket.service.OrderProService;
import kr.ac.kopo.kkssmm.bookmarket.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    Order order;
    List<Book> listofBooks;

    @Autowired
    private OrderProService orderProService;
    @Autowired
    private BookService bookService;

    //	@Autowired
    //   private BookService bookService;

    @GetMapping("/{cartId}")
    public String requestCartList(@PathVariable(value = "cartId") String cartId, Model model) {
        Cart cart = cartService.validateCart(cartId);
        order = new Order();
        listofBooks = new ArrayList<Book>();

        for (CartItem item : cart.getCartItems().values()) {
            OrderItem orderItem = new OrderItem();
            Book book = item.getBook();
            listofBooks.add(book);
            orderItem.setBookId(book.getBookId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setTotalPrice(item.getTotalPrice());

            order.getOrderItems().put(book.getBookId(), orderItem);
        }

        order.setCustomer(new Customer());
        order.setShipping(new Shipping());
        order.setGrandTotal(cart.getGrandTotal());

        return "redirect:/order/orderCustomerInfo";
    }

    @GetMapping("/orderCustomerInfo")
    public String requestCustomerInfoForm(Model model) {

        model.addAttribute("customer", order.getCustomer());
        return "orderCustomerInfo";
    }


    @PostMapping("/orderCustomerInfo")
    public String requestCustomerInfo(@ModelAttribute Customer customer, Model model) {

        order.setCustomer(customer);
        return "redirect:/order/orderShippingInfo";
    }


    @GetMapping("/orderShippingInfo")
    public String requestShippingInfoForm(Model model) {
        model.addAttribute("shipping", order.getShipping());
        return "orderShippingInfo";
    }


    @PostMapping("/orderShippingInfo")
    public String requestShippingInfo(@Valid @ModelAttribute Shipping shipping, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors())
            return "orderShippingInfo";

        order.setShipping(shipping);

        model.addAttribute("order", order);
        return "redirect:/order/orderConfirmation";
    }


    @GetMapping("/orderConfirmation")
    public String requestConfirmation(Model model) {

        model.addAttribute("bookList", listofBooks);
        model.addAttribute("order", order);

        return "orderConfirmation";
    }

    @PostMapping("/orderConfirmation")
    public String requestConfirmationFinished(Model model, HttpServletRequest request) {

        model.addAttribute("order", order);
        // 1. 주문 저장
        orderProService.save(order);
        // 2. 장바구니 삭제
        String sessionId = request.getSession().getId();
        Cart cart = cartService.read(sessionId);

        if (cart != null) {
            cart.getCartItems().clear();   // 내부 아이템 제거
            cartService.delete(sessionId); // CartRepository에서 완전히 삭제
        }

        return "redirect:/order/orderFinished";
    }


    @GetMapping("/orderFinished")
    public String requestFinished(HttpServletRequest request, Model model) {
        orderService.saveOrder(order);

        model.addAttribute("order", order);

        // 주문 관련 정보만 초기화
        order = null;
        listofBooks = null;

        return "orderFinished";
    }


    @GetMapping("/orderCancelled")
    public String requestCancelled(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "orderCancelled";
    }

    @GetMapping("/list")
    public String viewHomePage(Model model) {
        return viewPage(1, "orderId", "asc", model);
    }

    @GetMapping("/page")
    public String viewPage(@RequestParam("pageNum") int pageNum, @RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir, Model model) {
        Page<Order> page = orderProService.listAll(pageNum, sortField, sortDir);
        List<Order> listOrders = page.getContent();
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("orderList", listOrders);
        return "orderList";
    }

    // OrderController.java

// ... (생략)

    @GetMapping("/view/{id}")
    public ModelAndView viewOrder(@PathVariable(value = "id") Long id) {
        Order order = orderProService.get(id);

        // 1. 필요한 모든 Book 객체를 조회하여 Map으로 변환합니다.
        Map<String, Book> bookMap = order.getOrderItems().values().stream()
                .map(orderItem -> bookService.getBookById(orderItem.getBookId()))
                .collect(Collectors.toMap(Book::getBookId, Function.identity()));

        ModelAndView modelAndView = new ModelAndView("orderView");
        modelAndView.addObject("order", order);
        modelAndView.addObject("bookMap", bookMap); // Map으로 전달

        return modelAndView;
    }



    @GetMapping("/edit/{id}")
    public ModelAndView showEditOrder(@PathVariable(value = "id") Long id) {
        Order order = orderProService.get(id);
        List<Book> listOfBooks = new ArrayList<Book>();
        for (OrderItem orderItem : order.getOrderItems().values()) {
            String bookId = orderItem.getBookId();
            Book book = bookService.getBookById(bookId);
            listOfBooks.add(book);
        }

        ModelAndView modelAndView = new ModelAndView("orderEdit");
        modelAndView.addObject("order", order);
        modelAndView.addObject("bookList", listOfBooks);
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable(value = "id") Long id) {
        orderProService.delete(id);
        return "redirect:/order/list";
    }

    @GetMapping("/deleteAll")
    public String deleteAllOrder() {
        orderProService.deleteAll();
        return "redirect:/order/list";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Order order) {
        Order saveOrder = orderProService.get(order.getOrderId());
        saveOrder.setShipping(order.getShipping());
        orderProService.save(saveOrder);
        return "redirect:/order/list";
    }
}