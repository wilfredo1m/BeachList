package com.example.beachlist;

import java.util.ArrayList;

public class ListingData {
    //private int[] listingPhotos;
    ArrayList<String> listingImages;
    String title, category, description, price, postDate, ownerId;
    String sellDate, sellPrice, buyerId;
    Boolean isBanned;

    public ListingData() {
        listingImages = new ArrayList();

        title = " ";
        category = " ";
        description = " ";
        price = " ";
        postDate = " ";
        ownerId = " ";

        sellPrice = " ";
        sellDate = " ";
        buyerId = " ";

        isBanned = false;
    }

    // For specific listings
    public ListingData(ArrayList<String> listingImageUrl,
                       String listingTitle, String listingCategory, String listingDescription, String listingAskingPrice, String listingPostDate, String listingOwnerId,
                       String listingSellDate, String listingSellPrice, String listingBuyerId, Boolean listingIsBanned) {
        listingImages = listingImageUrl;

        title = listingTitle;
        category = listingCategory;
        description = listingDescription;
        price = listingAskingPrice;
        postDate = listingPostDate;
        ownerId = listingOwnerId;

        sellDate = listingSellDate;
        sellPrice = listingSellPrice;
        buyerId = listingBuyerId;

        isBanned = listingIsBanned;
    }

//    public ListingData(int[] photos, String title,String askFor){
//        listingPhotos = photos;
//        listingTitle = title;
//        askingPrice = askFor;
//    }

    public ArrayList<String> getListingImages() { return listingImages; }

    public void setListingImages(ArrayList<String> listingImages) { this.listingImages = listingImages; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getSellDate() {
        return sellDate;
    }

    public void setSellDate(String sellDate) {
        this.sellDate = sellDate;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public Boolean getBanned() {
        return isBanned;
    }

    public void setBanned(Boolean isBanned) {
        this.isBanned = isBanned;
    }
}
