# Fantasy-Football-Android
Fantasy Football mobile application for the Android platform.

Designed and developed for a university project - a video demonstration can be found <a href="#">here</a>.

The application uses a third-party REST API to retreive live football data from the English Premier League, in order
to allocate points to players in a 'Fantasy Football' style game. The player may also select their favourite club in order to retrieve score updates
and other information specifically for this team.

A bespoke (and now depricated) REST API was developed and hosted in the cloud solely for the use of this project is also used to provide team metadata (logos, shirt
colours, stadium information etc.).

-----------------------------------------------------------------------------------------------

The code structure follows a MVVM architecture, employing Singleton classes, Polymorphic classes, Interfaces and Static methods where necessary.

Libraries and features used:

- Picasso (Image Handling)
- BluelineLabs LoganSquare (JSON Parsing)
- Volley (HTTP REST API Requests)
- SugarORM (Object-Relational Mapper library for working with local SQLite database)
- SQLCipher (Security class to encrypt local databse stored on device)
- Google Play Location Services
- Google Federated Sign-In
- Android AppCompat and other Support libraries for older devices

Development and Build tools used:

- Android Studio 2.2.3
- Gradle build system
- ADB and Emulation tools
- Samsung Galaxy S4 physical device
