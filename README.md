# QA4 Expense Tracker (JavaFX + SQLite + JDBC)

## Features
- 3 JavaFX screens: Home, Categories (CRUD), Expenses (CRUD + TableView)
- 3 SQLite tables: users, categories, expenses
- CRUD implemented using JDBC

## DB
`database/expense_tracker.db` is included.

## Add SQLite JDBC JAR
Download sqlite-jdbc and place it in `lib/` as `sqlite-jdbc.jar`.

## Compile & Run (PowerShell)
Edit the JavaFX path if needed.

### Compile
```powershell
javac --module-path "C:\openjfx-25.0.2_windows-x64_bin-sdk\javafx-sdk-25.0.2\lib" `
--add-modules javafx.controls `
-cp ".\lib\sqlite-jdbc.jar" `
-d out `
( Get-ChildItem -Recurse -Filter *.java -Path .\src | ForEach-Object { $_.FullName } )
```

### Run
```powershell
java --module-path "C:\openjfx-25.0.2_windows-x64_bin-sdk\javafx-sdk-25.0.2\lib" `
--add-modules javafx.controls `
-cp ".\out;.\lib\sqlite-jdbc.jar" `
app.Main
```
