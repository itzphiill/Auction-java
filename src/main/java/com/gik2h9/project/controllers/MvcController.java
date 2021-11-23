package com.gik2h9.project.controllers;

import com.gik2h9.project.models.User;
import com.gik2h9.project.interfaces.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class MvcController {
    @Autowired
    UsersRepository usersRepository;
    @ModelAttribute("globalLoggedInUser")
    public User getLoggedInUser() {
        User loggedInUser = usersRepository.findByEmail(TestController.getLoggedInUser());

        return loggedInUser;
    }
}
