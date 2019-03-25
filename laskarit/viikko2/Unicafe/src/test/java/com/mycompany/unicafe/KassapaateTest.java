package com.mycompany.unicafe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class KassapaateTest {

    Kassapaate paate;
    Maksukortti kortti;

    @Before
    public void setUp() {
        paate = new Kassapaate();
    }

    @Test
    public void paatteenRahamaaraAlussaOikein() {
        assertEquals(100000, paate.kassassaRahaa());
    }

    @Test
    public void paatteenMyydytLounaatAlussaOikein() {
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }

    @Test
    public void kateisostoTasarahallaToimiiEdullisellaLounaalla() {
        int vaihtoraha = paate.syoEdullisesti(240);
        assertEquals(0, vaihtoraha);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void kateisostoIsommallaRahallaToimiiEdullisellaLounaalla() {
        int vaihtoraha = paate.syoEdullisesti(500);
        assertEquals(260, vaihtoraha);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void kateisostoLiianPienellaRahallaEiToimiEdullisellaLounaalla() {
        int vaihtoraha = paate.syoEdullisesti(239);
        assertEquals(239, vaihtoraha);
        assertEquals(0, paate.edullisiaLounaitaMyyty());
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void kateisostoTasarahallaToimiiMaukkaallaLounaalla() {
        int vaihtoraha = paate.syoMaukkaasti(400);
        assertEquals(0, vaihtoraha);
        assertEquals(0, paate.edullisiaLounaitaMyyty());
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void kateisostoIsommallaRahallaToimiiMaukkaallaLounaalla() {
        int vaihtoraha = paate.syoMaukkaasti(500);
        assertEquals(100, vaihtoraha);
        assertEquals(0, paate.edullisiaLounaitaMyyty());
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void kateisostoLiianPienellaRahallaEiToimiMaukkaallaLounaalla() {
        int vaihtoraha = paate.syoMaukkaasti(399);
        assertEquals(399, vaihtoraha);
        assertEquals(0, paate.edullisiaLounaitaMyyty());
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void korttiostoToimiiKunSaldoaJuuriRiittavastiEdullisellaLounaalla() {
        kortti = new Maksukortti(240);
        boolean onnistui = paate.syoEdullisesti(kortti);
        assertTrue(onnistui);
        assertEquals(0, kortti.saldo());
        assertEquals(1, paate.edullisiaLounaitaMyyty());
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void korttiostoToimiiKunSaldoRiittaaEdullisellaLounaalla() {
        kortti = new Maksukortti(1000);
        boolean onnistui = paate.syoEdullisesti(kortti);
        assertTrue(onnistui);
        assertEquals(760, kortti.saldo());
        assertEquals(1, paate.edullisiaLounaitaMyyty());
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void korttiostoEiToimiKunSaldoEiRiitaEdullisellaLounaalla() {
        kortti = new Maksukortti(239);
        boolean onnistui = paate.syoEdullisesti(kortti);
        assertFalse(onnistui);
        assertEquals(239, kortti.saldo());
        assertEquals(0, paate.edullisiaLounaitaMyyty());
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void korttiostoToimiiKunSaldoaJuuriRiittavastiMaukkaallaLounaalla() {
        kortti = new Maksukortti(400);
        boolean onnistui = paate.syoMaukkaasti(kortti);
        assertTrue(onnistui);
        assertEquals(0, kortti.saldo());
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }

    @Test
    public void korttiostoToimiiKunSaldoRiittaaMaukkaallaLounaalla() {
        kortti = new Maksukortti(1000);
        boolean onnistui = paate.syoMaukkaasti(kortti);
        assertTrue(onnistui);
        assertEquals(600, kortti.saldo());
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }

    @Test
    public void korttiostoEiToimiKunSaldoEiRiitaMaukkallaLounaalla() {
        kortti = new Maksukortti(399);
        boolean onnistui = paate.syoMaukkaasti(kortti);
        assertFalse(onnistui);
        assertEquals(399, kortti.saldo());
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void korttiostoEiVaikutaKassanSaldoon() {
        kortti = new Maksukortti(1000);
        paate.syoEdullisesti(kortti);
        assertEquals(100000, paate.kassassaRahaa());
        
        paate.syoMaukkaasti(kortti);
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void kortinLatausNostaaKassanJaKortinSaldoa() {
        kortti = new Maksukortti(0);
        paate.lataaRahaaKortille(kortti, 1000);
        assertEquals(1000, kortti.saldo());
        assertEquals(101000, paate.kassassaRahaa());
    }
    
    @Test
    public void kortinLatausNegatiivisellaSummallaEiTeeMitaan() {
        kortti = new Maksukortti(10);
        paate.lataaRahaaKortille(kortti, -1);
        assertEquals(10, kortti.saldo());
        assertEquals(100000, paate.kassassaRahaa());
    }
}
