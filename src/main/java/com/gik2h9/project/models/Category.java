package com.gik2h9.project.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    //Creates a OneToMany relationship with Item class
    @OneToMany(
            mappedBy ="category",
            cascade =CascadeType.ALL,
            orphanRemoval = true
    )
    public List<Item> items =new ArrayList<>();
    public void addItem(Item item){
        items.add(item);
        item.setCategory(this);
    }

    public Category(){

    }
    public Category(String title,String description){
        this.title=title;
        this.description=description;
    }



    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
