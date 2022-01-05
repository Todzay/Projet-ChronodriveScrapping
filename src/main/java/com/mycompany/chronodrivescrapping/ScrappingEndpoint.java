/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chronodrivescrapping;

import com.gargoylesoftware.htmlunit.WebClient;
import java.io.IOException;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.OnClose;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.List;

@ServerEndpoint("/ScrappingEndpoint")
public class ScrappingEndpoint {

    @OnMessage
    public void onMessage(String message, Session session) throws IOException, EncodeException {
        System.out.println("Message reçu : " + message);
                
        Scrapper scrapper = new Scrapper();
        WebClient client = scrapper.initiateClient();
        client.getCookieManager().clearCookies();
        List<Article> articles = scrapper.scrappePage(client, "https://www.chronodrive.com/bio-ecolo/frais-et-surgeles-bio-R13845364");
                
        for (Article article : articles) {
            JsonObject jsonArticle = Json.createObjectBuilder()
                .add("name", article.getName())
                .add("price", article.getPrice())
                .add("capacity", article.getCapacity())
                .add("link", article.getLink())
                .add("image", article.getImage())
                .build();
            session.getBasicRemote().sendObject(jsonArticle);
        }
    }
    
    @OnOpen
    public void onOpen() {
        System.out.println("Client connecté ...");
    }
    
    @OnClose
    public void onClose() {
        System.out.println("Client déconnecté ...");
    }
} 
