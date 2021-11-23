package com.gik2h9.project.controllers;

import com.gik2h9.project.models.Category;
import com.gik2h9.project.models.Item;
import com.gik2h9.project.models.User;
import com.gik2h9.project.interfaces.BidsRepository;
import com.gik2h9.project.interfaces.CategoriesRepository;
import com.gik2h9.project.interfaces.ItemsRepository;
import com.gik2h9.project.interfaces.UsersRepository;
import com.gik2h9.project.services.EmailService;
import com.gik2h9.project.AuctionTimer.AuctionTimer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller

@RequestMapping("/seller")
public class SellerController {
    @Autowired
    ItemsRepository itemsRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    CategoriesRepository categoriesRepository;
    @Autowired
    AuctionTimer auctionTimer;
    @Autowired
    BidsRepository bidsRepository;
    @Autowired
    EmailService emailService;

    @GetMapping("/add")
    public String home(Model model){

        User loggedInUser = usersRepository.findByEmail(TestController.getLoggedInUser());
        model.addAttribute("items",itemsRepository.findAllByUser(loggedInUser));
        model.addAttribute("category",categoriesRepository.findAll());
        return "seller";
    }


    @GetMapping("/addauktion")
    public String addAuction(@RequestParam(defaultValue = "-1") String name,
                             @RequestParam(defaultValue = "-1") String description,
                             @RequestParam(defaultValue = "-1") String endtime,
                             @RequestParam(defaultValue = "-1") Integer startingprice,
                             @RequestParam(defaultValue = "-1") String picture,
                             @RequestParam(defaultValue = "-1") Integer category){
        int enabled = 1;

        Item item = new Item(name,description,startingprice,new Date(),enabled,picture);
        item.setCategory(categoriesRepository.findById(category).get());
        List<Category>categories = categoriesRepository.findAll();
        User loggedInUser = usersRepository.findByEmail(TestController.getLoggedInUser());
        loggedInUser.addItem(item);

        item = itemsRepository.save(item);

        //noterar att tiden är slut och mailar till vinnaren
        auctionTimer.startTimer(item);
        //mailar användaren om att deras produkt lagts till
        emailService.sendEmailNotification(loggedInUser.getEmail(),"Item",item.getName() + "added. Bidding starts at: " + item.getStartingBid());

        return "redirect:/seller/add";
    }

    @GetMapping("")
    public String index() {
        return "test";
    }
    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable Integer id) {
        itemsRepository.deleteById(id);
        return "redirect:/seller/add";
    }
    @GetMapping("/updateItem")
    public String updateItem(@RequestParam(defaultValue = "-1") String name,
                             @RequestParam(defaultValue = "-1") String description,
                             @RequestParam(defaultValue = "-1") String startingBid,
                             @RequestParam(defaultValue = "-1") String picture,
                             @RequestParam(defaultValue = "-1") String id,
                             @RequestParam(defaultValue = "-1") String category) {
        Category category1 = categoriesRepository.findByTitle(category);
        itemsRepository.updateItem(name,description,Integer.parseInt(startingBid),picture,category1,Integer.parseInt(id));

        return "redirect:/seller/add";
    }
}