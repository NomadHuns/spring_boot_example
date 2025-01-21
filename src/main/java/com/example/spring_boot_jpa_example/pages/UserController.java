package com.example.spring_boot_jpa_example.pages;

import com.example.spring_boot_jpa_example._core.utils.ViewValidErrorUtils;
import com.example.spring_boot_jpa_example.module.users.UsersImpService;
import com.example.spring_boot_jpa_example.module.users.dtos.UsersSaveRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/{id}")
    public String test(Model model, @PathVariable Long id) {
        var responseDTO = usersImpService.detail(id);

        model.addAttribute("test",responseDTO.user().getUsername());
        return "user/user";
    }

    @PostMapping("/insert")
    public String insert(@Valid UsersSaveRequestDTO requestDTO, Errors errors) {
        ViewValidErrorUtils.errorCheck(errors);
        var responseDTO = usersImpService.save(requestDTO);
        return "redirect:/test/"+responseDTO.user().getId();
    }


}
