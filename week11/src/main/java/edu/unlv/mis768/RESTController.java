package edu.unlv.mis768;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.unlv.mis768.Repository.Api.BookApi;
import edu.unlv.mis768.Repository.Api.BookApiImp;
import edu.unlv.mis768.Repository.Book;
import edu.unlv.mis768.Repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class RESTController {
    private static final Logger log = LoggerFactory.getLogger(RESTController.class);

    @Autowired  BookRepository repository;

    @RequestMapping(value = "/v1/books/{bookId}", method = RequestMethod.GET, produces = "application/json")
    public String getOneBook(@PathVariable Long bookId) {
        BookApi api = new BookApiImp();
        final List<Book> books = api.getBooks();
        final Book book = api.getBook(bookId);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(book);
        } catch (JsonProcessingException e) {
            return String.format(" {\"error\" : \"%s\" }", e.getMessage());
        }

    }

    @RequestMapping(value = "/v1/books", method = RequestMethod.GET, produces = "application/json")

    public String getBooks(@RequestParam(name = "limit", defaultValue = "10") Integer limit,
                           @RequestParam(name = "offset", defaultValue = "0") Integer offset) {
        BookApi api = new BookApiImp();
        final List<Book> allbooks = api.getBooks();
        final List<Book> books = allbooks.stream().skip(offset * limit).limit(limit).collect(Collectors.toList());
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(books);
        } catch (JsonProcessingException e) {
            return String.format(" {\"error\" : \"%s\" }", e.getMessage());
        }
    }


    @RequestMapping(value = "/v2/books/{bookId}", method = RequestMethod.GET, produces = "application/json")

    public String getOneBookV2(@PathVariable Long bookId) {

        final Optional<Book> book = repository.findById(bookId);
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (book.isPresent()) {
                return mapper.writeValueAsString(book.get());

            } else {
                return "{}";
            }
        } catch (JsonProcessingException e) {
            return String.format(" {\"error\" : \"%s\" }", e.getMessage());
        }

    }

    @RequestMapping(value = "/v2/books", method = RequestMethod.GET, produces = "application/json")

    public String getBooksV2(@RequestParam(name = "limit", defaultValue = "10") Integer limit,
                             @RequestParam(name = "offset", defaultValue = "0") Integer offset) {
        final Stream<Book> bookStream = StreamSupport.stream(repository.findAll().spliterator(), false);

        final List<Book> books = bookStream
                .skip(offset * limit)
                .limit(limit)
                .collect(Collectors.toList());
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(books);
        } catch (JsonProcessingException e) {
            return String.format(" {\"error\" : \"%s\" }", e.getMessage());
        }
    }

}