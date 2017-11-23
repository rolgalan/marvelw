# Marvel
This is an Android application that communicates with the Marvel API and requests the comics of a certain character (using Captain America by default).

Then it displays all the comics in a grid. When an item is selected, a detail view of the comic is displayed with some extra info.

## Compilation
Marvel API requires a public and private key. You need your owns to use the application. Once you get them, rename the file `secrets.properties.CHANGEME` to `secrets.properties` and fill the variables with your own data. Then, gradle will inject this data into the app. This way, we're not exposing the keys in public repositories. [Learn how to get your keys in the Marvel website](http://developer.marvel.com/documentation/authorization)

## Libraries
- Retrofit for networkig
- ButtnerKnife for view injection
- Glide for image loading
- JUnit and Mockito for testing
- Stetho for debugging requests

## Architecture
The whole project is implemented following a clean architecture. The solution has been over-engineered just to show how an Android application should be designed.

Different modules have been used to separate each layer. This is not only for the sake of separation of concerns, it's also because modularisation may improve the compilation times ([read this article for more info][modularisation]).

The four modules are:

- **model**: All the interfaces representing the information used by our app. There are also a specific implementation for each interface.
- **server**: all the networking is doing here. Each response from the server has its own data classes different than those in the **model** package, so both can be changed independently.
- **provider**: It's the only thing known by the view and contains the business part. Connects to the server to fetch the info, transform it into the **model** data types, and return the data asynchronously. It implements a basic cache level of already requested data.
- **app**: all the activities and view related code.

If we required storing info in a local database, a new module should be created and the provider would decide whether fetch the info from the server or from the DB. This way the view doesn't care about where the data comes from.

Different classes are defined for server objects and local objects. This helps avoiding null responses from the server, and simplify complex data structures (like converting server objects holding images into simple strings with the whole url).

The provider layer stores responses from server in a really basic cache (it does not persist, it's just an object in the memory). But this helps to not reload the list when coming back from the detail.

Pagination is implemented, and pre-loads the next page of items when the scroll is about to end (last 4 rows).

## Testing
Just an example unit test class has been added. Adding more is in the todo list. UI testing would have required more time to implement it.

Manual testing has been done on physical Nexus 5X device 8.0 (API 26).

[modularisation]: https://medium.freecodecamp.org/how-modularisation-affects-build-time-of-an-android-application-43a984ce9968