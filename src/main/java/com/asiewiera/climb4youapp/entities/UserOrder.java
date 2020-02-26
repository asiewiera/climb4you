package com.asiewiera.climb4youapp.entities;

import com.asiewiera.climb4youapp.enums.OrderStatus;
import lombok.Data;

import javax.jws.soap.SOAPBinding;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "user_order")
public class UserOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinColumn(name = "user_order_id")
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "modify_date")
    private Date modDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 100, name = "status")
    private OrderStatus status;
}
