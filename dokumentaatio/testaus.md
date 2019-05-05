# Testausdokumentti

Sovelluksen testaus on suoritettu automatisoiduilla yksikkötesteillä sekä manuaalisella järjestelmätestaamisella.

## Yksikkätestaus

QRCodeHandlerTest testaa sovelluksen logiikkaa eli QRCodeHandler luokan metodeja. QRCodeHandler-luokka sisältää pelkästään testejä varten luodun metodin decodeQRCodeFromStorage. 
Sen avulla voidaan testata skannausta valmiilla QR-koodi kuvilla, koska Web-kameran avulla tapahtuvaa skannausta on hyvin vaikea testata.

## Testikattavuus

Kun käyttöliittymän koodi jätetään huomioimatta, on testien rivikattavuus 82%. Metodi decodeQRCodeFromWebCam jäi nyt testaamatta, mutta samaa toiminnallisuutta testattiin decodeQRCodeFromStorage-metodin avulla kuten aikaisemmin mainittiin.

<img src="https://github.com/r0bert1/QRCodeApp/blob/master/dokumentaatio/kuvat/testikattavuus.png" >
