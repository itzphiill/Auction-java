package com.gik2h9.project.controllers;

import com.gik2h9.project.models.Bid;
import com.gik2h9.project.models.Item;
import com.gik2h9.project.models.User;
import com.gik2h9.project.interfaces.BidsRepository;
import com.gik2h9.project.interfaces.ItemsRepository;
import com.gik2h9.project.interfaces.UsersRepository;

import com.gik2h9.project.services.BidderNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/bidder/")
public class BidderController {
    List<Item> items = new ArrayList<>();

    @Autowired
    private ItemsRepository itemsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    BidsRepository bidsRepository;

    @Autowired
    BidderNoticeService bidderNoticeService;

    @GetMapping("")
    public String index() {
        return "test";
    }

    @PostMapping("/laybid")
    public String placeBid(Model model, @RequestParam(name = "id") Integer id, @ModelAttribute Bid placedBid) {

        Bid newBid = new Bid();
        User loggedInUser = usersRepository.findByEmail(TestController.getLoggedInUser());
        model.addAttribute("loggedin",loggedInUser);
        Item currentItem = itemsRepository.findById(id).get();
        Item currentItemSave = itemsRepository.findById(id).get();
        newBid.setDate(new Date());
        newBid.setUser(loggedInUser);
        newBid.setPrice(placedBid.getPrice());

        Integer highestBid = 0;
        for (Bid tempBid : bidsRepository.findAllByItemOrderByPrice(currentItem)) {
            if (highestBid < tempBid.getPrice())
                highestBid = tempBid.getPrice();
        }
        if(!currentItem.auctionHasEnded()) {
            if (placedBid.getPrice() < currentItem.getStartingBid()) {
                model.addAttribute("message", "The bid can not be lower than the starting bid.");
                model.addAttribute("loggedin",loggedInUser);

                return "exceptions";
            }

            if (placedBid.getPrice() <= highestBid) {
                model.addAttribute("message", "The bid can not be lower than the current highest bid. Bid higher than the current highest.");
                model.addAttribute("loggedin",loggedInUser);
                return "exceptions";
            }
            currentItemSave.addObserver(bidderNoticeService);
            currentItem.addBid(newBid);
            itemsRepository.save(currentItem);
        }
        else {
            model.addAttribute("message", "This auction is over.");
            return "exceptions";
        }

        model.addAttribute("item", itemsRepository.findById(id).get());
        model.addAttribute("top3bids", bidsRepository.findTop3ByItemOrderByPriceDesc(currentItem));
        return "redirect:/auctionitem?id=" + currentItem.getId();
    }
}
