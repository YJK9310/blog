package com.lrm.web.admin;

import com.lrm.po.User;
import com.lrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created by limi on 2017/10/15.
 */
@Controller
@RequestMapping("/a")
public class LoginController {


    @Autowired
    private UserService userService;

    @GetMapping
    //@GetMapping方法没写路径，8080就直接跳转/admin，识别方法后跳转到admin/login
    public String loginPage() {
        return "a/login";
    }


    @PostMapping("/login")
    //用户对象放入session
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        //将登录信息存在session中
                        HttpSession session,
                        //重定向只能用RedirectAttributes发送信息
                        RedirectAttributes attributes) {
        User user = userService.checkUser(username, password);
        //如果存在，保存并跳转
        if (user != null) {
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/index";
            //如果不存在，重定向
        } else {
            attributes.addFlashAttribute("message", "用户名和密码错误");
            //重定向不能使用model，页面拿不到信息，只能用RedirectAttributes
            //直接 return "/admin" 会产生路径问题，重定向不会
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        //登出后清空session
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
