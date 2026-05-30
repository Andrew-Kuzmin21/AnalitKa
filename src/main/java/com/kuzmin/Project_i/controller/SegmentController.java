package com.kuzmin.Project_i.controller;
import com.kuzmin.Project_i.model.Segment;
import com.kuzmin.Project_i.model.User;
import com.kuzmin.Project_i.service.SegmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/segments")
public class SegmentController {
    private final SegmentService segmentService;

    public SegmentController(SegmentService segmentService) {
        this.segmentService = segmentService;
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute(
                "segments",
                segmentService.findAll()
        );
        return "segments/list";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute(
                "segment",
                new Segment()
        );
        return "segments/create";
    }

    @PostMapping("/create")
    public String create(
            @ModelAttribute Segment segment,
            User user
    ) {
        segment.setUser(user);

        segmentService.save(segment);

        return "redirect:/segments";
    }
}