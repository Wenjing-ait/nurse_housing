package com.jing.controller;


import com.jing.pojo.UserExpectancy;
import org.rosuda.REngine.Rserve.RserveException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.RConnectionUtils;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ChartRestController {

    @RequestMapping("/expectancyAge")
    public Object expectancyAge(Model model, HttpServletRequest request) {
        UserExpectancy userExpectancy = new UserExpectancy();
        try {
            int expectancyAge = Integer.parseInt(request.getParameter("expectancyAge"));
            int year = RConnectionUtils.expectancyAge(expectancyAge);
            userExpectancy.setYear(year);
        } catch (RserveException e) {
            userExpectancy.setMsg("Error expectancyAge");
            e.printStackTrace();
        }
        return userExpectancy;
    }

    @RequestMapping("/generateAvgLifeYear")
    public Object generateAvgLifeYear(Model model) {
        UserExpectancy userExpectancy = new UserExpectancy();
        try {
            RConnectionUtils.avgLife_year(null);
        } catch (RserveException e) {
            e.printStackTrace();
            userExpectancy.setMsg("Error generateAvgLifeYear");
        }
        return userExpectancy;
    }

}
