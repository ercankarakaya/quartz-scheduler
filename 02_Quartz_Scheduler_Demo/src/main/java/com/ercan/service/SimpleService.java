package com.ercan.service;

import org.springframework.stereotype.Service;

@Service
public class SimpleService {

    public void getSimpleMethod(){
        System.out.println("Called SimpleService.getSimpleMethod().");
    }
}
