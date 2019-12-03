# MoviePro
A simple app that shows an implementation of [Uncle Bob's](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) Clean Architecture on Android. It interacts with the [OMDB Api](http://www.omdbapi.com/)

Technologies and Libraries
-----------------
- Kotlin
- MVVM
- Clean Architecture
- RxJava2
- Microsoft's Image Cognition Library
- Retrofit
- Gson
- Architecture Components (Room, LiveData)
- Mockito, Espresso, JUnit4 tests

# App Flow
1. On app start, the user sees a list of movies.
2. The user can search for any movies in the search box above. The result of the search should display matching movies. 
3. When tapping on a movie, it brings more information about the movie such as 

```
Movie poster
Release Year
Plot
Ratings
Major cast names and image
```

## License

    Copyright 2019, Bolaji Kassim

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
