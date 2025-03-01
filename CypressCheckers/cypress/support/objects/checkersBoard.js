export class CheckerBoard {
    getBoard() { return cy.get('[id="board"]') }
    getSpaces() { return this.getBoard().find('img') }
    getSpace(column, row) { return this.getBoard().find('[name="space' + column + row + '"]') }
    waitForComputer() {
        this.getBoard().find('[src="https://www.gamesforthebrain.com/game/checkers/me2.gif"]').should('exist')
        this.getBoard().find('[src="https://www.gamesforthebrain.com/game/checkers/me2.gif"]').should('not.exist')
    }
    getMySpaces() { return this.getSpaces().filter('[src$="me1.gif"]') } // $ ends with, * anywhere, ^ begins with
    getYourSpaces() { return this.getSpaces().filter('[src$="you1.gif"]') }
}
export const checkersBoard = new CheckerBoard()