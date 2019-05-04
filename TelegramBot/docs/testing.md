## Testausdokumentti

Ohjelmaa on testattu sekä automatisoiduin yksikkö- ja integraatiotestein JUnitilla ja Mockitolla, sekä manuaalisesti tapahtunein järjestelmätason testein.

### Yksikkö- ja integraatiotestaus

#### Sovelluslogiikka

Domain -testipakkauksen InputHandlerTest -luokka testaa kattavasti ohjelman toimintoja. Luokka testaa komentoja sekä niihin reagointia, kuten botin viestien lähetystä. Testit kattavat myös pysyväistallennusta.

UserDaolle ja User -luokalle on tehty yksikkötestejä testauskattavuuden nostamiseksi.

#### Testauskattavuus

Rivikattavuus on 93% ja haaraumakattavuus 93%. Kattavuusraportti ei koske `telegrambot.bot` -pakkausta.

Testaamatta jäi tapauksia joissa tietokannasta lukeminen ei onnistu jostain syystä.

### Järjestelmätestaus

Sovelluksen järjestelmätestaus on suoritettu manuaalisesti.

#### Asennus ja konfigurointi

Sovellus on haettu ja saatu käyttöön käyttöohjeen mukaisesti, määrittelemällä `config.properties` -tiedosto käynnistyshakemistoon. Testausta on tehty sekä paikallisesti SQLite-tietokannalla että ElephantSQL-tietokannalla.

#### Koodin laatu

Sovellusta tehtäessä on käytetty checkstyletyylisääntöjä, joita on noudatettu koodissa mahdollisimman tarkasti.
