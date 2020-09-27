package com.clevercattv.top.book.repository;

import com.clevercattv.top.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Long, Book> {

}
