package com.kuzmin.Project_i.controller;
import com.kuzmin.Project_i.model.Customer;
import com.kuzmin.Project_i.service.ImportService;
import com.kuzmin.Project_i.service.CustomerService;
import com.kuzmin.Project_i.service.RfmAnalyseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final ImportService importService;
    private final RfmAnalyseService rfmAnalyseService;

    public CustomerController(CustomerService customerService, ImportService importService, RfmAnalyseService rfmAnalyseService) {
        this.customerService = customerService;
        this.importService = importService;
        this.rfmAnalyseService = rfmAnalyseService;
    }

    @GetMapping("/import")
    public String importPage() {
        return "customers/import";
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute(
                "customers",
                customerService.findAll()
        );
        return "customers/list";
    }

    @GetMapping("/{id}")
    public String findById(
            @PathVariable Long id,
            Model model
    ) {
        model.addAttribute(
                "customer",
                customerService.findById(id)
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
            @RequestParam("file") MultipartFile file
    ) {
        importService.importCsv(file);
        rfmAnalyseService.runAnalysis();
        return "redirect:/customers";
    }

    @PostMapping("/create")
    public String create(
            @ModelAttribute Customer customer
    ) {
        customerService.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/edit/{id}")
    public String editPage(
            @PathVariable Long id,
            Model model
    ) {
        model.addAttribute(
                "customer",
                customerService.findById(id)
        );
        return "customers/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(
            @PathVariable Long id,
            @ModelAttribute Customer customer
    ) {
        customer.setId(id);
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