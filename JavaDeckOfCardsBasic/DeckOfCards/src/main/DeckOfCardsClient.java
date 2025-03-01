package main;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import main.models.Card;
import main.models.CreatePileResponse;
import main.models.Deck;

public class DeckOfCardsClient {
    private static final String BASE_URL = "https://deckofcardsapi.com/api/deck/";

    // Shuffle New Deck
    public Deck createShuffledDeck() throws Exception {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "new/shuffle/"))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return Deck.fromJson(response.body().toString());
    }

    //  New Deck
    public Deck createDeck() throws Exception {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "new/"))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return Deck.fromJson(response.body().toString());
    }

    public List<Card> drawCards(String deckId, int numCards) throws Exception {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + deckId + "/draw/?count=" + numCards))
                .build();
    
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    
        // Parse the JSON response and create a list of Card objects
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.body());
    
        List<Card> cards = Card.fromJson(rootNode.toString());
        return cards;
    }

    // Reshuffle Deck (remaining=true option too)
    public void reshuffleDeck(String deckId, boolean remainingOnly) throws IOException, InterruptedException{
        HttpClient httpClient = HttpClient.newHttpClient();
        String url = BASE_URL + deckId + "/shuffle/";
        if(remainingOnly){
            url = url + "?remaining=true";
        }
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        /*HttpResponse<String> response = */httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
    /**
     * By default, only shuffle remaining cards.
     * @throws InterruptedException
     * @throws IOException
     */
    public void reshuffleDeck(String deckId) throws IOException, InterruptedException{
        reshuffleDeck(deckId, true);
    }

    /**
     * 
     * @param cards list of cards to create a deck from.
     * @return
     * @throws Exception
     */
    // Create Partial Deck.
    public Deck createPartialDeck(List<Card> cards) throws Exception{
        String cardsString = convertCardsToString(cards);
        return createPartialDeck(cardsString);
    }
    // Create Partial Deck.
    /**
     *  https://deckofcardsapi.com/api/deck/new/shuffle/?cards=AS,2S,KS,AD,2D,KD,AC,2C,KC,AH,2H,KH
     * 
     * @param cards Comma separate list of card codes, no spaces.
     * @return
     * @throws Exception
     */
    public Deck createPartialDeck(String cards) throws Exception{
        HttpClient httpClient = HttpClient.newHttpClient();
        String url = BASE_URL + "/new/shuffle/?cards="+cards;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return Deck.fromJson(response.body().toString());
    }

    // Add cards to pile.
    //https://deckofcardsapi.com/api/deck/<<deck_id>>/pile/<<pile_name>>/add/?cards=AS,2S
    public CreatePileResponse addCardsToPile(String deckId, String pileName, String cards) throws IOException, InterruptedException{
        HttpClient httpClient = HttpClient.newHttpClient();
        String url = BASE_URL + "/"+deckId+"/pile/"+pileName+"/add/?cards="+cards;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return CreatePileResponse.fromJson(response.body().toString());
    }
    public CreatePileResponse addCardsToPile(String deckId, String pileName, List<Card> cards) throws IOException, InterruptedException{
        return addCardsToPile(deckId, pileName, convertCardsToString(cards));
    }

    // Shuffle Pile
    //https://deckofcardsapi.com/api/deck/<<deck_id>>/pile/<<pile_name>>/shuffle/
    public void shufflePile(String deckId, String pileName) throws IOException, InterruptedException{
        HttpClient httpClient = HttpClient.newHttpClient();
        String url = BASE_URL + "/"+deckId+"/pile/"+pileName+"/shuffle/";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    // Listing cards in piles
    // https://deckofcardsapi.com/api/deck/<<deck_id>>/pile/<<pile_name>>/list/
    public List<Card> listCardsInPile(String deckId, String pileName) throws Exception{
        HttpClient httpClient = HttpClient.newHttpClient();
        String url = BASE_URL + "/"+deckId+"/pile/"+pileName+"/list/";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.body());
        JsonNode node = rootNode.get("piles").get(pileName);
        return Card.fromJson(node.toString());
    }

    // Draw cards from pile. Count
    // https://deckofcardsapi.com/api/deck/<<deck_id>>/pile/<<pile_name>>/draw/??count=2
    public List<Card> drawCardsFromPile(String deckId, String pileName, int count) throws Exception{
        HttpClient httpClient = HttpClient.newHttpClient();
        String url = BASE_URL + "/"+deckId+"/pile/"+pileName+"/draw/count="+count;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return Card.fromJson(response.body().toString());
    }

    // Draw cards from pile. specific cards
    // https://deckofcardsapi.com/api/deck/<<deck_id>>/pile/<<pile_name>>/draw/?cards=AS
    public List<Card> drawCardsFromPile(String deckId, String pileName, String cards) throws Exception{
        HttpClient httpClient = HttpClient.newHttpClient();
        String url = BASE_URL + "/"+deckId+"/pile/"+pileName+"/draw/cards="+cards;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return Card.fromJson(response.body().toString());
    }
    public List<Card> drawCardsFromPile(String deckId, String pileName, List<Card> cards) throws Exception{
        return drawCardsFromPile(deckId, pileName, convertCardsToString(cards));
    }

    // Draw Random card from deck
    // https://deckofcardsapi.com/api/deck/<<deck_id>>/draw/random/
    public Card drawRandomCard(String deckId) throws Exception{
        HttpClient httpClient = HttpClient.newHttpClient();
        String url = BASE_URL + "/"+deckId+"/draw/random/";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return Card.fromJson(response.body().toString()).get(0);
    }

    // Draw bottom card from deck
    public Card drawBottomCard(String deckId) throws Exception{
        HttpClient httpClient = HttpClient.newHttpClient();
        String url = BASE_URL + "/"+deckId+"/draw/bottom/";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return Card.fromJson(response.body().toString()).get(0);
    }

    // Return all cards to deck
    // https://deckofcardsapi.com/api/deck/<<deck_id>>/return/
    public void returnAllCardsToDeck(String deckId) throws IOException, InterruptedException{
        HttpClient httpClient = HttpClient.newHttpClient();
        String url = BASE_URL + "/"+deckId+"/return/";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    // Return specific cards to deck
    // https://deckofcardsapi.com/api/deck/<<deck_id>>/return/?cards=AS,2S
    public void returnCardsToDeck(String deckId, String cards) throws IOException, InterruptedException{
        HttpClient httpClient = HttpClient.newHttpClient();
        String url = BASE_URL + "/"+deckId+"/return/?cards="+cards;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
    public void returnCardsToDeck(String deckId, List<Card> cards) throws IOException, InterruptedException{
        returnCardsToDeck(deckId, convertCardsToString(cards));
    }

    // Return pile to deck
    // https://deckofcardsapi.com/api/deck/<<deck_id>>/pile/<<pile_name>>/return/
    public void returnPileToDeck(String deckId, String pileName) throws IOException, InterruptedException{
        HttpClient httpClient = HttpClient.newHttpClient();
        String url = BASE_URL + "/"+deckId+"/pile/"+pileName+"/return/";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    // Return specifc cards from pile to deck
    // https://deckofcardsapi.com/api/deck/<<deck_id>>/pile/<<pile_name>>/return/?cards=AS,2S
    public void returnPileCardsToDeck(String deckId, String pileName, String cards) throws IOException, InterruptedException{
        HttpClient httpClient = HttpClient.newHttpClient();
        String url = BASE_URL + "/"+deckId+"/pile/"+pileName+"/return/?cards="+cards;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
    public void returnPileCardsToDeck(String deckId, String pileName, List<Card> cards) throws IOException, InterruptedException{
        returnPileCardsToDeck(deckId, pileName, convertCardsToString(cards));
    }

    private String convertCardsToString(List<Card> cards){
        String cardsString = null;
        for(Card c : cards){
            if (cardsString==null){
                cardsString = c.getCode();
            }
            else {
                cardsString = cardsString+","+c.getCode();
            }
        }
        return cardsString;
    }
}
