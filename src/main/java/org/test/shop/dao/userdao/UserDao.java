package org.test.shop.dao.userdao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Repository
@Service
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

    // 사용자 존재 여부 확인
    public int isUserExist(String email, String name) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ? AND name = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, email,name);
    }

    // 임시 비밀번호 생성 (랜덤 UUID를 사용)
    public String generateTempPassword() {
        return UUID.randomUUID().toString().substring(0, 8);  // 8자리 임시 비밀번호
    }

    // 임시 비밀번호 업데이트
    public void updatePassword(String email, String tempPassword) {
        String query = "UPDATE users SET pw = ? WHERE email = ?";
        jdbcTemplate.update(query, tempPassword, email);
    }





}
