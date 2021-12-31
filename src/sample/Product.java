package sample;

import org.jsoup.nodes.Element;

public class Product {

    String title;
    String price;
    String url;
    Element product;

    public Product(Element product, String title, String price, String url) {
        this.product = product;
        this.title = title;
        this.price = price;
        this.url = url;
    }

    public void output() {
        System.out.println("Title: " + this.title);
        System.out.println("Price: " + this.price);
        System.out.println("URL: " + this.url);


    }
}
