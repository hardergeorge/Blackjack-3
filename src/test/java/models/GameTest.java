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
        assertEquals(200, g.money);
    }

    /*@Test
    public void testGameStart(){
        Game g = new Game();
        g.buildDeck();
        g.shuffle();
        g.dealHand();
        assertEquals(1,g.cols.get(0).size());
        assertEquals(2,g.cols.get(1).size());
        assertEquals(0,g.cols.get(2).size());
    }*/

    @Test
    public void testDealHand(){
        Game g = new Game();
        g.buildDeck();
        g.shuffle();
        g.dealHand();
        assertTrue(g.dealerTotal >= 2);
        assertTrue(g.playerTotal >= 2);
        assertEquals(0,g.doubleDownTotal);
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


}
