package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void kortillaOikeaTulostus() {
        assertEquals("saldo: 10.0", kortti.toString());
    }
    
    @Test
    public void oikeaSaldoAlussa() {
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void lataaminenKasvattaaSaldoa() {
        kortti.lataaRahaa(500);
        assertEquals(1500, kortti.saldo());
    }
    
    @Test
    public void saldoVaheneeJosRahatRiittavat() {
        kortti.otaRahaa(500);
        assertEquals(500, kortti.saldo());
    }
    
    @Test
    public void saldoEiMuutuJosRahatEivatRiita() {
        kortti.otaRahaa(2000);
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void ottoPalauttaaTrueJosRahatRiittavat() {
        assertTrue(kortti.otaRahaa(500));
    }
    
    @Test
    public void ottoPalauttaaFalseJosRahatEivatRiita() {
        assertFalse(kortti.otaRahaa(2000));
    }
}
