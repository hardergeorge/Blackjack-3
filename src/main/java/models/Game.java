package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by armatasc on 3/9/16.
 */

public class Game {

    public boolean error;

    public int money;
    public int dealerTotal;
    public int playerTotal;
    public int doubleDownTotal;

    public java.util.List<Card> deck = new ArrayList<>();

    public java.util.List<java.util.List<Card>> cols = new ArrayList<>();

    // Start of GAME CLASS FUNCTIONS

    public Game() {
        money = 200;
        cols.add(new ArrayList<Card>());
        cols.add(new ArrayList<Card>());
        cols.add(new ArrayList<Card>());
        error = false;
    }

    public void buildDeck() {
        for (int i = 2; i < 15; i++) {
            deck.add(new Card(i, Suit.Clubs));
            deck.add(new Card(i, Suit.Hearts));
            deck.add(new Card(i, Suit.Diamonds));
            deck.add(new Card(i, Suit.Spades));
        }
    }

    public void shuffle() {
        long seed = System.nanoTime();
        Collections.shuffle(deck, new Random(seed));
    }

    public void dealHand() {
        if (money > 1) {
            money = money - 2;
            dealerTotal = 0;
            playerTotal = 0;
            doubleDownTotal = 0;

            // Dealer gets a single visible card
            cols.get(0).add(deck.get(deck.size() - 1));
            deck.remove(deck.size() - 1);
            cols.get(0).add(deck.get(deck.size() - 1));
            deck.remove(deck.size() - 1);

            // Player gets 2 cards
            cols.get(1).add(deck.get(deck.size() - 1));
            deck.remove(deck.size() - 1);
            cols.get(1).add(deck.get(deck.size() - 1));
            deck.remove(deck.size() - 1);

            updateTotals();

        } else {
            // USER CANNOT PLAY; must refresh game
        }
    }

    // Deals one card to a specific column, i.e. player, dealer, double down
    // Will
    public void dealOne (int column) {
        cols.get(column).add(deck.get(deck.size()-1));
        deck.remove(deck.size()-1);
    }

    public void updateTotals() {
        for (int i = 0; i < 3; i++) {
            int tempTotal = 0, t = 0;
            for (int j = 0; j < cols.get(i).size(); j++) {
                Card tempCard = getCard(i, j);
                t = tempCard.getValue();

                if (t < 11) {
                    tempTotal = tempTotal + t;
                } else if ((t > 10) && (t < 14)) {
                    tempTotal += 10;
                } else {
                    if (tempTotal > 10) {
                        tempTotal += 1;
                    } else {
                        tempTotal += 11;
                    }
                }
                switch (i) {
                    case 0:
                        dealerTotal = tempTotal;
                        break;
                    case 1:
                        playerTotal = tempTotal;
                        break;
                    case 2:
                        doubleDownTotal = tempTotal;
                        break;
                    default:
                        break;
                }
            }
        }
    }

    //customDeal to setup game for testing purposes
    public void customDeal(int c1, int c2, int c3) {
        cols.get(0).add(deck.get(c1));
        deck.remove(c1);
        cols.get(1).add(deck.get(c2));
        deck.remove(c2);
        cols.get(2).add(deck.get(c3));
        deck.remove(c3);
    }

    private Card getCard(int columnNumber, int j) {
        return this.cols.get(columnNumber).get(this.cols.get(columnNumber).size()-(1 + j));
    }

    public boolean colHasCards(int colNumber) {
        if(this.cols.get(colNumber).size()>0){
            return true;
        }
        return false;
    }

    // End of GAME CLASS FUNCTIONS
}
