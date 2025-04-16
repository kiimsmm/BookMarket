package kr.ac.kopo.kkssmm.bookmarket.repository;

import kr.ac.kopo.kkssmm.bookmarket.domain.Book;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository {
    private List<Book> listOfBooks = new ArrayList<Book>();

    public BookRepositoryImpl() {
        Book book1 = new Book();
        book1.setBookID("isbn0001");
        book1.setName("Complete Conquest of Spring Boot");
        book1.setUnitPrice(BigDecimal.valueOf(35000));
        book1.setAuthor("Mi Young Song");
        book1.setDescription("스프링 부트는 스프링을 기반으로 쉽고 빠르게 웹 애플리케이션을 개발할 수 있는 도구이다. " +
                "이 책에서는 스프링 부트의 기본 개념을 쉽게 이해하고 다양한 실습 예제로 빠르게 익힐 수 있다. " +
                "그리고 단계별 실습을 따라 하다 보면 도서 쇼핑몰 구축 프로젝트를 완성할 수 있다. "
                );
        book1.setPublisher("GilBut Campus");
        book1.setCategory("IT교재");
        book1.setUnitsInStock(1000);
        book1.setReleaseDate("2024/12/31");
        book1.setCondition("신규도서");

        Book book2 = new Book();
        book2.setBookID("isbn0002");
        book2.setName("Android Programming Using Android Studio");
        book2.setUnitPrice(BigDecimal.valueOf(34000));
        book2.setAuthor("Jea Nam Woo, Gil Sik Park");
        book2.setDescription("이 책은 대학교나 IT 전문학원의 안드로이드 프로그래밍 과목 수강생을 대상으로 한다. " +
                "기본적인 프로그래밍을 접해본 독자라면 Java 기초부터 시작하여 안드로이드 앱 개발까지 한번에 학습할 수 있도록 구성되어 있다. " +
                "특별히 이번 9판에서는 버전업을 적용하여 JDK 17, Android 14.0(U), Android Studio에서 실습할 수 있다. "
                );
        book2.setPublisher("HanBit Academy");
        book2.setCategory("IT교재");
        book2.setUnitsInStock(1000);
        book2.setReleaseDate("2024/01/19");
        book2.setCondition("신규도서");

        Book book3 = new Book();
        book3.setBookID("isbn0003");
        book3.setName("Machine Learning + Deep Learning by Self");
        book3.setUnitPrice(BigDecimal.valueOf(25000));
        book3.setAuthor("Hea Sun Park");
        book3.setDescription("이 책은 수식과 이론으로 중무장한 머신러닝, 딥러닝 책에 지친 ‘독학하는 입문자’가 ‘꼭 필요한 내용을 제대로’ 학습할 수 있도록 구성했다. " +
                "구글 머신러닝 전문가(Google ML expert)로 활동하고 있는 저자는 여러 차례의 입문자들과 함께한 머신러닝&딥러닝 스터디와 번역·집필 경험을 통해 ‘무엇을’ ‘어떻게’ 학습해야 할지 모르는 입문자의 막연함을 이해하고, 과외 선생님이 알려주듯 친절하게 핵심적인 내용을 콕콕 집어준다. "
                );
        book3.setPublisher("HanBit Media");
        book3.setCategory("IT전문서");
        book3.setUnitsInStock(1000);
        book3.setReleaseDate("2020/12/21");
        book3.setCondition("신규도서");

        listOfBooks.add(book1);
        listOfBooks.add(book2);
        listOfBooks.add(book3);

    }

    @Override
    public List<Book> getAllBookList() {
        return listOfBooks;
    }

    @Override
    public Book getBookById(String bookId) {
        Book bookInfo = null;
        for (Book book : listOfBooks) {
            if (book != null && book.getBookID() != null && book.getBookID().equals(bookId)) {
                bookInfo = book;
                break;
            }
        }
        if (bookInfo == null) {
            throw new IllegalArgumentException("도서번호가 " + bookId + "인 해당 도서를 찾을 수 없습니다");
        }
        return bookInfo;
    }

    @Override
    public List<Book> getBookByCategory(String category) {
        List<Book> booksofCategory = new ArrayList<>();
        for (Book book : listOfBooks) {
            if (book.getCategory()!=null && book.getCategory().equals(category)) {
                booksofCategory.add(book);
            }
        }
        return booksofCategory;
    }
}
