package com.belhard.dao.user;

import com.belhard.dao.connection.DbConnection;
import com.belhard.dao.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

@Component
public class UserDaoImpl implements UserDao {

    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    private static final String SELECT_ALL = "SELECT u.id, u.name, u.second_name, u.email, u.password, r.role " +
            "AS role FROM users u JOIN roles r ON u.role_id = r.id WHERE activity = true ORDER BY id ASC";
    private static final String SELECT_BY_ID = "SELECT u.id, u.name, u.second_name, u.email, u.password, r.role " +
            "AS role FROM users u JOIN roles r ON u.role_id = r.id WHERE u.id = ? AND activity = true ORDER BY id ASC";
    private static final String ADD = "INSERT INTO users (name, second_name, email, password, role_id) " +
            "VALUES (?, ?, ?, ?, (SELECT id FROM roles WHERE role = ?))";
    private static final String UPDATE = "UPDATE users SET name = ?, second_name = ?, email = ?, password = ?, role_id = " +
            "(SELECT id FROM roles WHERE role = ?) WHERE id = ?";
    private static final String DELETE_BY_ID = "UPDATE users SET activity = false WHERE id = ? AND activity = true";

    private static final Connection CONNECTION = DbConnection.getConnection();

    @Override
    public List<User> getAllUsers() {
        logger.debug("Getting all users from database.");
        List<User> result = new ArrayList<>();
        try {
            PreparedStatement statement = CONNECTION.prepareStatement(SELECT_ALL);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User users = userToObj(resultSet);
                result.add(users);
            }
        } catch (SQLException e) {
            logger.error("Check the SQL request.", e);
        }
        return result;
    }

    @Override
    public User getUserById(Long id) {
        logger.debug("Getting user from database by ID={}.", id);
        User user = null;
        try {
            PreparedStatement statement = CONNECTION.prepareStatement(SELECT_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            user = userToObj(resultSet);
        } catch (SQLException e) {
            logger.error("Check the SQL request.", e);
        }
        return user;
    }

    @Override
    public User createUser(User newUser) {
        logger.debug("Creating new user and adding to database.");
        try {
            PreparedStatement statement = CONNECTION.prepareStatement(ADD, RETURN_GENERATED_KEYS);
            statement.setString(1, newUser.getName());
            statement.setString(2, newUser.getSecondName());
            statement.setString(3, newUser.getEmail());
            statement.setString(4, newUser.getPassword());
            statement.setString(5, newUser.getRole().toString());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return getUserById(generatedKeys.getLong("id"));
            } else {
                throw new RuntimeException("Failed to create user.");
            }
        } catch (SQLException e) {
            logger.error("Check the SQL request.", e);
        }
        throw new RuntimeException("User not created.");
    }

    @Override
    public User updateUser(User updateUser) {
        logger.debug("Updating existing user.");
        try {
            PreparedStatement statement = CONNECTION.prepareStatement(UPDATE, RETURN_GENERATED_KEYS);
            statement.setString(1, updateUser.getName());
            statement.setString(2, updateUser.getSecondName());
            statement.setString(3, updateUser.getEmail());
            statement.setString(4, updateUser.getPassword());
            statement.setString(5, updateUser.getRole().toString());
            statement.setLong(6, updateUser.getId());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return getUserById(generatedKeys.getLong("id"));
            } else {
                throw new RuntimeException("Failed to update user.");
            }
        } catch (SQLException e) {
            logger.error("Check the SQL request.", e);
        }
        throw new RuntimeException("User not updated.");
    }

    @Override
    public boolean deleteUserById(Long id) {
        logger.debug("Deleting existing user by ID={}.", id);
        try {
            PreparedStatement statement = CONNECTION.prepareStatement(DELETE_BY_ID);
            statement.setLong(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error("Check the SQL request.", e);
        }
        return false;
    }

    private User userToObj(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setName(resultSet.getString("name"));
        user.setSecondName(resultSet.getString("second_name"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(User.UserRole.valueOf(resultSet.getString("role")));

        return user;
    }
}
