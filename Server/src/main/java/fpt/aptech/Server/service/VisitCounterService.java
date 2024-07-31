/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.Server.service;

import fpt.aptech.Server.entities.VisitCounter;
import fpt.aptech.Server.repository.VisitCounterRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class VisitCounterService {
    @Autowired
    VisitCounterRepository visitCounterRepository;
    
     public void incrementVisitCount() {
    VisitCounter visitCounter = visitCounterRepository.findById(1).orElse(new VisitCounter());
    if (visitCounter.getVisitCount() == null) {
        visitCounter.setVisitCount(0); // Khởi tạo giá trị nếu null
    }
    visitCounter.setVisitCount(visitCounter.getVisitCount() + 1);
    visitCounterRepository.save(visitCounter);
}

    public Integer getVisitCount() {
    VisitCounter visitCounter = visitCounterRepository.findById(1).orElse(new VisitCounter());
    return visitCounter.getVisitCount() != null ? visitCounter.getVisitCount() : 0;
}
}
