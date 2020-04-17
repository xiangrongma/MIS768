package edu.unlv.mis768;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import edu.unlv.mis768.Repository.Api.BookApi;
import edu.unlv.mis768.Repository.Api.BookApiImp;
import edu.unlv.mis768.Repository.Book;
import edu.unlv.mis768.Repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap {
    private static final Logger log = LoggerFactory.getLogger(Bootstrap.class);

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
