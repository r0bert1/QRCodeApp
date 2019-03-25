package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class KassapaateTest {
    
    Kassapaate kassapaate;
    Maksukortti kortti;
    
    @Before
    public void setUp() {
        kassapaate = new Kassapaate();
        kortti = new Maksukortti(1000);
    }
    
    @Test
    public void alussaOikeaMaaraRahaa() {
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void alussaEiMyytyjaLounaita() {
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty() + kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void edullienKateisostoRiittavallaMaksulla() {
        assertEquals(260, kassapaate.syoEdullisesti(500));
        assertEquals(100240, kassapaate.kassassaRahaa());
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void maukasKateisostoRiittavallaMaksulla() {
        assertEquals(100, kassapaate.syoMaukkaasti(500));
        assertEquals(100400, kassapaate.kassassaRahaa());
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void edullinenKateisostoRiittamattomallaMaksulla() {
        assertEquals(100, kassapaate.syoEdullisesti(100));
        assertEquals(100000, kassapaate.kassassaRahaa());
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void maukasKateisostoRiittamattomallaMaksulla() {
        assertEquals(300, kassapaate.syoMaukkaasti(300));
        assertEquals(100000, kassapaate.kassassaRahaa());
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void edullienKorttiostoRiittavallaSaldolla() {
        assertTrue(kassapaate.syoEdullisesti(kortti));
        assertEquals(760, kortti.saldo());
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void maukasKorttiostoRiittavallaSaldolla() {
        assertTrue(kassapaate.syoMaukkaasti(kortti));
        assertEquals(600, kortti.saldo());
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void edullienKorttiostoRiittamattomallaSaldolla() {
        kortti = new Maksukortti(200);
        assertFalse(kassapaate.syoEdullisesti(kortti));
        assertEquals(200, kortti.saldo());
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void maukasKorttiostoRiittamattomallaSaldolla() {
        kortti = new Maksukortti(300);
        assertFalse(kassapaate.syoMaukkaasti(kortti));
        assertEquals(300, kortti.saldo());
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void kortinLatausPositiivisellaSummalla() {
        kassapaate.lataaRahaaKortille(kortti, 500);
        assertEquals(1500, kortti.saldo());
        assertEquals(100500, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void kortinLatausNegatiivisellaSummalla() {
        kassapaate.lataaRahaaKortille(kortti, -500);
        assertEquals(1000, kortti.saldo());
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    
    
}
