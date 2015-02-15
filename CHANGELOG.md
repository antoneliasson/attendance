# Change Log
All notable changes to this project will be documented in this file.

## Unreleased
### Added
- Enable some keyboard navigation. Ctrl+I to toggle *identification checked* for
  selected person, ctrl+space to toggle participation today. "Today" is actually
  the system time today, so ctrl+space can only be used on a course occasion.
- Focus the search field when Esc is pressed.

### Changed
- Updated documentation and change log, also for previous releases.

## 0.3.1 - 2015-02-07
### Changed
- Override the system default character encoding when opening CSV files. Always
  use UTF-8.

## 0.3.0 - 2015-02-07
### Added
- Store the paths of the most recently used database and imported CSV file.
  `java.util.preferences` is used for this.

### Changed
- Parse the header in CSV files during import and guess which column is which
  based on the header names.

### Fixed
- Don't ask the user for confirmation after a failed import. It is automatically
  rolled back.

## 0.2.0 - 2014-02-09
### Added
- GUI controls to open a course database.
- CSV person data importer. When importing existing persons, only update the
  payment field on the existing one instead of adding a duplicate.
- Pretty About dialog in the Help menu.

### Changed
- Figured out which license to use.

## 0.1.0 - 2014-02-02
### Changed
- Initial release
