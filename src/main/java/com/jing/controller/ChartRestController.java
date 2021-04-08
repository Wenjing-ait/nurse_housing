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
            String gender = request.getParameter("gender");
            int year = RConnectionUtils.expectancyAge(expectancyAge,gender);
            userExpectancy.setYear(year);
        } catch (RserveException e) {
            userExpectancy.setMsg("Error expectancyAge");
            e.printStackTrace();
        }
        return userExpectancy;
    }

    @RequestMapping("/generateAvgLifeYear")
    public Object generateAvgLifeYear(Model model,HttpServletRequest request) {
        String gender = request.getParameter("gender");
        UserExpectancy userExpectancy = new UserExpectancy();
        try {
            String fileName = RConnectionUtils.avgLife_year(gender);
            userExpectancy.setFileName(fileName);
        } catch (RserveException e) {
            e.printStackTrace();
            userExpectancy.setMsg("Error generateAvgLifeYear");
        }
        return userExpectancy;
    }

    @RequestMapping("/generateLifeExpectancy")
    public Object generateLifeExpectancy(Model model, HttpServletRequest request) {
        UserExpectancy userExpectancy = new UserExpectancy();
        try {
            String gender = request.getParameter("gender");
            String year = request.getParameter("year");
            String fileName = RConnectionUtils.lifeExpectancy(gender, year);
            userExpectancy.setFileName(fileName);
        } catch (RserveException e) {
            e.printStackTrace();
            userExpectancy.setMsg("Error generateLifeExpectancy");
        }
        return userExpectancy;
    }

}
