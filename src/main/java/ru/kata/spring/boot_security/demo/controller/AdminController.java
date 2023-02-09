package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String showAllUsers(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("users", userService.allUsers());
        model.addAttribute("newUser", new User());
        return "users";
    }

    @GetMapping("/addUser")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "addUser";
    }

    @PostMapping("/addUser")
    public String add(@ModelAttribute("user") User user,
                      @RequestParam(value = "newRole", required = false) String[] role) {
        userService.addUser(user);
        return "redirect:/admin/";
    }

    @GetMapping("/update")
    public String editUser(@RequestParam(value = "id") int id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "update";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user, int id,
                             @RequestParam(name = "allRoles", required = false) String[] roles){
        userService.editUser(id, user);
        return "redirect:/admin/";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin/";
    }

    @GetMapping("/{id}")
    public String showUser(Principal principal, Model model){
        User username = userService.getUserByName(principal.getName());
        model.addAttribute("user", username);
        return "user";
    }
}
