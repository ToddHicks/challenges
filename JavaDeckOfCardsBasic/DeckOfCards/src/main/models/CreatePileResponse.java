package main.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CreatePileResponse {
    /**
     * 
    {
    "success": true,
    "deck_id": "3p40paa87x90",
    "remaining": 12,
    "piles": {
        "discard": {
            "remaining": 2
        }
    }
}
     */
    private boolean success;
    public boolean isSuccess() {
        return success;
    }

    private String deckId;
    public String getDeckId() {
        return deckId;
    }

    private int remaining;

    public int getRemaining() {
        return remaining;
    }

    public static CreatePileResponse fromJson(String json) throws JsonMappingException, JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);
        CreatePileResponse cpr = new CreatePileResponse();
        // Extract relevant information from the JSON response
        cpr.setSuccess(rootNode.path("cards").asText());
        cpr.setDeckId(rootNode.path("deck_id").asText());
        cpr.setRemaining(rootNode.path("remaining").asText());
        return cpr;
    }

    protected void setRemaining(String path) {
        remaining = Integer.parseInt(path);
    }

    protected void setDeckId(String path) {
        deckId = path;
    }

    protected void setSuccess(String path) {
        success = Boolean.parseBoolean(path);
    }

    public String toString(){
        return "Remaining: " + remaining + ", DeckId: " + deckId + ", Success: " + success; 
    }
}
