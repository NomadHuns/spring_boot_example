package com.example.spring_boot_jpa_example.pages;

import com.example.spring_boot_jpa_example.module.users.UsersImpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/test")
public class UserController {
    private final UsersImpService usersImpService;

    @GetMapping
    public String test(Model model) {
        var responseDTO = usersImpService.detail(1l);

        model.addAttribute("test",responseDTO.user().getUsername());
        return "user/user";
    }
}
