package com.fortechcode.project.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class SiteController {
    @GetMapping("/")
    public String site() {
        return "Site/site";
    }
}

