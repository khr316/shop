package org.test.shop.controller.usercontroller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
        int user_ = userDao.selectUser(email, pw);
        if (user_ > 0) { // 회원 정보가 있는 경우
            session.setAttribute("email", email); // 세션에 회원 이메일 저장 후
            return "redirect:/"; // 메인 페이지로
        }
        // 회원 정보가 없는 경우 로그인 페이지로
        model.addAttribute("login_error", "이메일 또는 비밀번호를 확인하세요");
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
        String ar = address + " " + address2;
        // 회원가입 정보 저장
        userDao.insertUser(email, pw, name, ar);
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

    // 비밀번호 찾기 - 이메일 임시비번 발급
    @GetMapping("/find-pw")
    public String findPw() {
        return "user/find-pw";
    }

    @Autowired
    JavaMailSender mailSender;

    @PostMapping("/find/pw")
    public String findPwAction(@RequestParam String email, @RequestParam String name, Model model) {
        // 1. 이메일과 이름으로 사용자가 존재하는지 확인
        if (userDao.isUserExist(email, name) > 0) {
            // 2. 임시 비밀번호 생성
            String tempPassword = userDao.generateTempPassword();

            // 3. 임시 비밀번호 업데이트
            userDao.updatePassword(email, tempPassword);

            // 4. 임시 비밀번호 이메일로 전송
            sendEmail(email, tempPassword);

            // 5. 임시 비밀번호 발급 후
            model.addAttribute("ok","임시 비밀번호가 발급되었습니다. 메일을 확인해주세요.");

            return "user/find-pw";
        } else {
            // 6. 사용자가 존재하지 않으면 오류 메시지 추가
            model.addAttribute("error", "이메일 또는 이름을 확인해주세요");
            return "user/find-pw"; // 다시 비밀번호 찾기 페이지로
        }
    }

    public void sendEmail(String email, String tempPassword) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("임시 비밀번호 발급 안내");
            message.setText("귀하의 임시 비밀번호는 " + tempPassword + " 입니다.");
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            // 이메일 발송 실패 시 처리 로직 추가 (예: 오류 메시지 출력)
        }
    }



}
