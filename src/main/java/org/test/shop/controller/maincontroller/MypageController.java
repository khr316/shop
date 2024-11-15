package org.test.shop.controller.maincontroller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.test.shop.dao.maindao.MypageDao;
import org.test.shop.dao.userdao.UserDao;

import java.util.*;

@Controller
public class MypageController {
    @Autowired
    MypageDao mypageDao;
    UserDao userDao;

    @GetMapping("/mypage")
    public String mypage(HttpSession session,
                         Model model) {
        // 회원만 들어갈 수 있음
        if (session.getAttribute("email") == null) {
            return "redirect:/login";
        }

        String email = session.getAttribute("email").toString();
        model.addAttribute("email", email);

        Map<String, Object> userInfo = mypageDao.selectUser(email);
        model.addAttribute("userinfo",userInfo);
        return "main/mypage";
    }

    @PostMapping("/mypage/userinfo")
    public String mypageInfo(@RequestParam String id,
                             @RequestParam String name,
                             @RequestParam String email,
                             @RequestParam String pw,
                             @RequestParam String address,
                             @RequestParam String address2) {

        String ar = address + " " + address2;

        mypageDao.updateUser(id,name,email,pw,ar);

        return "redirect:/";
    }

}


