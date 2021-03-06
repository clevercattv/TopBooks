package com.clevercattv.top.book.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {

    private static final long serialVersionUID = -5331111904796071483L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private ClientType type;

    private String title;
    private String publisher;
    private String description;
    private String note; // user own notes (like : buy this book | good one and ect.)
    private String language;
    private String image;
    private Integer pages;
    private LocalDate year;

    private LocalDateTime creationTime;
    private LocalDateTime lastModifiedTime;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<Author> authors;

}
