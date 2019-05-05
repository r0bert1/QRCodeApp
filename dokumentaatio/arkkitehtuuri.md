# Arkkitehtuurikuvaus

## Rakenne

Koodin pakkausrakenne:

<img src="https://github.com/r0bert1/QRCodeApp/blob/master/dokumentaatio/kuvat/pakkausrakenne.png">

qrcodeapp.ui sisältää käyttöliittymän ja qrcodeapp.domain sisältää sovelluslogiikan. 

## Käyttöliittymä

Käyttöliittymään sisältyvät näkymät:

- aloitusvalikko
- QR-koodin lukeminen
- QR-koodin luominen

Näkymät on toteutettu omina Scene-olioina qrcodeapp.ui-pakkauksen sisältämässä [QRCodeApplication-luokassa](https://github.com/r0bert1/QRCodeApp/blob/master/src/main/java/qrcodeapp/ui/QRCodeApplication.java).

QR-koodin luku näkymään siirtyessä käyttöliittymä kutsuu [initializeWebCam-metodia](https://github.com/r0bert1/QRCodeApp/blob/master/src/main/java/qrcodeapp/ui/QRCodeApplication.java#L208), jonka seurauksena Web-kamera avataan. Tämän lisäksi initializeWebCam-metodi kutsuu kahta avustajametodia. [startWebCamStream](https://github.com/r0bert1/QRCodeApp/blob/master/src/main/java/qrcodeapp/ui/QRCodeApplication.java#L230) vastaa Web-kameran kuvan päivittämisestä käyttöliittymässä. [scan](https://github.com/r0bert1/QRCodeApp/blob/master/src/main/java/qrcodeapp/ui/QRCodeApplication.java#L266) vastaa mahdollisten kuvasta löytyvien QR-koodien etsimisestä ja lukemisesta. Metodit initializeWebCam, startWebCamStream ja scan ovat toteutettu [Task-olioina](https://docs.oracle.com/javafx/2/api/javafx/concurrent/Task.html), jotta käyttöliittymä pysyisi interaktiivisena samalla kun kameran kuvaa jatkuvasti skannataan QR-koodien varalta.

## Sovelluslogiikka

Sovelluksen logiikasta vastaa luokka [QRCodeHandler](https://github.com/r0bert1/QRCodeApp/blob/master/src/main/java/qrcodeapp/domain/QRCodeHandler.java):

<img src="https://github.com/r0bert1/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/QRCode_diagram.png" >

Koko ohjelmaa kuvaava pakkaus/luokkakaavio:

<img src="https://github.com/r0bert1/QRCodeApp/blob/master/dokumentaatio/kuvat/luokkarakenne.png" >

## Päätoiminnallisuudet sekvenssikaavioina

### QR-koodin lukeminen

Käyttäjän painettua aloitusvalikon _Scan_ painiketta etenee sovelluksen kontrolli seuraavasti:

<img src="https://github.com/r0bert1/QRCodeApp/blob/master/dokumentaatio/kuvat/scansequence.png" >

_Scan_ painikkeen tapahtumakäsittelijä vaihtaa näkymää, ja kutsuu metodia initializeWebCam, joka puolestaan kutsuu metodia scan. Scan metodi kutsuu silmukassa metodia decodeQRCodeFromWebCam niin kauan kunnes käyttäjä joko vaihtaa näkymää tai sulkee sovelluksen. Aina löytäessään QR-koodin decodeQRCodeFromWebCam palauttaa löytämänsä koodin tekstin, joka asetetaan käyttäjän näkyville tekstikenttään.  

### QR-koodin luominen

Käyttäjän syötettyä tarvittavat tiedot ja painettua _Generate_ painiketta generointinäkymässä etenee sovelluksen kontrolli seuraavasti:

<img src="https://github.com/r0bert1/QRCodeApp/blob/master/dokumentaatio/kuvat/generatesequence.png" >

_Generate_ painikkeen tapahtumakäsittelijä kutsuu generateQRCode metodia, joka puolestaan luo QR-koodi kuvan ja tallettaa sen käyttäjän valitsemaan kansioon. Tapahtumakäsittelijä myös asettaa generoidun kuvan käyttäjän nähtäville tekstikenttien yläpuolelle.
