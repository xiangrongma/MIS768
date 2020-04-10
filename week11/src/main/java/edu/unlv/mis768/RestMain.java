package edu.unlv.mis768;

import java.util.List;
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
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@RestController
public class RestMain {
    private static final Logger log = LoggerFactory.getLogger(RestMain.class);
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("**").allowedOrigins("*s");
//            }
//        };
//    }
    public static void main(String[] args) {
        SpringApplication.run(RestMain.class, args);
    }

//    @GetMapping("/v1/books/{bookId}")
        @RequestMapping(value = "/v1/books/{bookId}", method = RequestMethod.GET, produces = "application/json")
        public String getOneBook(@PathVariable Long bookId) {
        BookApi api = new BookApiImp();
        final List<Book> books = api.getBooks();
        final Book book = api.getBook(bookId);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(book);
        } catch (JsonProcessingException e) {
            return  String.format(" {\"error\" : \"%s\" }", e.getMessage());
        }

    }
    // limit = 100
    // offset = 1... n
//    @GetMapping("/v1/books")
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
            return  String.format(" {\"error\" : \"%s\" }", e.getMessage());
        }
    }

//    @GetMapping("/v2/books/{bookId}")
@RequestMapping(value = "/v2/books/{bookId}", method = RequestMethod.GET, produces = "application/json")

public String getOneBookV2(@PathVariable Long bookId) {
        BookApi api = new BookApiImp();
        final List<Book> books = api.getBooks();
        final Book book = api.getBook(bookId);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(book);
        } catch (JsonProcessingException e) {
            return  String.format(" {\"error\" : \"%s\" }", e.getMessage());
        }

    }
    // limit = 100
    // offset = 1... n
//    @GetMapping("/v2/books")
    @RequestMapping(value = "/v2/books", method = RequestMethod.GET, produces = "application/json")

    public String getBooksV2(@RequestParam(name = "limit", defaultValue = "10") Integer limit,
                           @RequestParam(name = "offset", defaultValue = "0") Integer offset) {
        BookApi api = new BookApiImp();
        final List<Book> allbooks = api.getBooks();
        final List<Book> books = allbooks.stream().skip(offset * limit).limit(limit).collect(Collectors.toList());
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(books);
        } catch (JsonProcessingException e) {
            return  String.format(" {\"error\" : \"%s\" }", e.getMessage());
        }
    }

    @Bean
    public CommandLineRunner initBooks(BookRepository repository) {
        return (args) -> {
            // save a few customers
            BookApi api = new BookApiImp();
            final List<Book> books = api.getBooks();
            repository.saveAll(books);
            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            final Stream<Book> stream = StreamSupport.stream(repository.findAll().spliterator(), false);
            stream.limit(10)
                    .map( book -> {log.info(book.title); return null;})
                    .collect(Collectors.toList());

//            for (Book book : repository.findAll()) {
//                log.info(book.toString());
//            }
            log.info("");

            // fetch an individual customer by ID
            Book book = repository.findById(133L);

            if (book != null) {
                log.info("Customer found with findById(133):");
                log.info("--------------------------------");
                log.info(book.toString());
                log.info("");
            }


            // fetch customers by last name
            log.info("Customer found with findByLastName('another book\"'):");
            log.info("--------------------------------------------");
            repository.findByTitle("another book").forEach(bauer -> {
                log.info(bauer.toString());
            });
            // for (Customer bauer : repository.findByLastName("Bauer")) {
            //  log.info(bauer.toString());
            // }
            log.info("");
        };
    }
}