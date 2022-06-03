package com.belhard.dao.order;

import com.belhard.dao.entity.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class OrderDaoImpl implements OrderDao {

    private static final Logger logger = LogManager.getLogger(OrderDaoImpl.class);

    private static final String SELECT_BY_ID = "SELECT o.id, o.user_id, o.total_cost, o.create_time, o.update_time, os.status " +
            "FROM orders o JOIN users u ON (o.user_id=u.id) JOIN order_status os ON (o.status_id=os.id) WHERE o.id = :id AND os.status != 'CANCELED'";
    private static final String SELECT_ALL = "SELECT o.id, o.user_id, o.total_cost, o.create_time, o.update_time, os.status " +
            "FROM orders o JOIN users u ON (o.user_id=u.id) JOIN order_status os ON (o.status_id=os.id) WHERE os.status != 'CANCELED' ORDER BY id ASC";
    private static final String UPDATE = "UPDATE orders SET user_id = :userId, total_cost = :totalCost, update_time = :updateTime, " +
            "status_id = (SELECT id FROM order_status WHERE status = :status) WHERE id = :id";
    private static final String DELETE_BY_ID = "UPDATE orders SET status_id = 3 WHERE id = :id";
    private static final String ADD = "INSERT INTO orders (user_id, total_cost, status_id) " +
            "VALUES (:userId, :totalCost, (SELECT id FROM order_status WHERE status = :status))";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final OrderRowMapper orderRowMapper;

    @Autowired
    public OrderDaoImpl(NamedParameterJdbcTemplate jdbcTemplate, OrderRowMapper orderRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.orderRowMapper = orderRowMapper;
    }

    @Override
    public List<Order> getAll() {
        logger.debug("Getting a order list from the database.");
        return jdbcTemplate.query(SELECT_ALL, orderRowMapper);
    }

    @Override
    public Order getOrderById(Long id) {
        logger.debug("Getting a order from the database by ID={}", id);
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbcTemplate.queryForObject(SELECT_BY_ID, params, orderRowMapper);
    }

    @Override
    public Order createOrder(Order newOrder) {
        logger.debug("Creating new order and adding to database.");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Map<String, Object> params = new HashMap<>();
        params.put("userId", newOrder.getUserId());
        params.put("totalCost", newOrder.getTotalCost());
        params.put("status", newOrder.getStatus().toString());
        SqlParameterSource source = new MapSqlParameterSource(params);
        int rowsUpdate = jdbcTemplate.update(ADD, source, keyHolder, new String[]{"id"});
        if (rowsUpdate != 1) {
            throw new RuntimeException("Can't create the order_item" + newOrder);
        }
        Long id = Optional.ofNullable(keyHolder.getKey())
                .map(Number::longValue)
                .orElseThrow(() -> new RuntimeException("Can't create the order" + newOrder));
        return getOrderById(id);
    }

    @Override
    public Order updateOrder(Order updateOrder) {
        logger.debug("Updating existing order in the database.");
        Map<String, Object> params = new HashMap<>();
        params.put("userId", updateOrder.getUserId());
        params.put("totalCost", updateOrder.getTotalCost());
        params.put("updateTime", LocalDateTime.now());
        params.put("status", updateOrder.getStatus().toString());
        params.put("id", updateOrder.getId());
        SqlParameterSource source = new MapSqlParameterSource(params);
        int rowsUpdate = jdbcTemplate.update(UPDATE, source);
        if (rowsUpdate != 1) {
            throw new RuntimeException("Can't update a order" + updateOrder);
        }
        return getOrderById(updateOrder.getId());
    }

    @Override
    public boolean deleteById(Long id) {
        logger.debug("Deleting existing order by ID={}.", id);
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        int rowsUpdate = jdbcTemplate.update(DELETE_BY_ID, params);
        if (rowsUpdate != 1) {
            throw new RuntimeException("Can't delete the order with id = " + id);
        }
        return rowsUpdate == 1;
    }
}
