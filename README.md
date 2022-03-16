# Pokemon Tracker

## About

Pokemon Tracker is a simple android app that allows users to search, filter, and favorite pokemon.

## Architecture

* Add diagram

Pokemon Tracker uses the MVP architectural pattern, with presenters and activities grouped into feature folders. Each activity and presenter extends the BaseActivity class and BasePresenter class respectively.
Since I'm pretty new to android development, I did some research into the android architectural components and quickly discovered many different approaches to integrating MVP.

- Title activity
- Finder activity
    - Search fragment
      - The search fragment 
    - Favorites fragment
    
- Data fetched using the GraphQL Pokedex API using Apollo 
- Network data cached into RoomDB store to for offline usage (todo)
- Shared preferences (for app theme?)
- Shared elements between finder fragment and pokemon detail view
- Transitions

Material Design Components
- Bottom Navigation
- Card
- Floating Action Button
- Sheets: Bottom
- Snackbars

### Data Models
* Add nice tables
