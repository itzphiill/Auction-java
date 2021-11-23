package com.gik2h9.project.models;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Bid() {
    }

    public Bid(Integer price, Date date, User user, Item item) {
        this.price = price;
        this.date = date;
        this.user = user;
        this.item = item;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    private Integer price;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getFormattedDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = simpleDateFormat.format(this.date);
        return formattedDate;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "id=" + id +
                ", price=" + price +
                ", date=" + date +
                ", user=" + user +
                '}';
    }
}
