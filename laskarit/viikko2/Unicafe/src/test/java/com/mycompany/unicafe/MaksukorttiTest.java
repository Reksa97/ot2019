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
    public void kortinSaldoOikeinAlussa() {
        assertEquals("saldo: 10.00", kortti.toString());
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void rahanLataaminenKasvattaaSaldoa() {
        kortti.lataaRahaa(910);
        assertEquals("saldo: 19.10", kortti.toString());
    }
    
    @Test
    public void saldoVaheneeOikeinJosRahaaOnTarpeeksi() {
        boolean otettiinRahaa = kortti.otaRahaa(495);
        assertEquals("saldo: 5.05", kortti.toString());
        assertEquals(true, otettiinRahaa);
    }
    
    @Test
    public void saldoEiVaheneJosRahaaEiOleTarpeeksi() {
        boolean otettiinRahaa = kortti.otaRahaa(1001);
        assertEquals("saldo: 10.00", kortti.toString());
        assertEquals(false, otettiinRahaa);
    }
}
