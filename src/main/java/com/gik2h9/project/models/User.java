package com.gik2h9.project.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private String role;
    @Column(columnDefinition = "TINYINT")
    private Integer enabled;
    private String description;

    //Creates a OneToMany relationship with Item class
    @OneToMany(
            mappedBy ="user",
            cascade =CascadeType.ALL,
            orphanRemoval = true
    )
    public List<Item> items = new ArrayList<>();

    public void addItem(Item item){
        item.setUser(this);
        items.add(item);
    }
    //Creates a OneToMany relationship with Bid class
    @OneToMany(
            mappedBy ="user",
            cascade =CascadeType.ALL,
            orphanRemoval = true
    )
    public List<Bid> bids =new ArrayList<>();

    public void addBid(Bid bid){
        bids.add(bid);
        bid.setUser(this);
    }
    public User(){

    }
    public User(String name,String email,String password,String description,String role,int enable){
        this.name = name;
        this.email = email;
        this.password = password;
        this.description = description;
        this.role=role;
        this.enabled=enable;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", enabled=" + enabled +
                ", description='" + description + '\'' +
                '}';
    }
}

