package models;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by armatasc on 3/9/16.
 */
public class GameTest {

    @Test
    public void testGameCreation() {
        Game g = new Game();
        assertNotNull(g);
    }

    @Test
    public void testGameBuildDeck() {
        Game g = new Game();
        g.buildDeck();
        assertEquals(52, g.deck.size());
    }

    @Test
    public void testGameInit(){
        Game g = new Game();
        g.buildDeck();
        g.shuffle();
        assertEquals(100, g.money);
        assertEquals(1, g.dealTo);

        assertFalse(g.error);
        assertFalse(g.hasSplit);
        assertFalse(g.dealerDone);
    }

    @Test
    public void testGameStart(){
        Game g = new Game();
        g.buildDeck();
        g.shuffle();
        g.dealHand();
        assertEquals(2,g.cols.get(0).size());
        assertEquals(2,g.cols.get(1).size());
        assertEquals(0,g.cols.get(2).size());
    }

    @Test
    public void testDealHand(){
        Game g = new Game();
        g.buildDeck();
        g.shuffle();
        g.dealHand();
        assertTrue(g.cols.get(0).size() == 2);
        assertTrue(g.cols.get(1).size() == 2);
        assertTrue(g.cols.get(2).size() == 0);
    }

    @Test
    public void testCustomDeal(){
        Game g = new Game();
        g.buildDeck();
        g.customDeal(0, 3, 6);
        assertEquals("2Clubs",g.cols.get(0).get(0).toString());
        assertEquals("3Clubs",g.cols.get(1).get(0).toString());
        assertEquals("4Clubs",g.cols.get(2).get(0).toString());
       }

    @Test
    public void testDealAces(){
        Game g = new Game();
        g.buildDeck();
        g.customDeal(48, 48, 48);
        assertEquals("14Clubs",g.cols.get(0).get(0).toString());
        assertEquals("14Hearts",g.cols.get(1).get(0).toString());
        assertEquals("14Diamonds",g.cols.get(2).get(0).toString());
    }

    @Test
    public void testDealOne () {
        Game g = new Game();
        g.buildDeck();
        g.shuffle();
        g.dealOne(0);
        assertTrue(g.colHasCards(0));
    }

    @Test
    public void testNoCards () {
        Game g = new Game();
        g.buildDeck();
        g.shuffle();
        assertFalse(g.colHasCards(0));
    }

    @Test
    public void testDoubleDown () {
        Game g = new Game();
        g.buildDeck();
        g.shuffle();
        g.dealHand();

        int initialCards = g.cols.get(1).size();
        int initialMoney = g.money;

        g.doubleDown();

        //After double down the player should have one more card and made another bet
        assertEquals((initialCards + 1), g.cols.get(1).size());
    }

    @Test
    public void testSplit () {
        Game g = new Game();
        Card c1 = new Card(10, Suit.Spades);
        Card c2 = new Card(10, Suit.Clubs);

        g.buildDeck();
        g.shuffle();

        g.cols.get(1).add(c1);
        g.cols.get(1).add(c2);

        g.split();

        assertEquals(1, g.cols.get(1).size());
        assertEquals(1, g.cols.get(2).size());
    }

    @Test
    public void testCalculateTotals () {
        Game g = new Game();

        Card c1 = new Card(12, Suit.Spades);
        Card c2 = new Card(14, Suit.Clubs);
        Card c3 = new Card(10, Suit.Hearts);
        Card c4 = new Card(2, Suit.Diamonds);

        g.cols.get(0).add(c1);

        g.cols.get(1).add(c3);
        g.cols.get(1).add(c4);
        g.cols.get(1).add(c2);

        g.cols.get(2).add(c2);

        g.calculateTotals();

        assertEquals(10, g.dealerTotal);
        assertEquals(13, g.playerTotal);
        assertEquals(11, g.splitTotal);
    }

    @Test
    public void testDealerPlay () {
        Game g = new Game();
        g.buildDeck();
        g.shuffle();

        Card c1 = new Card(2, Suit.Spades);
        Card c2 = new Card(12, Suit.Clubs);

        g.cols.get(0).add(c1);
        g.cols.get(0).add(c2);

        g.dealerPlay();

        assertTrue(g.dealerDone);
    }

    @Test
    public void testUpdateDealTo () {
        Game g = new Game();

        g.updateDealTo();

        assertEquals(2, g.dealTo);
    }

    @Test
    public void testWinner () {
        Game g = new Game();

        Card c1 = new Card(12, Suit.Spades);
        Card c2 = new Card(14, Suit.Clubs);
        Card c3 = new Card(10, Suit.Hearts);
        Card c4 = new Card(2, Suit.Diamonds);

        g.hasSplit = true;

        g.cols.get(0).add(c1);

        g.cols.get(1).add(c3);
        g.cols.get(1).add(c4);
        g.cols.get(1).add(c2);

        g.cols.get(2).add(c3);
        g.cols.get(2).add(c4);
        g.cols.get(2).add(c2);

        g.winner();

        assertTrue(g.playerWon);
        assertFalse(g.dealerWon);

        g.cols.get(0).add(c3);
        g.winner();

        assertFalse(g.playerWon);
        assertTrue(g.dealerWon);
    }

    @Test
    public void testResetGame () {
        Game g = new Game();
        g.playerWon = true;

        g.buildDeck();
        g.shuffle();
        g.dealHand();

        g.resetGame();

        assertEquals(2, g.cols.get(0).size());
        assertEquals(2, g.cols.get(1).size());
        assertEquals(0, g.cols.get(2).size());
        assertEquals(1, g.dealTo);

        assertFalse(g.hasSplit);
        assertFalse(g.error);
        assertFalse(g.dealerDone);
        assertFalse(g.playerWon);
        assertFalse(g.dealerWon);

    }
}
