/// <reference types="cypress" />

const { checkersBoard } = require("../support/objects/checkersBoard")

describe('Checkers Tests', () => {
    it('Exploratory Test', () => {
        const boardLayout = []
        cy.visit('/')
        checkersBoard.getSpace(0, 2).click()
        checkersBoard.getSpace(1, 3).click()
        checkersBoard.waitForComputer()
        checkersBoard.getSpaces().then(spaces => {
            console.log(spaces)
            console.log(`There are ${spaces.length} items in spaces.`)
            const yous = []
            const mes = []
            for (let space = 0; space < spaces.length; space++) {
                console.log(spaces[space])
                console.log(spaces[space].src)
                if (spaces[space].src.includes('you1.gif'))
                    yous.push(spaces[space])
                else if (spaces[space].src.includes('me1.gif'))
                    mes.push(spaces[space])
            }
            console.log(`Yous! ${yous.length}`)
            for (let c = 0; c < yous.length; c++) {
                console.log(yous[c])
                let space = yous[c].name
                let x = space.at(space.length - 2)
                let y = space.at(space.length - 1)
                console.log(x, y)
            }
            console.log(`Mes! ${mes.length}`)
            for (let c = 0; c < mes.length; c++) {
                console.log(mes[c])
                let space = mes[c].name
                let x = space.at(space.length - 2)
                let y = space.at(space.length - 1)
                console.log(x, y)
            }
        })
        console.log('My Spaces')
        checkersBoard.getMySpaces().then(spaces => {
            console.log(spaces)
        })
        console.log('Your Spaces')
        checkersBoard.getYourSpaces().then(spaces => {
            console.log(spaces)
        })

    })
})