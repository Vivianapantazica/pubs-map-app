package com.bere_SRL.PubMapApp.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator= "users_seq_gen")
    @SequenceGenerator(name= "users_seq_gen", sequenceName = "users_seq", allocationSize = 1)
    private Long id;

    String email;

    String password;

    boolean is_admin;

    @ManyToMany
    @JoinTable(
            name = "user_pubs",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "pub_id"))
    List<Pub> pubs;

}
