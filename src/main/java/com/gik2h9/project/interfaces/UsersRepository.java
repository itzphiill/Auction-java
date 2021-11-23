package com.gik2h9.project.interfaces;

import com.gik2h9.project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface UsersRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
////Injects behaviors before, after, or around method calls into the object.
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.name = ?1, u.email = ?2, u.description = ?3 WHERE u.id = ?4")
    void updateUser(String name,String email,String description,int id);
}
