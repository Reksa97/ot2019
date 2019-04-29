## Testausdokumentti

Ohjelmaa on testattu sekä automatisoiduin yksikkö- ja integraatiotestein JUnitilla ja Mockitolla, sekä manuaalisesti tapahtunein järjestelmätason testein.

### Yksikkö- ja integraatiotestaus

#### Sovelluslogiikka

Domain -testipakkauksen InputHandlerTest -luokka testaa kattavasti ohjelman toimintoja. Luokka testaa komentoja sekä niihin reagointia, kuten botin viestien lähetystä. Testit kattavat myös pysyväistallennusta.

UserDaolle ja User -luokalle on tehty yksikkötestejä testauskattavuuden nostamiseksi.

#### Testauskattavuus

Rivikattavuus on 95% ja haaraumakattavuus 95%. Kattavuusraportti ei koske `telegrambot.bot` -pakkausta.

Testaamatta jäi tapauksia joissa tietokannasta lukeminen ei onnistu jostain syystä.
