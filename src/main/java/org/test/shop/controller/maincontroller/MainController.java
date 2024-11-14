package org.test.shop.controller.maincontroller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    // 메인 페이지
    @GetMapping("/")
    public String mainpage(HttpSession session,
                       Model model) {
        // 로그인 한 회원 정보
        model.addAttribute("email",session.getAttribute("email"));
        return "main/main";
    }
}
