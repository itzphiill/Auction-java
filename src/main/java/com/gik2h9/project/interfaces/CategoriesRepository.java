package com.gik2h9.project.interfaces;

import com.gik2h9.project.models.Category;
import com.gik2h9.project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Integer> {

    public Category findByTitle(String title);

}