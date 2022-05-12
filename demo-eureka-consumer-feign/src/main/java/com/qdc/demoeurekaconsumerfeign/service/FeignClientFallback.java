package com.qdc.demoeurekaconsumerfeign.service;

public class FeignClientFallback implements UserClient{
    @Override
    public String hello() {
        return "";
    }

    @Override
    public String hello(int sleep_seconds) {
        return "Hi,容错保护机制启动";
    }
}
