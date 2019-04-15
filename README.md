# Ohjelmistotekniikka 2019

## Telegram-botti

[vaatimusmäärittely](./TelegramBot/docs/requirements.md)

[tuntikirjanpito](./TelegramBot/docs/hours.md)

[arkkitehtuuri](./TelegramBot/docs/architecture.md)

### Käynnistys

 - Luo oma botti Telegramin [BotFatherilla](https://telegram.me/BotFather), lisää token ja username config.properties.dist -tiedostoon TOKEN ja NAME tilalle. Poista nimestä pääte `.dist`. Jos et halua käyttää postgresql, niin poista tiedostosta muut rivit ja ohita seuraava vaihe.

 - Kirjaudu [ElephantSQL](https://www.elephantsql.com/) (tai muuhun postgresql palveluun) ja luo uusi instanssi (Create new instance). Kun tietokanta on luotu, lisää config.properties.dist -tiedostoon `ADDRESS` tilalle osoite (esim. `balarama.db.elephantsql.com:5432/abcabcab`. Lisää myös `USER` ja `PASSWORD` tilalle oikeat arvot. Poista tiedostosta pääte `.dist`.


 - NetBeansin vihreällä napilla, tai komennolla `mvn compile exec:java -Dexec.mainClass=reko.telegrambot.bot.Main`

### Testit

 - Aja testit: `mvn test`

 - Aja testit ja generoi Jacocon testikattavuusraportti: `mvn test jacoco:report`

 - Generoi checkstyle raportti: `mvn jxr:jxr checkstyle:checkstyle`

### Build

 - Generoi TelegramBot-1.0.jar -tiedosto target-hakemistoon: `mvn package`

### Release

#### TelegramBot-1.0

 - Luo oma botti Telegramin [BotFatherilla](https://telegram.me/BotFather)

 - Lataa [jar-tiedosto](https://github.com/Reksa97/ot2019/releases/tag/viikko5)

 - Lisää kansioon tiedosto `config.properties`

 - Lisää tiedostoon botille antamasi nimi ja BotFatherin antama token:

```
botname=NAME
bottoken=TOKEN
```

 - Jos haluat käyttää [ElephantSQL](https://www.elephantsql.com/) (tai muuta postgresql palvelua), lisää tiedostoon myös nämä (ADDRESS on tyyppiä `balarama.db.elephantsql.com:5432/abcabcab`). Jos nämä jätetään pois, käyttää botti SQLiteä ja luo `database.db` tiedoston:

```
dbuser=USER
dbpassword=PASSWORD
db=jdbc:postgresql://ADDRESS
```

 - Käynnistä jar: `java -jar TelegramBot-1.0.jar`


### Käyttöohjeet

Etsi botti Telegramista ja lähetä sille viesti.

 - `help`: Antaa ohjeita

 - `add pizza name, restaurant name, date eaten(dd.mm.yyyy)`: Lisää pizzan

 - `add margarita, barbarossa`: Lisää pizzan nykyisellä päivämäärällä

 - `list`: Listaa lisäämäsi pizzat
