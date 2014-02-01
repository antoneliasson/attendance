Attendance
==========

Ett smidigt program för att ta närvaron på föranmälda deltagare under något
evenemang, t.ex. en danskurs (atten-dance). Anmälningslistor importeras från en
kommaseparerad fil som exporterats från exempelvis Google Forms.

I Attendance kan man kryssa i vilka som är närvarande, notera när deras ID
kontrollerats (ex. studentlegitimation eller biljett) och under vilka tillfällen
de varit närvarande.

Användning
----------
Dubbelklicka på JAR-filen i `dist`.

Tekniskt
--------
Data sparas kontinuerligt i en SQLite-databas. Det finns ännu inget sätt att
exportera datan till ett mänskligt läsbart format, men är en funktion som kommer
i nästa version.

Det enda sättet att importera data just nu är att köra testfallen i Maven. Ett
av dem läser in data från `testdata.csv` och sparar i `test.db`. Denna kan
därefter flyttas till `example.db` som är standarddatabasen om ingen angetts på
kommandoraden.

Utförlig information om senaste körning sparas i loggfilen
`logs/debug.log`. Varningar och felmeddelanden skrivs även ut till terminalen
under körning.
