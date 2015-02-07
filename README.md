Attendance
==========

Ett smidigt program för att ta närvaron på föranmälda deltagare under något
evenemang, t.ex. en danskurs (atten-dance). Anmälningslistor importeras från en
kommaseparerad fil som exporterats från exempelvis Google Forms. Se
[doc/import](doc/Import.md) för närmare dokumentation.

I Attendance kan man kryssa i vilka som är närvarande, notera när deras ID
kontrollerats (ex. studentlegitimation eller biljett) och under vilka tillfällen
de varit närvarande.

Användning (utveckling)
-----------------------

Installera [Apache Maven][]. Klona Attendance' Git-repo. Kör `mvn compile` för
att bygga projektet. `mvn test` kör de automatiska testerna och kopierar
exempeldatabasen från `src/test/resources` till `target/test-classes`. Därefter
kan `mvn exec:exec` användas för att starta programmet. JAR-filer byggs med `mvn
package`. Maven laddar automatiskt ner alla beroenden som krävs.

[apache maven]: http://maven.apache.org/

Användning (demo)
-----------------

Hämta JAR-filen i `dist` och demodatabasen `example.db` i
`src/test/resources`. Spara dem i samma katalog. Kör JAR-filen med Java Runtime,
t.ex. genom att dubbelklicka på den. En annan databas kan användas genom att
ange den som första argumentet på kommandoraden, exempelvis så här:

    $ java -jar attendance-0.1-SNAPSHOT-jar-with-dependencies.jar nybörjarkurs.db

Man kan även starta Attendance utan argument för att öppna exempeldatabasen och
därefter öppna en annan via Arkiv-menyn.

Tekniskt
--------

Data sparas kontinuerligt i en SQLite-databas. Det finns ännu inget sätt att
exportera datan till ett mänskligt läsbart format, men det är en funktion som
kommer i näst-nästa version.

Det går inte att skapa nya kurstillfällen i programmet. Man får öppna databasen
med SQLite:s kommandoradsverktyg och lägga in dem för hand.

Utförlig information om senaste körning sparas i loggfilen `logs/debug.log`.
Varningar och felmeddelanden skrivs även ut till terminalen under körning. Dessa
kan vara ganska viktiga, så i nuläget rekommenderas det att köra programmet i en
terminal.

Kända problem
-------------

* Sökningar efter namn som innehåller Å, Ä eller Ö är skriftlägeskänsliga. Det
gör att t.ex. en sökning efter "åsa" inte kommer att hitta en person med namn
"Åsa".

Önskelista
----------

* Visa senast använda databaser i Arkiv-menyn. Kräver att programmet kan lagra
  konfigurationsfiler någonstans.
* Öppna senast använd databas då programmet startas.
* Visa deltagarstatus med färg i huvudfönstret. T.ex. grönt/gult namn för att
  markera om ID kontrollerats, grönt/rött betalningsdatum för att markera om
  betalning gjorts.
* Visa i statusfältet totala antalet anmälda, antal avprickade för valt
  tillfälle, antal som betalat etc.
* Tidsstämpel-fältet används som nyckel för varje deltagare och förväntas vara
  unik, men importfunktionen kontrollerar inte detta. Ger problem när deltagare
  skrivits in manuellt in anmälningsformuläret (med en påhittad tidsstämpel)
* Bättre navigation med enbart tangentbordet. Exempel på arbetsflöde:
  1. Filtrera personer (sökfältet är markerat som standard)
  2. Navigera till rätt person med piltangenterna
  3. Enter för att markera en person
  4. Mellanslag för att markera närvaro för dagens kurstillfälle
* Funktion för att redigera listan med kurstillfällen.
* Uppdatera andra fält än bara betalning vid CSV-import. Svenska tecken i namn-
  och medlemstyp-fälten gick åt skogen vid import i Windows. Eventuellt hade
  Microsoft Excel med saken att göra.
* Kolla om databasen är låst när en kurs öppnas. Vägra i så fall.
* Spara sökvägar utan sista segmentet (filnamnet) i Config. De senast importerade CSV-filerna raderas rätt ofta och då återställs sökvägen till user.dir. Kataloger raderas inte lika ofta.
