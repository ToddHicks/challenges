package main.models;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Card {
    private String code;
    public String getCode() {
        return code;
    }
    private String image;
    public String getImage() {
        return image;
    }
    private Images images;
    public Images getImages() {
        return images;
    }
    private String value;
    public String getValue() {
        return value;
    }
    private String suit;

    // Add getters and setters

    public String getSuit() {
        return suit;
    }
    // Inner class for 'images'
    public static class Images {
        private String svg;
        public String getSvg() {
            return svg;
        }
        private String png;
        public String getPng() {
            return png;
        }
        public void setSvg(String asText) {
            svg = asText;
        }
        public void setPng(String asText) {
            png = asText;
        }
    }

    public static List<Card> fromJson(String json) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);

        // Extract relevant information from the JSON response
        JsonNode cardsNode = rootNode.path("cards");
        if (cardsNode.isArray() && cardsNode.size() > 0) {
            List<Card> cards = new LinkedList<Card>();
            for(int index = 0; index < cardsNode.size(); ++index){
                JsonNode cardNode = cardsNode.get(index);

                Card card = new Card();
                card.setCode(cardNode.path("code").asText());
                card.setImage(cardNode.path("image").asText());
                card.setValue(cardNode.path("value").asText());
                card.setSuit(cardNode.path("suit").asText());

                Images images = new Images();
                images.setSvg(cardNode.path("images").path("svg").asText());
                images.setPng(cardNode.path("images").path("png").asText());
                card.setImages(images);
                cards.add(card);
            }

            return cards;
        }

        return null; // Handle if the cards array is empty
    }
    private void setSuit(String asText) {
        suit = asText;
    }
    private void setValue(String asText) {
        value = asText;
    }
    private void setImage(String asText) {
        image = asText;
    }
    protected void setImages(Images images) {
        this.images = images;
    }
    protected void setCode(String code){
        this.code = code;
    }
    public String toString(){
        return "CODE: " + getCode() + ", IMAGE: " + getImage() + ", SUIT: " + getSuit();
    }
}