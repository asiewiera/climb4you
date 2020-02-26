package com.asiewiera.climb4youapp.entities;

import com.asiewiera.climb4youapp.enums.UserStatus;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, name = "fist_name")
    private String firstName;

    @Column(length = 100, name = "last_name")
    private String lastName;

    @Column(length = 50, name = "login")
    private String login;

    @Column(length = 100, name = "password")
    private String password;

    @Column(length = 100, name = "email")
    private String email;

    @Column(length = 50, name = "phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(length = 100, name = "status")
    private UserStatus status;

    @Column(length = 50, name = "token")
    private String token;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Address> addressList;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Set<Role> roles;

}
