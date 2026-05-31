package com.kuzmin.Project_i.controller;
import com.kuzmin.Project_i.model.Customer;
import com.kuzmin.Project_i.model.User;
import com.kuzmin.Project_i.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final ImportService importService;

    public CustomerController(
            CustomerService customerService,
            ImportService importService
    ) {
        this.customerService = customerService;
        this.importService = importService;
    }

    @GetMapping("/import")
    public String importPage() {
        return "customers/import";
    }

    @GetMapping
    public String findAll(
            Model model,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        model.addAttribute(
                "customers",
                customerService.findAllByUser(user)
        );

        return "customers/list";
    }

    @GetMapping("/{id}")
    public String findById(
            @PathVariable Long id,
            Model model,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        model.addAttribute(
                "customer",
                customerService.findByIdAndUser(
                        id,
                        user
                )
        );

        return "customers/view";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute(
                "customer",
                new Customer()
        );
        return "customers/create";
    }

    @PostMapping("/import")
    public String importCsv(
            @RequestParam("file") MultipartFile file,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        importService.importCsv(file, user);

        return "redirect:/dashboards";
    }

    @PostMapping("/create")
    public String create(
            @ModelAttribute Customer customer,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        customer.setUser(user);

        customerService.save(customer);

        return "redirect:/customers";
    }

    @GetMapping("/edit/{id}")
    public String editPage(
            @PathVariable Long id,
            Model model,
            Authentication authentication
    ) {
        if (authentication == null) {
            return "redirect:/login";
        }

        User user = (User) authentication.getPrincipal();

        model.addAttribute(
                "customer",
                customerService.findByIdAndUser(id, user)
        );

        return "customers/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(
            @PathVariable Long id,
            @ModelAttribute Customer customer,
            Authentication authentication
    ) {
        if (authentication == null) {
            return "redirect:/login";
        }

        User user = (User) authentication.getPrincipal();

        customer.setId(id);

        customer.setUser(user);

        customerService.save(customer);

        return "redirect:/customers";
    }

    @PostMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id
    ) {
        customerService.deleteById(id);
        return "redirect:/customers";
    }

}