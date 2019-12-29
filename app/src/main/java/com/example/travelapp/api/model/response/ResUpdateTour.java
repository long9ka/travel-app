package com.example.travelapp.api.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class ResUpdateTour {


    public class FacebookLogin {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("hostId")
        @Expose
        private String hostId;
        @SerializedName("avatar")
        @Expose
        private Object avatar;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("minCost")
        @Expose
        private Integer minCost;
        @SerializedName("maxCost")
        @Expose
        private Integer maxCost;
        @SerializedName("startDate")
        @Expose
        private Long startDate;
        @SerializedName("endDate")
        @Expose
        private Long endDate;
        @SerializedName("adults")
        @Expose
        private Integer adults;
        @SerializedName("childs")
        @Expose
        private Integer childs;
        @SerializedName("isPrivate")
        @Expose
        private Boolean isPrivate;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getHostId() {
            return hostId;
        }

        public void setHostId(String hostId) {
            this.hostId = hostId;
        }

        public Object getAvatar() {
            return avatar;
        }

        public void setAvatar(Object avatar) {
            this.avatar = avatar;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getMinCost() {
            return minCost;
        }

        public void setMinCost(Integer minCost) {
            this.minCost = minCost;
        }

        public Integer getMaxCost() {
            return maxCost;
        }

        public void setMaxCost(Integer maxCost) {
            this.maxCost = maxCost;
        }

        public Long getStartDate() {
            return startDate;
        }

        public void setStartDate(Long startDate) {
            this.startDate = startDate;
        }

        public Long getEndDate() {
            return endDate;
        }

        public void setEndDate(Long endDate) {
            this.endDate = endDate;
        }

        public Integer getAdults() {
            return adults;
        }

        public void setAdults(Integer adults) {
            this.adults = adults;
        }

        public Integer getChilds() {
            return childs;
        }

        public void setChilds(Integer childs) {
            this.childs = childs;
        }

        public Boolean getIsPrivate() {
            return isPrivate;
        }

        public void setIsPrivate(Boolean isPrivate) {
            this.isPrivate = isPrivate;
        }

    }


}
