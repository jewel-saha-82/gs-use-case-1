package com.stocks.processor.controller;

import com.stocks.processor.model.TopGainerLooserData;
import com.stocks.processor.service.TopGainerLooserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StocksGainerLooserController {


    @Autowired
    private TopGainerLooserServiceImpl service;

    @GetMapping("/stocksresult")
    public TopGainerLooserData getTopGainerLooserStock(){
        return service.getTopGainerLooserStocks();
    }

}
