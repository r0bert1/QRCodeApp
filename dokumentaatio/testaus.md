# Testausdokumentti

Sovelluksen testaus on suoritettu automatisoiduilla yksikkötesteillä sekä manuaalisella järjestelmätestaamisella.

## Yksikkötestaus

[QRCodeHandlerTest](https://github.com/r0bert1/QRCodeApp/blob/master/src/test/java/domain/QRCodeHandlerTest.java) testaa sovelluksen logiikkaa eli [QRCodeHandler](https://github.com/r0bert1/QRCodeApp/blob/master/src/main/java/qrcodeapp/domain/QRCodeHandler.java) luokan metodeja. QRCodeHandler-luokka sisältää pelkästään testejä varten luodun metodin [decodeQRCodeFromStorage](https://github.com/r0bert1/QRCodeApp/blob/master/src/main/java/qrcodeapp/domain/QRCodeHandler.java).
Sen avulla voidaan testata skannausta valmiilla QR-koodi kuvilla, koska Web-kameran avulla tapahtuvaa skannausta on hyvin vaikea testata.

## Testikattavuus

Kun käyttöliittymän koodi jätetään huomioimatta, on testien rivikattavuus 82%. Metodi decodeQRCodeFromWebCam jäi nyt testaamatta, mutta samaa toiminnallisuutta testattiin decodeQRCodeFromStorage-metodin avulla kuten aikaisemmin mainittiin.

<img src="https://github.com/r0bert1/QRCodeApp/blob/master/dokumentaatio/kuvat/testikattavuus.png" >

## Järjestelmätestaus

Sovellusta on testattu manuaalisesti käyttöohjeen mukaan seka Linux- että Windows-käyttöjärjestelmillä. Sovellusta on testattu myös tietokoneella, jossa ei ole Web-kameraa.
