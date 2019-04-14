# Ohjelmistotekniikka 2019

## Telegram-botti

[vaatimusmäärittely](./TelegramBot/docs/requirements.md)

[tuntikirjanpito](./TelegramBot/docs/hours.md)

[arkkitehtuuri](./TelegramBot/docs/architecture.md)

### Käynnistys

 - Luo oma botti Telegramin [BotFatherilla](https://telegram.me/BotFather), lisää token ja username config.properties.dist -tiedostoon TOKEN ja NAME tilalle. Poista nimestä pääte `.dist`.

 - Kirjaudu [ElephantSQL](https://www.elephantsql.com/) (tai muuhun postgresql palveluun) ja luo uusi instanssi (Create new instance). Kun tietokanta on luotu, lisää config.properties.dist -tiedostoon `ADDRESS` tilalle osoite (esim. `balarama.db.elephantsql.com:5432/abcabcab`. Lisää myös `USER` ja `PASSWORD` tilalle oikeat arvot. Poista tiedostosta pääte `.dist`.


 - NetBeansin vihreällä napilla, tai komennolla `mvn compile exec:java -Dexec.mainClass=reko.telegrambot.bot.Main`

### Testit

 - Aja testit: `mvn test`

 - Aja testit ja generoi Jacocon testikattavuusraportti: `mvn test jacoco:report`

 - Generoi checkstyle raportti: `mvn jxr:jxr checkstyle:checkstyle`

