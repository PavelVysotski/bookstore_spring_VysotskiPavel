package com.belhard.dao.user;

import com.belhard.dao.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class UserDaoImpl implements UserDao {

    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    private static final String SELECT_ALL = "SELECT u.id, u.name, u.second_name, u.email, u.password, r.role " +
            "AS role FROM users u JOIN roles r ON u.role_id = r.id WHERE activity = true ORDER BY id ASC";
    private static final String SELECT_BY_ID = "SELECT u.id, u.name, u.second_name, u.email, u.password, r.role " +
            "AS role FROM users u JOIN roles r ON u.role_id = r.id WHERE u.id = :id AND activity = true ORDER BY id ASC";
    private static final String ADD = "INSERT INTO users (name, second_name, email, password, role_id) " +
            "VALUES (:name, :secondName, :email, :password, (SELECT id FROM roles WHERE role = :role))";
    private static final String UPDATE = "UPDATE users SET name = :name, second_name = :secondName, email = :email, password = :password, role_id = " +
            "(SELECT id FROM roles WHERE role = :role) WHERE id = :id";
    private static final String DELETE_BY_ID = "UPDATE users SET activity = false WHERE id = :id AND activity = true";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;

    @Autowired
    public UserDaoImpl(NamedParameterJdbcTemplate jdbcTemplate, UserRowMapper userRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRowMapper = userRowMapper;
    }

    @Override
    public List<User> getAllUsers() {
        logger.debug("Getting all users from database.");
        return jdbcTemplate.query(SELECT_ALL, userRowMapper);
    }

    @Override
    public User getUserById(Long id) {
        logger.debug("Getting user from database by ID={}.", id);
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbcTemplate.queryForObject(SELECT_BY_ID, params, userRowMapper);
    }

    @Override
    public User createUser(User newUser) {
        logger.debug("Creating new user and adding to database.");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Map<String, Object> params = new HashMap<>();
        params.put("name", newUser.getName());
        params.put("secondName", newUser.getSecondName());
        params.put("email", newUser.getEmail());
        params.put("password", newUser.getPassword());
        params.put("role", newUser.getRole().toString());
        SqlParameterSource source = new MapSqlParameterSource(params);
        int rowsUpdate = jdbcTemplate.update(ADD, source, keyHolder, new String[]{"id"});
        if (rowsUpdate != 1) {
            throw new RuntimeException("Can't create the user" + newUser);
        }
        Long id = Optional.ofNullable(keyHolder.getKey())
                .map(Number::longValue)
                .orElseThrow(() -> new RuntimeException("Can't create the user" + newUser));
        return getUserById(id);
    }

    @Override
    public User updateUser(User updateUser) {
        logger.debug("Updating existing user.");
        Map<String, Object> params = new HashMap<>();
        params.put("name", updateUser.getName());
        params.put("secondName", updateUser.getSecondName());
        params.put("email", updateUser.getEmail());
        params.put("password", updateUser.getPassword());
        params.put("role", updateUser.getRole().toString());
        params.put("id", updateUser.getId());
        SqlParameterSource source = new MapSqlParameterSource(params);
        int rowsUpdate = jdbcTemplate.update(UPDATE, source);
        if (rowsUpdate != 1) {
            throw new RuntimeException("Can't update the user" + updateUser);
        }
        return getUserById(updateUser.getId());
    }

    @Override
    public boolean deleteUserById(Long id) {
        logger.debug("Deleting existing user by ID={}.", id);
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        int rowsUpdate = jdbcTemplate.update(DELETE_BY_ID, params);
        if (rowsUpdate != 1) {
            throw new RuntimeException("Can't delete the user with id = " + id);
        }
        return rowsUpdate == 1;
    }
}
