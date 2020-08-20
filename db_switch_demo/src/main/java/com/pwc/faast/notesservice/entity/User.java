package com.pwc.faast.notesservice.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Integer id;

    private String name;

    private String email;
}

