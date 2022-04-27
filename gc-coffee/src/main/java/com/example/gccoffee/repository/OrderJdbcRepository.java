package com.example.gccoffee.repository;

import com.example.gccoffee.domain.Order;
import com.example.gccoffee.domain.OrderItem;
import com.example.gccoffee.utils.Utils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class OrderJdbcRepository implements OrderRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrderJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Order insert(Order order) {
        jdbcTemplate.update("INSERT INTO orders(id, email, address, postcode, order_status, created_at, updated_at) " +
                "VALUES (:id, :email, :address, :postcode, :orderStatus, :createdAt, :updatedAt)", toOrderParaMap(order));

        order.getOrderItems().forEach(
                item -> jdbcTemplate.update(
                        "INSERT INTO order_item(order_id, product_id, category, price, quantity, created_at, updated_at)" +
                        "VALUES (:orderId, :productId, :category, :price, :quantity, :createdAt, :updatedAt)",
                        toOrderItemParamMap(order.getId(), order.getCreatedAt(), order.getUpdatedAt(), item)
                )
        );

        return order;
    }

    private Map<String, Object> toOrderParaMap(Order order){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", Utils.uuidToBytes(order.getId()));
        paramMap.put("email", order.getEmail().getAddress());
        paramMap.put("address", order.getAddress());
        paramMap.put("postcode", order.getPostCode());
        paramMap.put("orderStatus", order.getOrderStatus().toString());
        paramMap.put("createdAt", order.getCreatedAt());
        paramMap.put("updatedAt", order.getUpdatedAt());

        return paramMap;
    }

    private Map<String, Object> toOrderItemParamMap(
            UUID orderId,
            LocalDateTime orderCreatedAt,
            LocalDateTime orderUpdatedAt,
            OrderItem item
    ) {
        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("orderId", Utils.uuidToBytes(orderId));
        paramMap.put("productId", Utils.uuidToBytes(item.getProductId()));
        paramMap.put("category", item.getCategory().toString());
        paramMap.put("price", item.getPrice());
        paramMap.put("quantity", item.getQuantity());
        paramMap.put("updatedAt", orderUpdatedAt);
        paramMap.put("createdAt", orderCreatedAt);

        return paramMap;
    }
}
