from api import DeckOfCardsAPI
import pytest

def test_shuffle():
    deck_of_cards_api = DeckOfCardsAPI()
    deck_call = deck_of_cards_api.shuffle_new_deck(1).json() 
    assert deck_call['success'] is True
    deck_id = deck_call['deck_id']
    assert deck_id is not None
    assert deck_call['remaining'] == 52
    assert deck_call['shuffled'] is True
    cards_call = deck_of_cards_api.draw_cards(deck_id, 5).json()
    assert cards_call['success'] is True
    assert cards_call['remaining'] == 47
    new_deck = deck_of_cards_api.create_new_deck(True).json()
    assert new_deck['remaining'] == 54
    new_deck_no_jokers = deck_of_cards_api.create_new_deck(False).json()
    assert new_deck_no_jokers['remaining'] == 52