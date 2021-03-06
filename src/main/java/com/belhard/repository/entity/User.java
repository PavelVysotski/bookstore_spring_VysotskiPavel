package com.belhard.repository.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public enum UserRole {

        ADMIN("admin"),
        MANAGER("manager"),
        CUSTOMER("customer");

        private final String title;

        UserRole(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }
}
