# SuperMarvel

<b>SuperMarvel</b> is an Android Application that lists Marvel's Superheroes and their abilities and power details</b>

This project uses the <b>MVVM Architectural Pattern</b> with <b>SOLID principles.</b>

# Responsibility of each Layer in the Architecture Pattern

<b>The Model Layer:</b> This layer contains at the same time two entities, <b>the Business Model</b> and a <b>Repository</b>.
The fisrt entity, is used for mapping the data for handling, the second entity is resposible for fetching this data like superheroes from the API and to hold the data as cache for serving it.

<b>The ModelView Layer:</b> the responsability of this layer is to serve the data from the respository to the view and also to notify it when a change exist inside this data. Also handles changes on the data logic, like mutating the data doing operatios on them from the repository.

<b>The View Layer:</b> the responsability of this layer is only for display user interface and handle user input, is the visual communicaton from the user to the application, this layer is aware of data changing to represent it in a reactive form. Also it handles animations and transitions between views.

# SuperMarvel uses following Features

- Android<br />
- Kotlin<br />
- Jetpack<br />
- Architectural Components<br />
- MVVM<br />
- LiveData<br />
- DataBinding<br />
- RxJava<br />
- Reactive Programming<br />
- Activity<br />
- Fragment<br />
- SOLID<br />

# App Screenshots

<table style="width:100%">
  <tr>
    <th><img src="https://github.com/inigofrabasa/SuperMarvel/blob/master/0001.png" width="250"/></th>
    <th><img src="https://github.com/inigofrabasa/SuperMarvel/blob/master/0002.png" width="250"/></th>
    <th><img src="https://github.com/inigofrabasa/SuperMarvel/blob/master/0003.png" width="250"/></th>
  </tr>
</table>
