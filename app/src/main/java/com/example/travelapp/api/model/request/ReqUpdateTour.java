package com.example.travelapp.api.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReqUpdateTour {

//    @SerializedName("id")
//    @Expose
//    private String id;
//    @SerializedName("name")
//    @Expose
//    private String name;
//    @SerializedName("startDate")
//    @Expose
//    private String startDate;
//    @SerializedName("endDate")
//    @Expose
//    private String endDate;
//    @SerializedName("adults")
//    @Expose
//    private String adults;
//    @SerializedName("childs")
//    @Expose
//    private String childs;
//    @SerializedName("minCost")
//    @Expose
//    private String minCost;
//    @SerializedName("maxCost")
//    @Expose
//    private String maxCost;
//    @SerializedName("status")
//    @Expose
//    private String status;
//    @SerializedName("isPrivate")
//    @Expose
//    private String isPrivate;
//
//    public ReqUpdateTour(String id, String name, String startDate, String endDate, String adults, String childs, String minCost, String maxCost, String status, String isPrivate) {
//        this.id = id;
//        this.name = name;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.adults = adults;
//        this.childs = childs;
//        this.minCost = minCost;
//        this.maxCost = maxCost;
//        this.status = status;
//        this.isPrivate = isPrivate;
//    }


    private int id;
    private String name;
    private long startDate;
    private long endDate;
    private int adults;
    private int childs;
    private int minCost;
    private int maxCost;
    private boolean isPrivate;
    private int status;

    public ReqUpdateTour(int id, String name, long startDate, long endDate, int adults, int childs, int minCost, int maxCost, boolean isPrivate, int status) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.adults = adults;
        this.childs = childs;
        this.minCost = minCost;
        this.maxCost = maxCost;
        this.isPrivate = isPrivate;
        this.status = status;
    }
}

