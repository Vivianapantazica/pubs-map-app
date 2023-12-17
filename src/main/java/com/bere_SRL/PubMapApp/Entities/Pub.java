package com.bere_SRL.PubMapApp.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
public class Pub {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator= "users_seq_gen")
    @SequenceGenerator(name= "users_seq_gen", sequenceName = "users_seq", allocationSize = 1)
    private Long id;

    String name;

    String address;

    String description;

    boolean deleted;

    @ManyToMany(mappedBy = "pubs")
    List<Users> users;
}
