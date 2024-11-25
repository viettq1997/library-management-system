# Library-management-system

## Set up

### In Intellij:
- Import javafx sdk: \
    File →
    Project Structure → Libraries → click on `+` symbol
    (New project libraries) → choose ./externallib/javafx-sdk-17.0.13/lib → click `Ok`
- Set up sdk: \
  File →
  Project Structure → SDKs (under platform settings) → choose jdk17
- Edit configurations: \
  - Add Vm options: --module-path pathToSdk/lib --add-modules javafx.controls,javafx.fxml \
  - Note: pathToSdk should be an absolute path. (e.g. /Users/viettran/Public/project/apptech_graduated/project2/library-management-system/externallib/javafx-sdk-17.0.13.2)
- Run

## Admin account
username: admin
password: password123
