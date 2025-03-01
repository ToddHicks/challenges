package main.models;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Deck {
    private String deckId;
    private boolean shuffled;
    private int remaining;

    // Add getters and setters

    // Factory method to create a Deck object from JSON
    public static Deck fromJson(String json) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);

        // Extract relevant information from the JSON response
        String deckId = rootNode.path("deck_id").asText();
        boolean shuffled = rootNode.path("shuffled").asBoolean();
        int remaining = rootNode.path("remaining").asInt();

        // Create and return a new Deck object
        Deck deck = new Deck();
        deck.setDeckId(deckId);
        deck.setShuffled(shuffled);
        deck.setRemaining(remaining);

        return deck;
    }

    public String getDeckId() {
        return deckId;
    }

    protected void setDeckId(String deckId) {
        this.deckId = deckId;
    }

    public boolean isShuffled() {
        return shuffled;
    }

    protected void setShuffled(boolean shuffled) {
        this.shuffled = shuffled;
    }

    public int getRemaining() {
        return remaining;
    }

    protected void setRemaining(int remaining) {
        this.remaining = remaining;
    }
    public String toString(){
        return "ID: " + getDeckId() + ", IS_SHUFFLED: " + isShuffled() + ", REMAINING: " + getRemaining();
    }
}
