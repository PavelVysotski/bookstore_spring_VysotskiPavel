package com.belhard.dao.order;

import com.belhard.dao.entity.OrderItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class OrderItemDaoImpl implements OrderItemDao {

    private static final Logger logger = LogManager.getLogger(OrderItemDaoImpl.class);

    private static final String SELECT_BY_ID = "SELECT id, order_id, book_id, quantity, price FROM order_item WHERE order_id = :id";
    private static final String DELETE_BY_ID = "DELETE FROM order_item WHERE id=:id";
    private static final String UPDATE = "UPDATE order_item SET order_id = :orderId, book_id = :bookId, quantity = :quantity, " +
            "price = :price WHERE id = :id";
    private static final String ADD = "INSERT INTO order_item (order_id, book_id, quantity, price) " +
            "VALUES (:orderId, :bookId, :quantity, :price)";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final OrderItemRowMapper orderItemRowMapper;

    @Autowired
    public OrderItemDaoImpl(NamedParameterJdbcTemplate jdbcTemplate, OrderItemRowMapper orderItemRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.orderItemRowMapper = orderItemRowMapper;
    }

    @Override
    public List<OrderItem> getAll() {
        return null;
    }

    @Override
    public List<OrderItem> getByOrderId(Long id) {
        logger.debug("Getting the order_items from the database by ORDER_ID={}", id);
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbcTemplate.query(SELECT_BY_ID, params, orderItemRowMapper);
    }

    @Override
    public OrderItem getOrderItemById(Long id) {
        return null;
    }

    @Override
    public OrderItem createOrderItem(OrderItem newOrderItem) {
        logger.debug("Creating new order_item and adding to database.");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", newOrderItem.getOrderId());
        params.put("bookId", newOrderItem.getBookId());
        params.put("quantity", newOrderItem.getQuantity());
        params.put("price", newOrderItem.getPrice());
        SqlParameterSource source = new MapSqlParameterSource(params);
        int rowsUpdate = jdbcTemplate.update(ADD, source, keyHolder, new String[]{"id"});
        if (rowsUpdate != 1) {
            throw new RuntimeException("Can't create the order_item" + newOrderItem);
        }
        Long id = Optional.ofNullable(keyHolder.getKey())
                .map(Number::longValue)
                .orElseThrow(() -> new RuntimeException("Can't create the order_item" + newOrderItem));
        return getOrderItemById(id);
    }

    @Override
    public OrderItem updateOrderItem(OrderItem updateOrderItem) {
        logger.debug("Updating existing order_item in the database.");
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", updateOrderItem.getOrderId());
        params.put("bookId", updateOrderItem.getBookId());
        params.put("quantity", updateOrderItem.getQuantity());
        params.put("price", updateOrderItem.getPrice());
        params.put("id", updateOrderItem.getId());
        SqlParameterSource source = new MapSqlParameterSource(params);
        int rowsUpdate = jdbcTemplate.update(UPDATE, source);
        if (rowsUpdate != 1) {
            throw new RuntimeException("Can't update a order" + updateOrderItem);
        }
        return getOrderItemById(updateOrderItem.getId());
    }

    @Override
    public boolean deleteOrderItemById(Long id) {
        logger.debug("Deleting a order_item from the database by ID={}", id);
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        jdbcTemplate.update(DELETE_BY_ID, params);
        return false;
    }
}
