package pl.allegro.promo.geecon2015.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.allegro.promo.geecon2015.domain.Report;
import pl.allegro.promo.geecon2015.domain.ReportGenerator;
import pl.allegro.promo.geecon2015.domain.ReportRequest;

@Controller
@RequestMapping("/report")
public class ReportEndpoint {
    
    private final ReportGenerator reportGenerator;

    @Autowired
    public ReportEndpoint(ReportGenerator reportGenerator) {
        this.reportGenerator = reportGenerator;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Report generateReport(ReportRequest request) {
        return reportGenerator.generate(request);
    }
    
}
