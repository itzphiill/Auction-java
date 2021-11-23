package com.gik2h9.project.AuctionTimer;

import com.gik2h9.project.models.Bid;
import com.gik2h9.project.models.Item;
import com.gik2h9.project.interfaces.BidsRepository;
import com.gik2h9.project.interfaces.ItemsRepository;
import com.gik2h9.project.services.BidderNoticeService;
import com.gik2h9.project.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static java.util.Comparator.comparing;

@Repository
public class AuctionTimer {

    @Autowired
    ItemsRepository itemsRepository;
    @Autowired
    BidsRepository bidsRepository;
    @Autowired
    EmailService emailService;

    public void startTimer(Item item) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Item savedEndedAuctionItem;
                List<Bid> bids;
                savedEndedAuctionItem = itemsRepository.findById(item.getId()).get();

                //ändrar till disabled och sparar informationen
                savedEndedAuctionItem.setEnabled(0);
                System.out.println(savedEndedAuctionItem.getName() + " ändrad! Enabled: " + savedEndedAuctionItem.getEnabled());
                //System.out.println(bidsRepository.findAllByItemOrderByPrice(item));
                savedEndedAuctionItem = itemsRepository.save(savedEndedAuctionItem);
                bids = bidsRepository.findAllByItemOrderByPrice(item);

                Bid highestBid = null;
                //vinnare
                if (!(bids.isEmpty())) {
                    highestBid = bids.stream().max(comparing(Bid::getPrice)).get();
                    System.out.println("Number of bids: " + bids.size());
                    System.out.println("Highest bid: " + highestBid.getPrice());
                    System.out.println("We have a winner: " + highestBid.getUser().getName());
                    System.out.println("Mail: " + highestBid.getUser().getEmail());

                    //JavaMailSender skickar mail till vinnaren
                    String emailBodyText = String.format("Hello %s! You won the auction with item %s with the winning bid %d SEK. \nThere were %o bids. Please pay. \nYours sincerely, The Auction company",
                            highestBid.getUser().getName(), savedEndedAuctionItem.getName(), highestBid.getPrice(), bids.size());
                    emailService.sendEmailNotification(highestBid.getUser().getEmail(), "You've won! auction: " + savedEndedAuctionItem.getName(), emailBodyText);
                } else {
                    System.out.println("No winner");
                }
            }
        };
        timer.schedule(task, item.getEndTime());
    }
}
