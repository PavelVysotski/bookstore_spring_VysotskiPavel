package com.belhard.service.dto.user;

import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String name;
    private String secondName;
    private String email;
    private String password;
    private UserRoleDto role;

    public enum UserRoleDto {

        ADMIN("admin"),
        MANAGER("manager"),
        CUSTOMER("customer");

        private final String title;

        UserRoleDto(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }
}
