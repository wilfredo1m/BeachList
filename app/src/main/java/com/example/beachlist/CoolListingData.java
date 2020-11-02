package com.example.beachlist;

public class CoolListingData {
    //private int[] listingPhotos;
    String listingImages;
    String title, category, description, price, postDate, ownerId;
    String sellDate, sellPrice, buyerId;


    public CoolListingData() {
        listingImages = " ";

        title = " ";
        category = " ";
        description = " ";
        price = " ";
        postDate = " ";
        ownerId = " ";

        sellPrice = " ";
        sellDate = " ";
        buyerId = " ";
    }

    // For specific listings
    public CoolListingData(String listingImageUrl,
                       String listingTitle, String listingCategory, String listingDescription, String listingAskingPrice, String listingPostDate, String listingOwnerId,
                       String listingSellDate, String listingSellPrice, String listingBuyerId) {
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
    }

//    public ListingData(int[] photos, String title,String askFor){
//        listingPhotos = photos;
//        listingTitle = title;
//        askingPrice = askFor;
//    }

    public String getListingImages() { return listingImages; }

    public void setListingImages(String listingImages) { this.listingImages = listingImages; }

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
}
