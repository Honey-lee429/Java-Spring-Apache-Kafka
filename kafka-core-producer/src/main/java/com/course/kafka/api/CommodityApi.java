package com.course.kafka.api;

import com.course.kafka.entity.Commodity;
import com.course.kafka.repository.CommodityRepository;
import com.course.kafka.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/commodity/v1")
public class CommodityApi {
    @Autowired
    private CommodityRepository repository;
    @Autowired
    private CommodityService commodityService;

    @GetMapping(value = "/all")
    public List<Commodity> generateAllCommodities(){
        return repository.findAll();
    }
}
