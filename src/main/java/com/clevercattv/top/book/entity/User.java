package com.clevercattv.top.book.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 4531526626272541803L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    private String username;
    private char[] password;

    @OneToMany(mappedBy = "user")
    private List<Book> books;

}
