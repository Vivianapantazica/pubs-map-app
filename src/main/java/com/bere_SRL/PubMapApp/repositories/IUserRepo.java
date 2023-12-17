package com.bere_SRL.PubMapApp.repositories;

import com.bere_SRL.PubMapApp.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepo extends JpaRepository<Users, Long> {

}

