package com.gik2h9.project.services;

import org.springframework.stereotype.Service;

import java.util.Observable;
import java.util.Observer;

@Service
public class BidderNoticeService implements Observer {


    @Override
    public void update(Observable o, Object arg) {
        System.out.println(arg);



    }
}
