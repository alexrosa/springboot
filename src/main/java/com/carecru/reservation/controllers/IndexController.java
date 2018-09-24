package com.carecru.reservation.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping(path ="/")
    public String home(){
        return "redirect:/swagger-ui.html";
    }
}
