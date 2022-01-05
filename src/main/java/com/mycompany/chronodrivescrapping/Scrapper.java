/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chronodrivescrapping;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Scrapper {
    
    List<Article> articlesObjects = new ArrayList<Article>();
    
    public WebClient initiateClient() {
        WebClient client = new WebClient();
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setUseInsecureSSL(true);
        return client;
    }
    
    public List<Article> scrappePage(WebClient client, String url) throws IOException {
                
        try {
            HtmlPage page = client.getPage(url);
            
            List<HtmlElement> articles = (List<HtmlElement>) page.getByXPath("//article[contains(@class, ' item')]");
            List<HtmlElement> alreadyTakenArticles = new ArrayList<HtmlElement>();
            
            if (articles.isEmpty()) { System.out.println("Pas d'articles"); }
            
            else {
                
                for(int i = 0; i < 20; i++) {
                    
                    int random = generateRandom(articles.size());
                    
                    while(alreadyTakenArticles.contains(articles.get(random))) {
                        random = generateRandom(articles.size());
                    }
                    
                    HtmlElement article = articles.get(random);
                    alreadyTakenArticles.add(article);
                    
                    HtmlSpan spanPrice = article.getFirstByXPath(".//span[contains(@class, 'item-goodPrice')]");
                    String price = spanPrice.getTextContent().trim();
                    HtmlDivision divName = article.getFirstByXPath(".//div[contains(@class, 'item-desc')]");
                    String name = divName.getTextContent().trim();
                    HtmlSpan spanCapacity = article.getFirstByXPath(".//span[contains(@class, 'item-qtyCapacity')]");
                    String capacity = spanCapacity.getTextContent().trim();

                    HtmlAnchor linkToProductAnchor = article.getFirstByXPath(".//a[contains(@class, 'item-link open seoActionLink')]");
                    String linkToProduct = "https://www.chronodrive.com" + linkToProductAnchor.getHrefAttribute();
                    
                    HtmlPage productPage = client.getPage(linkToProduct);
                   
                    HtmlImage image = (HtmlImage) productPage.getFirstByXPath("//img[contains(@class, 'mainImgFa')]");
                    String imageUrl = image.getSrcAttribute();
                    
                    Article articleObject = new Article(name, price, capacity, linkToProduct, imageUrl);
                    articlesObjects.add(articleObject);
                }     
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        return articlesObjects;
    }
    
    public void printArticles() {
        for(Article article : articlesObjects) {
            article.print();
        }
    }
    
    private int generateRandom(int maxSize) {
        Random r = new Random();
        int low = 0;
        int high = maxSize - 1;
        int result = r.nextInt(high-low) + low;
        return result;
    }
}
