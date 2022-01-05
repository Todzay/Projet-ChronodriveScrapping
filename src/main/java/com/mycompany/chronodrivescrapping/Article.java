package com.mycompany.chronodrivescrapping;

public class Article {
    
    private String name;
    private String price;
    private String capacity;
    private String link;
    private String image; 
    
    public Article (String name, String price, String capacity, String link, String image) {
        this.name = name;
        this.price = price;
        this.capacity = capacity;
        this.link = link;
        this.image = image;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPrice() {
        return this.price;
    }
    
    public void setPrice(String price) {
        this.price = price;
    }

    public String getCapacity() {
        return this.capacity;
    }
    
    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getLink() {
        return this.link;
    }
    
    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return this.image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }    
    
    public void print() {
        System.out.println("Name : " + name);
        System.out.println("Price : " + price);
        System.out.println("Capacity : " + capacity);
        System.out.println("Link to product : " + link);
        System.out.println("Image : " + image);
        System.out.println("-------------------------------------------------------------");
    }
}
