package com.belhard.dao.order;

import com.belhard.dao.entity.Order;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OrderRowMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getLong("id"));
        order.setUserId(resultSet.getLong("user_id"));
        order.setTotalCost(resultSet.getBigDecimal("total_cost"));
        order.setCreateTime(resultSet.getTimestamp("create_time").toLocalDateTime());
        if (resultSet.getTimestamp("update_time") != null) {
            order.setUpdateTime(resultSet.getTimestamp("update_time").toLocalDateTime());
        }
        order.setStatus(Order.OrderStatus.valueOf(resultSet.getString("status")));
        return order;
    }
}
