package com.clevercattv.top.book.repository;

import com.clevercattv.top.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByUserId(Long userId);

    Optional<Book> findByIdAndUserId(Long id, Long userId);

    List<Book> findAllByIdAndUserId(Long id, Long userId);

    @Modifying
    Long deleteByIdAndUserId(Long id, Long userId);

    @Modifying
    List<Long> deleteAllByIdInAndUserId(List<Long> ids, Long userId);

}
