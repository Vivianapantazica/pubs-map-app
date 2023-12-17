package com.bere_SRL.PubMapApp.repositories;

import com.bere_SRL.PubMapApp.Entities.Pub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPubRepo extends JpaRepository<Pub, Long> {
}
