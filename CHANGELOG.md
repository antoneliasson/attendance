# Change Log
All notable changes to this project will be documented in this file.

## Unreleased
### Added
- Enable some keyboard navigation. Ctrl+I to toggle *identification checked* for
  selected person, ctrl+space to toggle participation today. "Today" is actually
  the system time today, so ctrl+space can only be used on a course occasion.
- Focus the search field when Esc is pressed.

## 0.3.1 - 2015-02-07
### Changed
- Override the system default character encoding when opening CSV files. Always
  use UTF-8.

## 0.3.0 - 2015-02-07
### Added
- Parse the header in CSV files during import and guess which column is which
  based on the header names.
- Store the paths of the most recently used database and imported CSV file.
  `java.util.preferences` is used for this.

## <=0.2
### Changed
- TODO: To be filled out.
