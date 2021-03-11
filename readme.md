# Simple Android App to search for StarWars characters

This is a simple Android App to search for Star Wars characters using http://swapi.dev/ API to fetch the character data.

This project aims to build a simple yet scalable product, using modern architectures and resources to solve some of the problems encountered.

## Architectural Patterns
The project makes use of a Clean Architectural pattern, although the initial app is quite small and clean architecture seemed like overkill, I had to be futuristic in mind and build something that leaves room for scalability and also offers a better avenue to be as thorough as with testing of the application, the project structure is also aided when implementing clean architecture, as you have a clear separation of concern.

I combined this with a repository pattern in my data layer, so as to have a clean abstraction from the domain layer, implementing interactors that served to implement the use-cases provided by the domain layer. I also favoured an MVVM approach which gives a structure of how the UI interacts with the repository by observing changes in the viewmodel livedata and reacting to these changes.

Furthermore, I settled on Hilt for dependency injection and most of the injected classes were from the data layer, I set up injection for our API client, API service and our interactors. we get benefits like better testing, cleaner code, less boilerplate when we use dependency injection frameworks.

## Resources
In terms of resources, the following libraries were used.

* Kotlin: As was required, the project was entirely built on Kotlin.

* Retrofit: Android client for connecting to REST APIs, it provides a clean and easy setup, with little setup overhead cost.

* Hilt: Android Dependency Injection library built on Dagger framework.

* Timber: Library for simple console logging

* ViewModels: components for managing state and interactions between fragments in the app.

* Chuck Interceptor: Network interceptor library for monitoring network requests, shows a notification of an ongoing request in the status bar.

* Jetpack Navigation Components: Jetpack libraries for navigation through an android app using Fragments, this app uses a single activity and multiple fragment approach, this was made easier using the Jetpack Navigation components, using this approach allows for less clutter, lighter apps, since fragments are created and swapped out at runtime, not having to declare tons of activity in the manifest file, etc.

* AndroidX with support libraries: constraint layout, material, appcompat etc.

* Coroutines: a library that aids multithreading by allowing us to specify threads on which we want our processes should run. I favored coroutines over RxKotlin firstly because, Trivago is looking to navigate away form Rx, also because of its simplicity.

## Test resources
In terms of testing, I leaned towards more android Instrumented tests over unit tests as I didn't have a bunch of utility classes or functions to test, I implemented tests for the ViewModels, Repository, API Service, and adapter.

* Truth google library for test assertions, I find it quite concise and more human-readable than other of the same libraries.

* Jupiter: used mostly for ordering the sequence of unit tests. 

* Mock Web Server: used for mocking web services and returning responses.  

* Coroutines-test: was primarily used for running concurrent blocks of code that depended on the outcomes of other blocks.

* Hilt test: Hilt testing library for injection of test components.


## The Approach
The main idea was to have a solution where users can search for Star wars characters and see more details about them, the user flow would entail that the user opens the app and searches for a character, then we show the result and on click of that result, we take the user to the detail page.

Firstly I defined my domain layer, defining the use cases for the search page and detail page, next I implemented the data layer that deals with remote API connectivity, the API service class defines the endpoints that would be used and an ApiHelper class defines a set of use cases(that match those from the domain layer) and the interactors get to implement the use cases and pass the result to the repository which in turn feeds the ViewModel and the UI.

When a user lands on the search page, he provides the search query and we initiate the search. when the result is returned, we parse the data via our GsonConverter factory that's injected into the retrofit builder instance.

Using navigation components, we set up a navigation graph that defined the UI contracts and connection specification, the DetailFragment was set up to receive the initially searched data payloads a bundle argument.

When the user clicks on the displayed character we bundle the Character Object and pass it to the DetailFragment, so as to avoid making multiple network calls for duplicate data.

in the DetailFragment I receive the Character data and map it to its associated text, then I set up my observers to listen for changes from the ViewModel, while I make requests for the Film, Specie, and planet data. when this data is returned, the user can click on the film elements to access the full description of the movie.

## Further steps
Some other steps I could take to further enhance the functionality of the app would be:

* Local Datastore: I could implement and room database to save the users' searches and display them in an autocomplete list.

* Display all: I could use another endpoint to fetch some available user, so the user can choose between searching a character and selecting an existing user on their screen.

* Load More: I could also implement a load more feature, so the app auto loads more data when the users scrolls to the bottom.

## Conslusion
In conclusion, I enjoyed working on this solution, one of the places that made me look for a different approach was in getting the films and displaying them, so I had to use a Flow, instead of a regular function, because flows can repeatedly call functions until the producer of the instruction is terminated of completes, so I used a flow to retried the data and then add this data to a list in my view model, then finally add this updated list to my livedata.

In all, I would say it's an exciting case study to work on.

Thanks."# swapi-api" 
