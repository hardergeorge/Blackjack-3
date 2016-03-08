package models;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by harderg and friends on 1/26/16.
 */
public class CardTest {
    @Test
    public void testGetSuit(){
        Card c = new Card(5, Suit.Hearts);
        assertEquals(Suit.Hearts, c.getSuit());
    }

    @Test
    public void testGetValue(){
        Card c = new Card(5, Suit.Hearts);
        assertEquals(5, c.getValue());
    }

    @Test
    public void testToString(){
        Card c = new Card(5, Suit.Hearts);
        assertEquals("5Hearts", c.toString());
    }

}