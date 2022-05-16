package com.belhard.service.dto.user;

import java.util.Objects;

public class UserDto {

    private Long id;
    private String name;
    private String secondName;
    private String email;
    private String password;
    private UserRoleDto role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRoleDto getRole() {
        return role;
    }

    public void setRole(UserRoleDto role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id)
                && Objects.equals(name, userDto.name)
                && Objects.equals(secondName, userDto.secondName)
                && Objects.equals(email, userDto.email)
                && Objects.equals(password, userDto.password)
                && role == userDto.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, secondName, email, password, role);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", secondName='" + secondName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }

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
