CSV-import
==========

Programmet importerar information om deltagare från en CSV-fil som exporterats
från exempelvis Google Forms. Vilka fält som kan hanteras i programmet är ganska
hårdkodat i bland annat Person- och PersonTableModel-klasserna. Vilka som ska
importeras styrs av metoden map i Importer.

Hela importen görs i en databastransaktion som efteråt bekräftas av
användaren. Om importen inte är till belåtenhet ges möjlighet att ångra den
genom att transaktionen rullas tillbaka. Under importen behandlas fältet
Tidsstämpel som ett unikt fält. Detta påtvingas dock inte i kod i nuläget och
dubletter kan orsaka dataförlust eller att programmet kraschar. Därför är det
viktigt att användaren granskar sammanfattningen av importen innan den godkänns.

Importfunktionen försöker först hitta en befintlig person i databasen (baserat
på fältet Tidsstämpel). Om en sådan hittas så uppdateras programmet den med ny
information istället för att lägga till en ny person. I nuläget uppdateras
endast fältet Betalningsdatum (eftersom det är den som uppdateras med tiden i
upphovsmannens användningsfall). I annat fall läggs en ny person till.

Befintliga personer i databasen raderas aldrig vid import. Import av en tom fil
kommer därför inte att göra några ändringar alls.

