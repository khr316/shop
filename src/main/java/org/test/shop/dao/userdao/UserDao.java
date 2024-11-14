package org.test.shop.dao.userdao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void insertUser(String email,
                           String pw,
                           String name,
                           String ar) {
        String sql = "insert into users (email,pw,name,address) ";
        sql += "values (?,?,?,?)";
        jdbcTemplate.update(sql, email,pw,name,ar);
    }

    public int selectUser(String email, String pw) {
        String sql = "SELECT count(*) FROM users WHERE email = ? AND pw = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, email, pw);
    }

    public boolean checkEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

}
