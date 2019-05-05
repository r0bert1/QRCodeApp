# QR-code App
Sovelluksen avulla käyttäjien on mahdollista lukea ja luoda QR-koodeja.

## Dokumentaatio

[Käyttöohje](https://github.com/r0bert1/QRCodeApp/blob/master/dokumentaatio/kayttoohje.md)

[Vaatimusmäärittely](https://github.com/r0bert1/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Työaikakirjanpito](https://github.com/r0bert1/ot-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)

[Arkkitehtuurikuvaus](https://github.com/r0bert1/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

[Testausdokumentti](https://github.com/r0bert1/QRCodeApp/blob/master/dokumentaatio/testaus.md)

## Releaset

[Viikko 6](https://github.com/r0bert1/QRCodeApp/releases/tag/viikko6)
[Loppupalautus](https://github.com/r0bert1/QRCodeApp/releases/tag/loppupalautus)

## Komentorivikomennot

### Testaus

Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportin voi luoda komennolla

```
mvn jacoco:report
```

Raportti luodaan tiedostoon _target/site/jacoco/index.html_

### Jarin luominen

Jari luodaan komennolla

```
mvn package
```

Jari luodaan _target_ hakemiston tiedostoon _QRCodeApp-1.0-SNAPSHOT.jar_ ja se suoritetaan samassa kansiossa komennolla

```
java -jar QRCodeApp-1.0-SNAPSHOT.jar
```

### JavaDoc

JavaDoc generoidaan komennolla

```
mvn javadoc:javadoc
```

ja sitä pääse katsomaan selaimella tiedostosta _target/site/apidocs/index.html_

### Checkstyle

Checkstyle tarkistukset suoritetaan komennolla

```
 mvn jxr:jxr checkstyle:checkstyle
```
