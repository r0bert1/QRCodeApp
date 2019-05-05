# Testausdokumentti

Sovelluksen testaus on suoritettu automatisoiduilla yksikkötesteillä sekä manuaalisella järjestelmätestaamisella.

## Yksikkätestaus

QRCodeHandlerTest testaa sovelluksen logiikkaa eli QRCodeHandler luokan metodeja. QRCodeHandler-luokka sisältää pelkästään testejä varten luodun metodin decodeQRCodeFromStorage. 
Sen avulla voidaan testata skannausta valmiilla QR-koodi kuvilla, koska Web-kameran avulla tapahtuvaa skannausta on hyvin vaikea testata.

## Testikattavuus

