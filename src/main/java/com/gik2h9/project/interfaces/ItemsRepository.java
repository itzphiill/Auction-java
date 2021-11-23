package com.gik2h9.project.interfaces;

import com.gik2h9.project.models.Category;
import com.gik2h9.project.models.FinishedAuction; /// KOLLA PÅ DETTA IGEN
import com.gik2h9.project.models.Item;
import com.gik2h9.project.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date; // KOLLA PÅ DETTA IGEN
import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<Item, Integer> {


    //Injects behaviors before, after, or around method calls into the object.
    @Transactional
    @Modifying
    @Query("UPDATE Item i SET i.name = ?1, i.description = ?2, i.startingBid = ?3, i.picture = ?4, i.category = ?5 WHERE i.id = ?6")
    void updateItem(String name, String description, int startingBid, String picture, Category category, int id);

    @Transactional
    @Modifying
    @Query("UPDATE Item i SET i.name = ?1, i.description = ?2, i.startingBid = ?3, i.picture = ?4, i.category = ?5 WHERE i.id = ?6")
    void updateItems(String name, String description, int startingBid, String picture, Category category1, int id);

    List<Item> findAllByCategory(Category category, PageRequest pagin);

    @Query(value = "SELECT i FROM Item i ORDER BY i.endTime ASC")
    Page<Item> findAllByOrderByEndTimeAsc(PageRequest pagin);

    @Query("SELECT i FROM Item i WHERE i.category.id = ?1")
    List<Item> findAllItemsByCategory(int id);

    List<Item> findAllByUser(User user);
}
