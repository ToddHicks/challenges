const baseUrl = "https://deckofcardsapi.com/api";
export class DeckOfCards {
  shuffleNewDeck() {
    cy.request("GET", baseUrl + "/deck/new/shuffle/?deck_count=1")
      .its("body")
      .then((body) => {
        console.log(body);
        expect(body.success).to.equal(true);
        expect(body.shuffled).to.equal(true);
        cy.wrap(body.deck_id).as("deckId");
      });
  }
  newDeck(jokersEnabled) {
    cy.request("GET", baseUrl + "/deck/new?jokers_enabled=" + jokersEnabled)
      .its("body")
      .then((body) => {
        console.log(body);
        expect(body.success).to.equal(true);
        expect(body.shuffled).to.equal(false);
        cy.wrap(body.deck_id).as("deckId");
      });
  }
  drawCards(deckId, cardCount, wrapString) {
    cy.request("GET", baseUrl + "/deck/" + deckId + "/draw/?count=" + cardCount)
      .its("body")
      .then((body) => {
        console.log(body);
        expect(body.success).to.equal(true);
        expect(body.cards).to.have.length(cardCount);
        cy.wrap(body.cards).as(wrapString);
      });
  }
  shuffleDeck(deckId) {
    cy.request("GET", baseUrl + "/deck/" + deckId + "/shuffle/?remaining=true")
      .its("body")
      .then((body) => {
        expect(body.shuffled).to.equal(true);
        expect(body.success).to.equal(true);
      });
  }
  // https://deckofcardsapi.com/api/deck/new/shuffle/?cards=AS,2S,KS,AD,2D,KD,AC,2C,KC,AH,2H,KH
  newPartialDeck(cards) {
    let cardList = "";
    for (let c in cards) {
      if (cardList === "") {
        cardList = cardList + cards[c];
      } else {
        cardList = cardList + "," + cards[c];
      }
    }
    cy.request("GET", baseUrl + "/deck/new/shuffle/?cards=" + cardList)
      .its("body")
      .then((body) => {
        console.log(body);
        expect(body.success).to.equal(true);
        expect(body.shuffled).to.equal(true);
        cy.wrap(body.deck_id).as("deckId");
      });
  }
}
export const deckOfCards = new DeckOfCards();
