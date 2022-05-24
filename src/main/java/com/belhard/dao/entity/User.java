package com.belhard.dao.entity;

import lombok.Data;

@Data
public class User {

    private Long id;
    private String name;
    private String secondName;
    private String email;
    private String password;
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
