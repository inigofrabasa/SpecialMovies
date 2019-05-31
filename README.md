# SpecialMovies

<b>Special Movies</b> android project uses the <b>MVVM Architectural Pattern</b> with <b>Clean Code</b> structure and <b>SOLID principles.</b>

Internally for a clean code structure the project only uses a <b>Single Entity</b> called "specialMovies" since is a basic app that holds a list of movies and their detail, just this unique entity holds the differents Layers of the <b>MVVM Architectural Pattern</b> for the separation of resposabilities.

# Responsibility of each Layer in the Architecture Pattern

<b>The Model Layer:</b> This layer contains at the same time two entities, <b>the Business Model</b> and a <b>Repository</b>.
The fisrt entity, is used for map the data for handling, the second entity is resposible for fetching this data like movies and video from the API and hold the data as cache for serving it.

<b>The ModelView Layer:</b> the responsability of this layer is to serve the data from the respository to the view and also to notify it when a change exist inside this data. Also handles changes on the data logic, like mutating the data doing operatios on them from the repository.

<b>The View Layer:</b> the responsability of this layer is only for display user interface and handle user input, is the visual communicaton from the user to the application, this layer is aware of data changing to represent it in a reactive form. The user will see list of movies and details of each item. Also it handles animations and transitions between views.

# SpecialMovies uses following Features

Android<br />
Kotlin<br />
Jetpack<br />
Architectural Components<br />
MVVM<br />
LiveData<br />
DataBinding<br />
Reactive Programing<br />
RxJava<br />
Retofrit<br />
Activity<br />
Fragment<br />
Webview<br />
Clean Code<br />
SOLID<br />

# What is the principle of sole responsibility? What's its purpose?

# What characteristics have a "good" code or clean code?


