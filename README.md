Attendance
==========

Ett smidigt program för att ta närvaron på föranmälda deltagare under något
evenemang, t.ex. en danskurs (atten-dance). Anmälningslistor importeras från en
kommaseparerad fil som exporterats från exempelvis Google Forms.

I Attendance kan man kryssa i vilka som är närvarande, notera när deras ID
kontrollerats (ex. studentlegitimation eller biljett) och under vilka tillfällen
de varit närvarande.

Användning (utveckling)
-----------------------

Installera [Apache Maven][]. Klona Attendance' Git-repo. Kör `mvn compile` för
att bygga projektet. `mvn test` kör de automatiska testerna och kopierar
exempeldatabasen till `target/test-classes`. Därefter kan `mvn exec:exec`
användas för att starta programmet. JAR-filer byggs med `mvn package`. Maven
laddar automatiskt ner alla beroenden som krävs.

[apache maven]: http://maven.apache.org/

Användning (demo)
-----------------

Hämta JAR-filen i `dist` och demodatabasen `example.db` i rotkatalogen. Spara
dem i samma katalog. Kör JAR-filen med Java Runtime, t.ex. genom att
dubbelklicka på den. En annan databas kan användas genom att ange den som första
argumentet på kommandoraden, exempelvis så här:

    $ java -jar attendance-0.1-SNAPSHOT-jar-with-dependencies.jar nybörjarkurs.db

Tekniskt
--------

Data sparas kontinuerligt i en SQLite-databas. Det finns ännu inget sätt att
exportera datan till ett mänskligt läsbart format, men det är en funktion som
kommer i nästa version.

Det enda sättet att importera data just nu är att köra testfallen i Maven. Ett
av dem läser in data från `testdata.csv` och sparar i `test.db`. Denna kan
därefter flyttas till `example.db` som är standarddatabasen om ingen angetts på
kommandoraden.

Utförlig information om senaste körning sparas i loggfilen `logs/debug.log`.
Varningar och felmeddelanden skrivs även ut till terminalen under körning.
