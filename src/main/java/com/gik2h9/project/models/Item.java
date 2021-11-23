package com.gik2h9.project.models;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Comparator.comparing;

@Entity
public class Item extends Observable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    private Integer startingBid;
    private Date endTime;
    private Integer enabled;
    private String picture;

    ////Creates a OneToMany relationship with bid class
    @OneToMany(
            mappedBy ="item",
            cascade =CascadeType.ALL,
            orphanRemoval = true
    )
    public List<Bid> bids = new ArrayList<>();

    public void addBid(Bid bid){
        bids.add(bid);
        bid.setItem(this);
        setChanged();
        notifyObservers(bid);

    }

    //Relation to Category
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    //Relation to User
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Item(){
    }

    public Item(String name,String description,int startingBid, Date endtime,int enabled,String picture){
        this.name=name;
        this.description=description;
        this.startingBid=startingBid;

        Calendar cNow = Calendar.getInstance();
        cNow.add(Calendar.MINUTE, 1);
        this.endTime=cNow.getTime();
        this.enabled=enabled;
        this.picture=picture;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStartingBid(Integer startingBid) {
        this.startingBid = startingBid;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStartingBid() {
        return startingBid;
    }

    public void setStartingBid(int startingBid) {
        this.startingBid = startingBid;
    }

    public Integer getId() {
        return id;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Boolean auctionHasEnded () {
        Date dateNow;

        Calendar c = Calendar.getInstance();
        dateNow = c.getTime();

        if (dateNow.after(this.endTime)) {
            return true;
        }
        else {
            return false;
        }
    }

    public String getFormattedDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = simpleDateFormat.format(this.endTime);
        return formattedDate;
    }

    public Bid getHighestBid() {
        Bid highestBid = null;
        if (this.bids.isEmpty()) {
            return highestBid;
        }
        else {
            highestBid = this.bids.stream().max(comparing(Bid::getPrice)).get();
        }

        return highestBid;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startingBid=" + startingBid +
                ", endTime=" + endTime +
                ", enabled=" + enabled +
                ", picture='" + picture + '\'' +
                ", bids=" + bids +
                ", category=" + category +
                ", user=" + user +
                '}';
    }
}

