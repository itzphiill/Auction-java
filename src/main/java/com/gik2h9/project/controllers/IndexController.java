package com.gik2h9.project.controllers;

import com.gik2h9.project.models.Category;
import com.gik2h9.project.models.Item;
import com.gik2h9.project.models.User;
import com.gik2h9.project.interfaces.BidsRepository;
import com.gik2h9.project.interfaces.CategoriesRepository;
import com.gik2h9.project.interfaces.ItemsRepository;
import com.gik2h9.project.interfaces.UsersRepository;
import com.gik2h9.project.AuctionTimer.AuctionTimer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller

public class IndexController {
    @Autowired
    ItemsRepository itemsRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    CategoriesRepository categoriesRepository;
    @Autowired
    private BidsRepository bidsRepository;
    @Autowired
    AuctionTimer auctionTimer;

    @EventListener(ApplicationReadyEvent.class)
    public void Index() {

        //Skapa user
        User u1 = new User("KarwanGara", "karwangara@gmail.com", "$2y$12$wSg7LFxucjJOEqnxqb1SN.lUQcvsxeYccpnk07Gxe/pyAMjDemPxO", "Admin", "ROLE_ADMIN",1);
        User u2 = new User("Kelli", "p.kellllly@gmail.com", "$2y$12$wSg7LFxucjJOEqnxqb1SN.lUQcvsxeYccpnk07Gxe/pyAMjDemPxO", "Seller", "ROLE_USER",1);
        User u3 = new User("Filis", "fstanic2@gmail.com", "$2y$12$wSg7LFxucjJOEqnxqb1SN.lUQcvsxeYccpnk07Gxe/pyAMjDemPxO", "Bidder", "ROLE_USER",1);
        usersRepository.save(u1);
        usersRepository.save(u2);
        usersRepository.save(u3);

        //Skapa produkt
        Item a1 = new Item("Mjölner","Thors hammer, can harness the power of lightning.",50,new Date(),1,"https://cdn.webshopapp.com/shops/305440/files/334089699/600x600x2/thor-hammer-of-thor-mjolnir-resin.jpg");
        Item a2 = new Item("Byggare Bobs Hammer","A very reliable hammer indeed.",500,new Date(),1,"https://www.byggmax.se/media/catalog/product/cache/ba11c7fad252997e23ce11024add322b/0/_/0_28002_7.jpg");
        Item a3 = new Item("Barad Dur","All shall tremble before the might of Sauron.",200,new Date(),1,"https://images-na.ssl-images-amazon.com/images/I/51DPYYOmWwL._AC_SL1000_.jpg");
        Item a4 = new Item("Usopp Hammer", "Belonged to a mighty liar.", 10, new Date(), 1, "https://i.redd.it/3sn64gphauy41.png");
        Item a5 = new Item("Usopp Hammer", "Belonged to a mighty liar.", 10, new Date(), 1, "https://i.redd.it/3sn64gphauy41.png");
        Item a6 = new Item("Usopp Hammer", "Belonged to a mighty liar.", 10, new Date(), 1, "https://i.redd.it/3sn64gphauy41.png");
        Item a7 = new Item("Usopp Hammer", "Belonged to a mighty liar.", 10, new Date(), 1, "https://i.redd.it/3sn64gphauy41.png");
        Item a8 = new Item("Usopp Hammer", "Belonged to a mighty liar.", 10, new Date(), 1, "https://i.redd.it/3sn64gphauy41.png");

        //Skapa kategori
        Category c1 = new Category("Mallet", "");
        Category c2 = new Category("Sledge Hammer", "");
        Category c3 = new Category("Mace", "");

        categoriesRepository.save(c1);
        categoriesRepository.save(c2);
        categoriesRepository.save(c3);

        a1.setCategory(c1);
        a2.setCategory(c1);
        a3.setCategory(c3);
        a4.setCategory(c2);
        a5.setCategory(c2);
        a6.setCategory(c2);
        a7.setCategory(c2);
        a8.setCategory(c2);



        //ändrar datum till utgått
        a4.setEndTime(Calendar.getInstance().getTime());
        Calendar cNow = Calendar.getInstance();

        //ökar datum med 1
        cNow.add(Calendar.DATE, 1);
        a1.setEndTime(cNow.getTime());
        a2.setEndTime(cNow.getTime());
        a3.setEndTime(cNow.getTime());
        a4.setEndTime(cNow.getTime());

        u2.addItem(a1);
        itemsRepository.save(a1);
        u2.addItem(a2);
        itemsRepository.save(a2);
        u2.addItem(a3);
        itemsRepository.save(a3);
        u2.addItem(a4);
        itemsRepository.save(a4);
        u2.addItem(a5);
        itemsRepository.save(a5);
        u2.addItem(a6);
        itemsRepository.save(a6);
        u2.addItem(a7);
        itemsRepository.save(a7);
        u2.addItem(a8);
        itemsRepository.save(a8);


        //timers
        auctionTimer.startTimer(a1);
        auctionTimer.startTimer(a2);
        auctionTimer.startTimer(a3);
        auctionTimer.startTimer(a4);


    }

    @GetMapping("/")
    public String redirectIndex() {

        return "redirect:/allitems?page=0";
    }
//Metod för pagination
    @GetMapping("/allitems")
    public String index(Model model, @RequestParam(name = "page") Integer page) {
        if (page<=0 || page==null) {
            page = 0;
        }

        final int PAGESIZE = 6;
        PageRequest pagin = PageRequest.of(page, PAGESIZE);
        Page<Item> pagedResult = itemsRepository.findAllByOrderByEndTimeAsc(pagin);
        List<Item> items = pagedResult.getContent();

        //HashMap
        Map<Integer, Integer> totalPagesPairDisplay = new HashMap<>();
        for (int i = 0; i < pagedResult.getTotalPages(); i++) {
            totalPagesPairDisplay.put(i, i+1);
        }

        //getContent för hur många inlägg per sida
        model.addAttribute("currentPageNr", pagedResult.getNumber());
        model.addAttribute("displayCurrentPageNr", pagedResult.getNumber() + 1);
        model.addAttribute("nextPageNumber", page+1);
        model.addAttribute("previousPageNumber", page-1);
        model.addAttribute("totalPages", pagedResult.getTotalPages());
        model.addAttribute("totalItems", pagedResult.getTotalElements());
        model.addAttribute("hasNext", pagedResult.hasNext());
        model.addAttribute("hasPrevious", pagedResult.hasPrevious());
        model.addAttribute("showingNrOfPosts", PAGESIZE);


        model.addAttribute("currentCategory", new Category("All items", ""));
        model.addAttribute("totalPagesPairDisplay", totalPagesPairDisplay.entrySet());
        model.addAttribute("items",items);
        model.addAttribute("category",categoriesRepository.findAll());

        return "bidder";
    }

    @GetMapping("/home/{id}")
    public String findByCategory(Model model,@PathVariable Integer id) {

        model.addAttribute("currentCategory", categoriesRepository.findById(id).get());
        model.addAttribute("items",itemsRepository.findAllItemsByCategory(id));
        model.addAttribute("category",categoriesRepository.findAll());

        return "bidder";
    }

    @GetMapping(value = "/Itemsite")
    public String GetItemSite() {
        return "Item";
    }

    //Öppnar html för en produkt
    @GetMapping("/auctionitem")
    public String getSingleItemPage(Model model, @RequestParam(name = "id") Integer id) {
        User user = usersRepository.findByEmail(TestController.getLoggedInUser());
        Item item = itemsRepository.findById(id).get();

        User loggedInUser = usersRepository.findByEmail(TestController.getLoggedInUser());
        model.addAttribute("loggedin",loggedInUser);
        model.addAttribute("item", itemsRepository.findById(id).get());
        model.addAttribute("top3bids", bidsRepository.findTop3ByItemOrderByPriceDesc(item));
        return "item";
    }
}
