# Pokemon Tracker

## About

Pokemon Tracker is an android app that allows users to search, favorite, and
view pokemon!

## App Flow and UI Components

This is the general app flow:

1. When the user starts the app, they enter the title activity.
2. After tapping the title activity screen they enter the tracker activity which will create a
   search fragment once.
   - The tracker uses a [*tab navigation layout*](https://material.io/components/tabs)
     with an [*app bar*](https://material.io/components/app-bars-top) at the top of the view.
3. Within the tracker activity, the user can switch between the **Search** and **Favorites** tab
   and corresponding fragments by either tapping the tab or swiping horizontally.
   - Upon navigating to the favorites tab, the fragment will be created once and reused.
4. The user can select a theme using the toolbar at the top, which will be persisted using
   *SharedPreferences*. The activity will be restarted with the new theme applied and a
   [*snackbar*](https://material.io/components/snackbars) confirmation message will be shown.
3. The search fragment renders a list of pokemon using a RecyclerView. Each element utilizes a
   [*card*](https://material.io/components/cards) layout with nested cards for pokemon overview
   information. Upon tapping an element, a [*dialog*](https://material.io/components/dialogs)
   fragment is created which will display more detailed information about the pokemon selected.
4. Inside the detail view, the user can favorite a pokemon. They can tap outside of the dialog to
   exit the detail view.
5. Inside the **Favorites** tab, the user can view a list of all of the pokemon they favorited.
   They can re-enter the detail view and unfavorite a pokemon here by tapping on a sprite.

### Diagram and use cases for each view

![App flow](https://i.imgur.com/wxuJjug.png)

## Architecture

### Dependency Injection

I integrated [Koin](https://insert-koin.io/) annotations to create
class instances with required dependencies.
* I used `@Factory` annotations on
  presenters since they can be created and recreated corresponding to the
  lifecycle of their fragments. I added `@Single`annotations to the repositories
  and image processor class since they only need one instance.

### Data

The app uses data provided by the GraphQL
[DexAPI](https://studio.apollographql.com/sandbox/explorer?endpoint=https%3A%2F%2Fdex-server.herokuapp.com%2F&explorerURLState=N4IgJg9gxgrgtgUwHYBcQC4TADpIAR4CGANsQAoQDWCcE%2BO%2BBeShiuBAvrhyADQgA3QgCcAloQBGxBAGcMWbMO4gOQA).
* Two queries are used to fetch data from the API. The first retrieves a list of
  overview information for each pokemon, and the other provides additional information about a single
  pokemon and is used on an as-needed basis by the app.

```
query PokemonOverview {
    allPokemon {
        name
        nat_dex_num
        weight
        types {
            name
        }
        sprites {
            front_default
        }
    }
}
```

```
query PokemonDetail($id: Int!) {
    pokemon(id: $id) {
        pokedex_entries {
            description
        }
        height
        sprites {
            back_default
        }
    }
}
```

I split up the queries this way instead of fetching all of the data at once because each of the 893
pokemon the API provides info for can have up to ~30 pokedex entries.
* I did not benchmark the reduction in time but there was a noticeable decrease of at least a few seconds
  for the initial overview fetch for me.

After a network request for overview or detailed data, the result is cached to a single table in a
Room database.
* The app will try and find overview or detailed info about a pokemon from the Room
  database before performing a network request to facilitate offline usage.
* Both network requests and database queries are handled by the `PokemonRepository`. SharedPreferences
  queries are handled by the `PreferencesRepository`.

![Repositories](https://i.imgur.com/xBhSoJi.png)

![Pokemon Schema](https://i.imgur.com/rvzi1Xi.png)

### Activity Class Design

Pokemon Tracker uses the MVP architectural pattern, with presenters and activities grouped into
feature folders.

* This pattern separates business logic from each activity and fragment to a
  dedicated presenter. The presenter tells the view when to update the UI and
  communicates with the Model or data layers.

I used abstract superclasses for functionality shared across app activities and
fragments.
* The `TitleActivity` and `TrackerActivity` are derived from the `BaseActivity` class.
* My intention here was for `BaseActivity` to apply app theme settings stored as  
  SharedPreferences to each activity on creation.

The presenters are ultimately derived from the `BasePresenter` class.
* This class  stores references to the activity or fragment lifecycle each presenter is responsible for.
* This setup came in handy for me when I needed to launch coroutines associated with the view lifecycle.

![Title activity](https://i.imgur.com/0bf5x31.png)

![Tracker activity](https://i.imgur.com/3wk6Xv3.png)

The two primary fragments of `TrackerActivity` are subclasses of the abstract class
`DetailFragment`.
* This abstract class contains the functionality responsible for showing the
  pokemon detail modal view. The modal view itself is an Android dialog fragment with a custom view.

While researching I came across a variety of ways to set up base classes and encourage code reuse.
At first, I tried to create interfaces for each activity and fragment to separate the interface
from the implementation.
* Since this was a small application, I eventually opted to have just the
  implementation but have an extra extensible interface for snackbar messages.

### Fragment Class Design

The presenter logic for displaying the pokemon detail dialog is located inside the abstract class `PokemonDetailPresenter`.
* This abstract class also extends the `ImagePresenter` class which contains logic to load an image and extract a color palette from an image.
* The search fragment uses a `RecyclerView` to display all pokemon queried from the `PokemonRepository`.

![Search fragment](https://i.imgur.com/uaNmDym.png)

Similarly, the favorites fragment inherits from the `PokemonDetailPresenter`.
* It uses a grid layout to display sprites of all favorited pokemon.
* One part I didn't get to in time that I
  would like to keep in mind for next time is to propagate changes in the model to the search and favorites
  fragments. Right now, if you add or remove a favorite the UI will not immediately update inside the
  favorites fragment.

![Favorites fragment](https://i.imgur.com/OWVME8g.png)

Finally, the detail dialog fragment inherits directly from the `ImagePresenter`
to display sprites inside the dialog.
* I refactored some of the hardcoded callbacks invoked after asynchronous image processing inside the `ImageProcessor` while working on this fragment.
* This allowed me to more easily set up the `ImagePresenter` which all of the fragments inherit from. 
* For example, descendants of `ImagePresenter` can now pass in their own callback functions upon finishing loading or constructing a palette from an image instead of relying on the `ImageProcessor` to figure out what needs to be done with a processed image.

![Detail dialog fragment](https://i.imgur.com/rUJFOqH.png)
![Image processor](https://i.imgur.com/IP247TI.png)

### UI Design

I used `ConstraintLayouts` for all of the activities and fragments, except the favorites fragment where I used a `GridLayout`.
I avoided using hardcoded values and tried to apply reusable styles except for app module level variables, which I had trouble overriding (ex: `app:cardCornerRadius`).

The color theme was randomly generated using [Coolors.co](https://coolors.co/)
