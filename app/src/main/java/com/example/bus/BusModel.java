package com.example.bus;

import java.util.List;

public class BusModel {
    private String routeName;
    private String busName;
    private String imgUrl;
    private List<BusDetailsModel> busDetails;

    public BusModel(String routeName, String busName, String imgUrl, List<BusDetailsModel> busDetails) {
        this.routeName = routeName;
        this.busName = busName;
        this.imgUrl = imgUrl;
        this.busDetails = busDetails;
    }

    public String getRouteName() {
        return routeName;
    }

    public String getBusName() {
        return busName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public List<BusDetailsModel> getBusDetails() {
        return busDetails;
    }
}
