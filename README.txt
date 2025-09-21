ClosestWords.java och Main.java är orginalfiler från uppgiften utöver att Main.java använder ClosestWordsV3 klassen istället för orginalet ClosestWords.
Kan vara så att små ändringar har gjorts men ingenting som påverkar funktionenen. 
Exempelvis implementerar alla ClosestWord ett interface som heter ClosestWordInterface för att MyMain ska fungera som det ska (men gör ingen skillnad i funktion).

ClosestWordsV2.java är den första versionen där jag implementerat partDist metoden med dynamisk programmering.
Denna klarade 4 av 7 tester på kattis men var för långsam för den 5:e.

ClosestWordsV3 är den sista versionen där jag untyttjat att två ord med samma p första bokstäver har distansmatriser med de p första kolumnerna likadana.
Detta var väldigt effectivt då alla ord från ordlistan kommer i bokstavsordning så det är stor sannolikhet för stort värde på p.
Detta enligt sista uppgiften för teorifrågorna för labb 2. 

MyMain.java är nästan identisk till Main.java utöver att man kan välja vilken version man köra (orginal, V2 eller V3). 

Hur kör man dessa program:

    Main.java
        Testning:
            kommando: java Main -t testkatalog
            där testfil exempelvis kan vara: test eller large
            exempel: java Main -t large
            exempel: java Main -t test large #Testar både katalogen test och large
        Köra en fil:
            cat filenDuVillKöra | java Main
    MyMain.java
        Testning:
            kommando: java MyMain 0 -t testkatalog
            där testfil exempelvis kan vara: test eller large
            exempel: java Main 0 -t large

