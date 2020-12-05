package com.example.beachlist;

import android.text.format.Time;

public class SoldData {
    private String imageUrl;
    private String title;
    private Time soldDate;
    private Integer sellPrice;
    private String soldTo;

    SoldData() { }

    public SoldData(String imageUrl, String title, Time soldDate, Integer sellPrice, String soldTo) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.soldDate = soldDate;
        this.sellPrice = sellPrice;
        this.soldTo = soldTo;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Time getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(Time soldDate) {
        this.soldDate = soldDate;
    }

    public Integer getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Integer sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getSoldTo() {
        return soldTo;
    }

    public void setSoldTo(String soldTo) {
        this.soldTo = soldTo;
    }
}
