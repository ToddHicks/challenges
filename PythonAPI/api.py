import requests

class DeckOfCardsAPI():
    root_url = 'https://deckofcardsapi.com/api/'
    def __create_deck_list(self, cards):
        card_list = None
        for card in cards:
            if card_list is None:
                card_list = card
            else:
                card_list = card_list + ',' + card
        return card_list
    def shuffle_new_deck(self, deck_count):
        if deck_count is None:
            deck_count = 1
        response = requests.post(self.root_url+'deck/new/shuffle/', data = {'deck_count': deck_count})
        return response
    def draw_cards(self, deck_id, card_count):
        response = requests.post(self.root_url+'deck/'+deck_id+'/draw/', data = {'count': card_count})
        return response
    def reshuffle_deck(self, deck_id):
        response = requests.post(self.root_url+'deck/'+deck_id+'/shuffle/')
        return response
    def reshuffle_remaining_deck(self, deck_id):
        response = requests.post(self.root_url+'deck/'+deck_id+'/shuffle/', data = {'remaining': True})
        return response
    def create_new_deck(self, add_jokers):
        if add_jokers is not None and add_jokers is not False:
            args = {'jokers_enabled': True}
        else:
            args = {}
        response = requests.post(self.root_url+'deck/new/', args)
        return response
    def create_partial_deck(self, cards):
        card_list = self.__create_deck_list(cards)
        response = requests.post(self.root_url+'deck/new/shuffle/', data = {'cards': card_list})
        return response
    def add_to_pile(self, deck_id, pile_name, cards):
        card_list = self.__create_deck_list(cards)
        response = requests.post(self.root_url+'deck/'+deck_id+'/pile/'+pile_name+'/add/', data = {'cards':card_list})
        return response
    def shuffle_piles(self, deck_id, pile_name):
        response = requests.post(self.root_url+'deck/'+deck_id+'/pile/'+pile_name+'/shuffle/')
        return response
    def list_cards_pile(self, deck_id, pile_name):
        response = requests.post(self.root_url+'deck/'+deck_id+'/pile/'+pile_name+'/list/')
        return response
    def draw_from_pile_specific_cards(self, deck_id, pile_name, cards):
        card_list = self.__create_deck_list(cards)
        response = requests.post(self.root_url+'deck/'+deck_id+'/pile/'+pile_name+'/draw/', data = {'cards':card_list})
        return response
    def draw_from_pile_count(self, deck_id, pile_name, card_count):
        response = requests.post(self.root_url+'deck/'+deck_id+'/pile/'+pile_name+'/draw/', data = {'count': card_count})
        return response
    def draw_from_pile_bottom(self, deck_id, pile_name, card_count):
        response = requests.post(self.root_url+'deck/'+deck_id+'/pile/'+pile_name+'/draw/bottom/', data = {'count': card_count})
        return response
    def draw_from_pile_random(self, deck_id, pile_name, card_count):
        response = requests.post(self.root_url+'deck/'+deck_id+'/pile/'+pile_name+'/draw/random/', data = {'count': card_count})
        return response
    def return_all_cards_to_deck(self, deck_id):
        response = requests.post(self.root_url+'deck/'+deck_id+'/return/')
        return response
    def return_pile_to_deck(self, deck_id, pile_name):
        response = requests.post(self.root_url+'deck/'+deck_id+'/pile/'+pile_name+'/return/')
        return response
    def return_cards_to_deck(self, deck_id, cards):
        card_list = self.__create_deck_list(cards)
        response = requests.post(self.root_url+'deck/'+deck_id+'/return/', data = {'cards': card_list})
        return response
    def return_cards_from_pile_to_deck(self, deck_id, pile_name, cards):
        card_list = self.__create_deck_list(cards)
        response = requests.post(self.root_url+'deck/'+deck_id+'/pile/'+pile_name+'/return/', data = {'cards': card_list})
        return response

