package main;

import java.util.List;

import main.models.Card;
import main.models.CreatePileResponse;
import main.models.Deck;

public class DeckOfCardsTest {
    public static void main(String args[]) throws Exception{
        DeckOfCardsClient client = new DeckOfCardsClient();
        Deck deck = client.createDeck();
        System.out.println(deck.toString());
        String deckId = deck.getDeckId();
        client.reshuffleDeck(deckId);
        List<Card> cards = client.drawCards(deckId, 4);
        System.out.println(cards);
        String pileName = "NewPile";
        CreatePileResponse cpr1 = client.addCardsToPile(deckId, pileName, cards);
        System.out.println("Pile: " + cpr1);
        List<Card> cards2 = client.listCardsInPile(deckId,pileName);
        System.out.println("Cards: " + cards2);
        for( Card c : cards2){
            if(c.getSuit().equals("SPADES")){
                System.out.println("SPADE!!!!");
            }
            else{
                System.out.println("Not a Spade.");
            }
            System.out.println("Card: " + c);
        }
    }
}
