/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package fpt.aptech.Server.controller;

import fpt.aptech.Server.service.VisitCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api/visit")
public class VisitCountController {
     @Autowired
    private VisitCounterService visitCounterService;
    @PostMapping("/increment")
    public void incrementVisitCount() {
        visitCounterService.incrementVisitCount();
    }

    @GetMapping("/count")
    public int getVisitCount() {
        return visitCounterService.getVisitCount();
    }
}
