package org.test.shop.dao.maindao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class MypageDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Map<String, Object> selectUser(String email){
        String sql = "select * from users where email = ?";
        return jdbcTemplate.queryForMap(sql, email);
    }


    public void updateUser(String id, String name, String email, String pw, String ar) {
        String sql = "update users set name = ?, ";
        sql += "email = ?, ";
        sql += "pw = ?, ";
        sql += "address = ? ";
        sql += "where id = ?";
        jdbcTemplate.update(sql, id,name,email,pw,ar);

    }
}
