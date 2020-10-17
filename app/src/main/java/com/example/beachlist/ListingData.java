package com.example.beachlist;

public class ListingData {
    private int[] listingPhotos;
    String listingTitle, listingDescription, listingSoldTo, listingSoldDate, soldPrice, askingPrice;

    public ListingData(int[] photos, String title, String description, String askFor,
                       String soldFor, String soldTo, String soldDate){
        listingPhotos = photos;
        listingTitle = title;
        listingDescription = description;
        askingPrice = askFor;
        soldPrice = soldFor;
        listingSoldTo = soldTo;
        listingSoldDate = soldDate;
    }

    public String getListingSoldDate(){ return listingSoldDate; }

    public void setListingSoldDate(String listingSoldDate){ this.listingSoldDate = listingSoldDate; }

    public String getListingSoldTo() {
        return listingSoldTo;
    }

    public void setListingSoldTo(String listingSoldTo) {
        this.listingSoldTo = listingSoldTo;
    }

    public int[] getListingPhotos() {
        return listingPhotos;
    }

    public void setListingPhotos(int[] listingPhotos) {
        this.listingPhotos = listingPhotos;
    }

    public String getListingTitle() {
        return listingTitle;
    }

    public void setListingTitle(String listingTitle) {
        this.listingTitle = listingTitle;
    }

    public String getListingDescription() {
        return listingDescription;
    }

    public void setListingDescription(String listingDescription) {
        this.listingDescription = listingDescription;
    }

    public String getAskingPrice() {
        return askingPrice;
    }

    public void setAskingPrice(String askingPrice) {
        this.askingPrice = askingPrice;
    }

    public String getSoldPrice() {
        return soldPrice;
    }

    public void setSoldPrice(String soldPrice) {
        this.soldPrice = soldPrice;
    }
}
