package com.kawikaavilla.abodoandy.Models;

/**
 * Created by Owner on 4/13/2016.
 */
public class Apartment {
    private String propertyName;
    private String tileUrl;
    private String priceRange;
    private String bedRange;

    public Apartment(String propertyName, String tileUrl, String priceRange, String bedRange){
        this.propertyName = propertyName;
        this.tileUrl = tileUrl;
        this.priceRange = priceRange;
        this.bedRange = bedRange;
    }

    public String getPropertyName(){
        return propertyName;
    }

    public String getTileUrl(){
        return tileUrl;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public String getBedRange() {
        return bedRange;
    }
}
