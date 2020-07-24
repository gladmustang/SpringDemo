package com.pwc.faast.notesservice.repository;


import com.pwc.faast.notesservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    //In addition to predefined query methods, query derivation for both count and delete queries is available
    long countByName(String name) ;

    @Query("select u from User u where u.email like %?1% and u.name = ?2")
    User findByQuery(String email, String name);

    @Query("select u from User u where u.email like %:email% and u.name = :name")
    User findByNameParam(String email, String name);

}

