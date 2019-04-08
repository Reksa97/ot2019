# Ohjelmistotekniikka 2019

## Telegram-botti

[vaatimusmäärittely](./TelegramBot/docs/requirements.md)

[tuntikirjanpito](./TelegramBot/docs/hours.md)

[arkkitehtuuri](./TelegramBot/docs/architecture.md)

### Käynnistys

 - Luo oma botti Telegramin BotFatherilla, lisää token ja username PizzaCounterBot.java -tiedostoon.

 - NetBeansin vihreällä napilla, tai komennolla `mvn compile exec:java -Dexec.mainClass=reko.telegrambot.Main`

### Testit

 - Aja testit: `mvn test`

 - Aja testit ja generoi Jacocon testikattavuusraportti: `mvn test jacoco:report`

 - Generoi checkstyle raportti: `mvn jxr:jxr checkstyle:checkstyle`

