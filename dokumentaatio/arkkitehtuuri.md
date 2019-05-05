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

## Luokkakaavio

Sovelluksessa on toistaiseksi vain sovelluslogiikasta vastaava luokka QRCodeHandler.

<img src="https://github.com/r0bert1/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/QRCode_diagram.png" >
