package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by armatasc on 3/9/16.
 */

public class Game {

    static int BET_SIZE = 2;
    static int DEALER_COL = 0;
    static int PLAYER_COL = 1;
    static int SPLIT_COL = 2;

    public boolean error;
    public boolean hasSplit;
    public boolean dealerDone;
    public boolean playerWon;
    public boolean dealerWon;

    public int money;
    public int dealerTotal;
    public int playerTotal;
    public int splitTotal;
    public int dealTo;

    public java.util.List<Card> deck = new ArrayList<>();

    public java.util.List<java.util.List<Card>> cols = new ArrayList<>();

    // Start of GAME CLASS FUNCTIONS

    public Game() {
        money = 100;
        dealTo = 1;

        cols.add(new ArrayList<Card>());
        cols.add(new ArrayList<Card>());
        cols.add(new ArrayList<Card>());

        error = false;
        hasSplit = false;
        dealerDone =false;
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
            money = money - BET_SIZE;
            dealerTotal = 0;
            playerTotal = 0;
            splitTotal = 0;

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
            

        } else {
            error = true;
        }
    }

    // Deals one card to a specific column, i.e. player, dealer, double down
    // Will
    public void dealOne (int column) {
        cols.get(column).add(deck.get(deck.size()-1));
        deck.remove(deck.size()-1);
        error = false;

        determineOver21();
    }

    public void doubleDown () {
        if (cols.get(PLAYER_COL).size() == 2 && noWinner()) {
            dealOne(1);
            money = money - BET_SIZE;
            error = false;
            dealerPlay();
            winner();
        }
        else {
            error = true;
        }
    }

    public void split () {
        if (cols.get(PLAYER_COL).size() == 2 && noWinner()) {
            if (cols.get(PLAYER_COL).get(0).getValue() == cols.get(PLAYER_COL).get(1).getValue()) {
                hasSplit = true;
                cols.get(SPLIT_COL).add(cols.get(PLAYER_COL).get(1));
                cols.get(PLAYER_COL).remove(1);
                money = money - BET_SIZE;
                error = false;
            }
            else {
                error = true;
            }
        }
        else {
            error = true;
        }
    }

    public void dealerPlay () {

        while (true) {
            calculateTotals();

            if (dealerTotal < 17) {
                dealOne(DEALER_COL);
            }
            else {
                dealerDone = true;
                break;
            }
        }
    }

    //small helper to update who is being dealt to, player or split
    public void updateDealTo () {
        dealTo = 2;
    }

    public void winner () {
        calculateTotals();

        if (playerTotal > dealerTotal) {
            playerWon = true;
            dealerWon = false;
            money = money + 4;
        }
        else {
            dealerWon = true;
            playerWon = false;
        }

        if (hasSplit && splitTotal > dealerTotal) {
            playerWon = true;
            dealerWon = false;
            money = money + 4;
        }
    }

    public void calculateTotals() {
        Card tempCard;
        int tempTotal, currValue;
        
        for (int i = 0; i < 3; i++) {
            tempTotal = 0;

            for (int j = 0; j < cols.get(i).size(); j++) {
               
                tempCard = getCard(i, j);
                currValue = tempCard.getValue();

                //handle regular cards
                if (currValue < 11) {
                    tempTotal = tempTotal + currValue;
                } 
                //handle royal cards
                else if ((10 < currValue) && (currValue < 14)) {
                    tempTotal += 10;
                } 
                //handle aces
                else {
                    //don't bust
                    if (tempTotal > 10) {
                        tempTotal += 1;
                    }
                    //add high ace if we don't bust
                    else {
                        tempTotal += 11;
                    }
                }
                
                //update the appropriate total
                switch (i) {
                    case 0:
                        dealerTotal = tempTotal;
                        break;
                    case 1:
                        playerTotal = tempTotal;
                        break;
                    case 2:
                        splitTotal = tempTotal;
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

    private Card getCard(int columnNumber, int rowNumber) {
        return this.cols.get(columnNumber).get(rowNumber);
    }

    public void determineOver21 () {
        calculateTotals();

        if (dealerTotal > 21) {
            dealerWon = false;
            playerWon = true;
        }
        if (playerTotal > 21) {
            dealerWon = true;
            playerWon = false;
        }
    }

    public boolean colHasCards(int colNumber) {
        if(this.cols.get(colNumber).size()>0){
            return true;
        }
        return false;
    }

    public boolean noWinner () {
        if (!playerWon && !dealerWon) {
            return true;
        }
        else {
            return false;
        }
    }

    // End of GAME CLASS FUNCTIONS
}
