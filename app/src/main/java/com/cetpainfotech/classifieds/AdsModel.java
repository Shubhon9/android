package com.cetpainfotech.classifieds;

/**
 * Created by ganeshbisht on 25/02/18.
 */

public class AdsModel {

    private String id;
    //private String mainImage;
    private String title;
    private String tags;
    private String date;
    private String price;
    private String description;



    public AdsModel(String id,  String title,
                    String tags, String date, String price, String description) {
        this.id = id;
        //this.mainImage = mainImage;
        this.title = title;
        this.tags = tags;
        this.date = date;
        this.description = description;
        this.price= price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public String getMainImage() {
//        return mainImage;
//    }
//
//    public void setMainImage(String mainImage) {
//        this.mainImage = mainImage;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
