package com.example.bus;

public class BusDetailsModel {
    private String counterName;
    private String counterNumber;
    private String imgUrl;

    public BusDetailsModel(String counterName, String counterNumber, String imgUrl) {
        this.counterName = counterName;
        this.counterNumber = counterNumber;
        this.imgUrl = imgUrl;
    }

    public String getCounterName() {
        return counterName;
    }

    public String getCounterNumber() {
        return counterNumber;
    }

    public String getImgUrl() {
        return imgUrl;
    }

} // BusDetailsModel end here ==========
