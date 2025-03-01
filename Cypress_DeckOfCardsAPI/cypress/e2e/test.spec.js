/// <reference types="cypress" />

import { deckOfCards } from "../support/objects/deckOfCards"

describe('Test', () => {
    it('Test',()=>{
        deckOfCards.shuffleNewDeck()
        cy.get('@deckId').then(deckId => {
            console.log(deckId)
            deckOfCards.drawCards(deckId, 3, 'drawnCards')
        })
        cy.get('@drawnCards').then(drawnCards =>{
            console.log(drawnCards)
            for( let card in drawnCards){
                console.log(card)
                console.log(drawnCards[card])
            }
        })
        // works, but doesn't align with the scenario.
        //deckOfCards.newPartialDeck(['AS','AC','AD','AH'])
    })
})