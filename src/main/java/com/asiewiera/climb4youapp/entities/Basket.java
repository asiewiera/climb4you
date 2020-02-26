package com.asiewiera.climb4youapp.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "basket")
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinColumn(name = "basket_id")
    private List<BasketItem> basketItems;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
