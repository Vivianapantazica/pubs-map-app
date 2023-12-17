package com.bere_SRL.PubMapApp.repositories;

import com.bere_SRL.PubMapApp.Entities.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class IUsersRepoTest {

    @Autowired
    IUserRepo dan;

    @Test
    public void test1(){
        dan.save(new Users() );
        List<Users> dani = dan.findAll();
        System.out.println(dani);
    }
}