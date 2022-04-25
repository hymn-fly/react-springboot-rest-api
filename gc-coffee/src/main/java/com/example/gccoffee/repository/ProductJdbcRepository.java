package com.example.gccoffee.repository;

import com.example.gccoffee.domain.Category;
import com.example.gccoffee.domain.Product;
import com.example.gccoffee.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.*;

import static com.example.gccoffee.utils.Utils.*;
import static com.google.common.base.Preconditions.checkArgument;

@Repository
public class ProductJdbcRepository implements ProductRepository{
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public ProductJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query("SELECT * from product", this::productRowMapper);
    }

    @Override
    public Product insert(Product product) {
        val update = jdbcTemplate.update("INSERT INTO product(id, name, category, price, description, created_at, updated_at) " +
                        "values(:id, :name, :category, :price, :description, :createdAt, :updatedAt)", mapOfProduct(product));

        checkArgument(update == 1, "Nothing was inserted");

        return product;
    }

    @Override
    public Product update(Product product) {
        val update = jdbcTemplate.update("UPDATE product SET name = :name, category = :category, price = :price, description = :description, created_at= :createdAt, updated_at = :updatedAt " +
                "where id = :id", mapOfProduct(product));

        checkArgument(update == 1, "Nothing to update");

        return product;
    }

    @Override
    public Optional<Product> findById(UUID id) {
        try{
            return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM product WHERE id = :id", Collections.singletonMap("id", uuidToBytes(id)), this::productRowMapper));
        }
        catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Product> findByName(String name) {
        try{
            return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM product WHERE name = :name", Collections.singletonMap("name", name), this::productRowMapper));
        }
        catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return jdbcTemplate.query("SELECT * FROM product WHERE category = :category", Collections.singletonMap("category", category.toString()), this::productRowMapper);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM product", Collections.emptyMap());
    }

    private Product productRowMapper(ResultSet rs, int rowNum) throws SQLException {
        try{
            val productId = bytesToUUID(rs.getBytes("id"));
            val productName = rs.getString("name");
            val category = Category.valueOf(rs.getString("category"));
            val price = rs.getLong("price");
            val description = rs.getString("description");
            val createdAt = toLocalDateTime(rs.getTimestamp("created_at"));
            val updatedAt = toLocalDateTime(rs.getTimestamp("updated_at"));

            return new Product(
                    productId,
                    productName,
                    category,
                    price,
                    description,
                    createdAt,
                    updatedAt
            );
        }
        catch (SQLException e){
            throw new RuntimeException(MessageFormat.format("데이터 매핑이 잘못되었습니다. 에러메시지 : {0}", e.getMessage()));
        }
    }

    private Map<String, Object> mapOfProduct(Product product){
        val map = new HashMap<String, Object>();

        map.put("id", Utils.uuidToBytes(product.getId()));
        map.put("name", product.getName());
        map.put("category", product.getCategory().toString() );
        map.put("price", product.getPrice() );
        map.put("description", product.getDescription() );
        map.put("createdAt", product.getCreatedAt() );
        map.put("updatedAt", product.getUpdatedAt() );

        return map;
    }
}
