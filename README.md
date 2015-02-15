A05: Rückwertssalto
===================

Melichar Daniel (4CHITM) - 2014/2015

----------

Aufgabenstellung
-------------
Erstelle ein Java-Programm, dass Connection-Parameter und einen Datenbanknamen auf der Kommandozeile entgegennimmt und die Struktur der Datenbank als EER-Diagramm und Relationenmodell ausgibt (in Dateien geeigneten Formats, also z.B. PNG für das EER und TXT für das RM)

Verwende dazu u.A. das ResultSetMetaData-Interface, das Methoden zur Bestimmung von Metadaten zur Verfügung stellt.

Zum Zeichnen des EER-Diagramms kann eine beliebige Technik eingesetzt werden für die Java-Bibliotheken zur Verfügung stehen: Swing, HTML5, eine WebAPI, ... . Externe Programme dürfen nur soweit verwendet werden, als sich diese plattformunabhängig auf gleiche Weise ohne Aufwand (sowohl technisch als auch lizenzrechtlich!) einfach nutzen lassen. (also z.B. ein Visio-File generieren ist nicht ok, SVG ist ok, da für alle Plattformen geeignete Werkzeuge zur Verfügung stehen)

Recherchiere dafür im Internet nach geeigneten Werkzeugen.

Die Extraktion der Metadaten aus der DB muss mit Java und JDBC erfolgen.

**Im EER müssen zumindest vorhanden sein:**

 - korrekte Syntax nach Chen, MinMax oder IDEFIX
   
 - alle Tabellen der Datenbank als Entitäten
  
 - alle Datenfelder der Tabellen als Attribute
   
 - Primärschlüssel der Datenbanken entsprechend gekennzeichnet
   
 - Beziehungen zwischen den Tabellen inklusive Kardinalitäten soweit
   durch Fremdschlüssel nachvollziehbar. Sind mehrere Interpretationen
   möglich, so ist nur ein (beliebiger) Fall umzusetzen: 1:n, 1:n
   schwach, 1:1
   
 -  Kardinalitäten

**Fortgeschritten** (auch einzelne Punkte davon für Bonuspunkte umsetzbar)

- Zusatzattribute wie UNIQUE oder NOT NULL werden beim Attributnamen dazugeschrieben, sofern diese nicht schon durch eine andere Darstellung ableitbar sind (1:1 resultiert ja in einem UNIQUE)

- optimierte Beziehungen z.B. zwei schwache Beziehungen zu einer m:n zusammenfassen (ev. mit Attributen)

- Erkennung von Sub/Supertyp-Beziehungen


Voraussetzungen
-------------
Die Applikation ist basierend auf [Graphviz](http://graphviz.org/ "Graphviz"). Notwendigerweise muss Graphviz auf dem System installiert sein, um die Applikation aus zu führen. 
In der Applikation selbst befindet sich (nach erstmaligem Aufruf, oder durch selbst Erstellung) ein *config.properties* File in welchem der Graphviz bin-Ordner Pfad angegeben werden muss.

Argumente
-------------
**Config**

 - graphvizBinPath

    Der Pfad zum *bin* Ordner der Graphviz Installation

 - outputDirectory
 

    In welchem Ordner das PDF bzw. Text file gespeichert werden soll

 - outputType

    *eer* für ein Entity–Relationship-Diagram
    *rm* für ein Relationales Modell

 - databaseType
 
   MySQL, PostgreSQL, Oracle

**CLI**
Erforderliche Argumente:

    -D oder --Database
    # Name der Datenbank die analysiert werden soll

Optionale Argumente:

	-h oder --host
	# IP-Addresse oder Domain auf welcher sich die Datenbank befindet

	-u oder --user
	# Der User welcher Lese-Rechte auf die Datenbank hat
	
	-p oder --password  
	# Das passwort des Users 

	-P oder --Password
	# Passwortprompt für den User                
                                                                               
Final note
-------------
Das durchlesen der mitgeliferten Dokumentation liefert mehr einblicke.
