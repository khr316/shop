package org.test.shop.controller.usercontroller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.test.shop.dao.userdao.UserDao;
import java.util.*;

@Controller
public class UserController {
    @Autowired
    UserDao userDao;

    // 로그인
    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @PostMapping("/login/action")
    public String loginAction(HttpSession session,
                              @RequestParam String email,
                              @RequestParam String pw,
                              Model model) {
        int user_ = userDao.selectUser(email,pw);
        if (user_ > 0) { // 회원 정보가 있는 경우
            session.setAttribute("email",email); // 세션에 회원 이메일 저장 후
            return "redirect:/"; // 메인 페이지로
        }
        // 회원 정보가 없는 경우 로그인 페이지로
        model.addAttribute("login_error","이메일 또는 비밀번호를 확인하세요");
        return "user/login";
    }

    // 회원가입
    @GetMapping("/signup")
    public String signup() {
        return "user/signup";
    }

    @PostMapping("/signup/action")
    public String signupAction(@RequestParam String email,
                               @RequestParam String pw,
                               @RequestParam String name,
                               @RequestParam String address,
                               @RequestParam String address2) {
        String ar = address +" "+ address2;
        // 회원가입 정보 저장
        userDao.insertUser(email,pw,name,ar);
        return "redirect:/login";
    }

    // 이메일 중복 체크 API
    @GetMapping("/signup/email-check")
    @ResponseBody
    public Map<String, Boolean> checkEmailExists(@RequestParam String email) {
        boolean exists = userDao.checkEmailExists(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return response;
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}
