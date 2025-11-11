package kr.ac.kopo.kkssmm.bookmarket.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class commonException{
    @ExceptionHandler(categoryException.class)
    private ModelAndView handleException(categoryException e, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("errMessage", e.getErrMessage());
        mav.addObject("category", e.getCategory());
        mav.addObject("exception", e.toString());
        mav.addObject("url", request.getRequestURL());
        mav.setViewName("errorCommon");
        return mav;
    }
}