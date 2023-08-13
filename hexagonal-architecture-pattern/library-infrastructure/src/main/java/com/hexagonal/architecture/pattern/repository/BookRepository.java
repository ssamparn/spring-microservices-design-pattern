package com.hexagonal.architecture.pattern.repository;

import com.hexagonal.architecture.pattern.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
