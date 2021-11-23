package com.gik2h9.project.controllers;

import com.gik2h9.project.models.Category;
import com.gik2h9.project.models.Item;
import com.gik2h9.project.models.User;
import com.gik2h9.project.interfaces.CategoriesRepository;
import com.gik2h9.project.interfaces.ItemsRepository;
import com.gik2h9.project.interfaces.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/")
public class AdminController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    ItemsRepository itemsRepository;

    @Autowired
    CategoriesRepository categoriesRepository;

    //get all items till index och admin.
    @GetMapping("")
    public String index(Model model,@RequestParam(required = false) Integer id) {
        User loggedInUser = usersRepository.findByEmail(TestController.getLoggedInUser());
        model.addAttribute("admin", loggedInUser);
        List<Item> items = itemsRepository.findAll();
        model.addAttribute("items", items);
        model.addAttribute("loggedin",loggedInUser);
        List<User> users = usersRepository.findAll();
        model.addAttribute("users", users);

        List<Category> categories= categoriesRepository.findAll();
        model.addAttribute("category", categories);

        return "admin";
    }

    //Update Item
    @GetMapping("/updateItem")
    public String updateItem(@RequestParam(defaultValue = "-1") String name,
                             @RequestParam(defaultValue = "-1") String description,
                             @RequestParam(defaultValue = "-1") String startingBid,
                             @RequestParam(defaultValue = "-1") String picture,
                             @RequestParam(defaultValue = "-1") String id,
                             @RequestParam(defaultValue = "-1") String category) {
        Category category1 = categoriesRepository.findByTitle(category);
        itemsRepository.updateItems(name,description,Integer.parseInt(startingBid),picture,category1,Integer.parseInt(id));

        return "redirect:/admin/";
    }

    //Update User
    @GetMapping("/updateUser")
    public String updateUser(@RequestParam(defaultValue = "-1") String name,
                             @RequestParam(defaultValue = "-1") String email,
                             @RequestParam(defaultValue = "-1") String description,
                             @RequestParam(defaultValue = "-1") String id){
        usersRepository.updateUser(name,email,description,Integer.parseInt(id));

        return "redirect:/admin/";
    }

    //Delete Item
    @GetMapping(value = "/deleteItem/{id}")
    public String deleteItem(@PathVariable Integer id){
        itemsRepository.deleteById(id);
        return "redirect:/admin/";
    }

    //Delete User
    @GetMapping(value = "/deleteUser/{id}")
    public String deleteUser(@PathVariable Integer id){
        usersRepository.deleteById(id);
        return "redirect:/admin/";
    }
}
