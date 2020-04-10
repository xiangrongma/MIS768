package edu.unlv.mis768.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findByTitle(String title);

    Book findById(long Id);
}