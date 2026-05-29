package com.kuzmin.Project_i.controller;
import com.kuzmin.Project_i.service.AbcAnalyseService;
import com.kuzmin.Project_i.service.RfmAnalyseService;
import com.kuzmin.Project_i.service.XyzAnalyseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/analytics")
public class AnalyticsController {
    private final AbcAnalyseService abcAnalyseService;
    private final XyzAnalyseService xyzAnalyseService;
    private final RfmAnalyseService rfmAnalyseService;
    public AnalyticsController(
            AbcAnalyseService abcAnalyseService,
            XyzAnalyseService xyzAnalyseService,
            RfmAnalyseService rfmAnalyseService
    ) {
        this.abcAnalyseService = abcAnalyseService;
        this.xyzAnalyseService = xyzAnalyseService;
        this.rfmAnalyseService = rfmAnalyseService;
    }

    @GetMapping("/abc")
    public String abcAnalysis(Model model) {
        model.addAttribute(
                "abcAnalysis",
                abcAnalyseService.findAll()
        );
        return "analytics/abc";
    }

    @GetMapping("/xyz")
    public String xyzAnalysis(Model model) {
        model.addAttribute(
                "xyzAnalysis",
                xyzAnalyseService.findAll()
        );
        return "analytics/xyz";
    }

    @GetMapping("/rfm")
    public String rfmAnalysis(Model model) {
        model.addAttribute(
                "rfmAnalysis",
                rfmAnalyseService.findAll()
        );
        return "analytics/rfm";
    }
}