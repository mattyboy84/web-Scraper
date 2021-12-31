package sample;

import org.jsoup.nodes.Element;

public class Product {

    String title;
    String price, strikePrice;
    String url;
    Element product;

    public Product(Element product, String title, String price, String url) {
        this.product = product;
        this.title = title;
        this.price = price;
        this.url = url;
    }

    public Product(String title, String price, String url) {


    }

    public Product(String title, String price, String strikePrice, String id) {
        this.title = title;
        this.price = price;
        this.strikePrice=strikePrice;
        this.url="https://facebook.com/marketplace/item/" + id;

    }

    public void output() {
        System.out.println("Title: " + this.title);
        System.out.print("Price: " + this.price);
        if (strikePrice!=null){
            System.out.print("  StrikePrice: " + this.strikePrice);
        }
        System.out.println("");
        System.out.println("URL: " + this.url);


    }
}
