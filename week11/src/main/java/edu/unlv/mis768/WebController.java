package edu.unlv.mis768;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import edu.unlv.mis768.Repository.Book;
import edu.unlv.mis768.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {


    @Autowired
    BookRepository repository;


    @GetMapping("/book/{bookId}")
    String getBook(@PathVariable Long bookId, Model model) {
        final Optional<Book> book = repository.findById(bookId);
//        model.addAttribute("title", book.toString());

        if(book.isPresent()) {
            model.addAttribute("book", book.get());
        } else {
            model.addAttribute("book", null);
        }

        return "bookpage";
    }
    @GetMapping("/books/")
    String getBooks( @RequestParam(name = "limit", defaultValue = "10") Integer limit,
                     @RequestParam(name = "offset", defaultValue = "0") Integer offset,
                     Model model) {
        final Stream<Book> bookStream = StreamSupport.stream(repository.findAll().spliterator(), false);
        model.addAttribute("title", "Book List");

        final List<Book> books = bookStream
                .skip(offset * limit)
                .limit(limit)
                .collect(Collectors.toList());
        if(books.size() > 1) {
            model.addAttribute("BookList", books);
        } else {
            model.addAttribute("BookList", null);
        }

        return "books";
    }
}
