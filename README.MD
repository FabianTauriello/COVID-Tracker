# The Brief:

Kotlin app for viewing statistical data relating to the COVID-19 disease. 

## Description

COVID Tracker allows users to view statistical data relating to the COVID-19 disease. Data can be viewed for an individual country or worldwide. The application includes the following features:
* MVVM architecture (formerly MVP)
* Single Activity design
* Data Binding
* View Models
* Coroutines
* Room for caching data (latest data saved to the local Room database can be viewed while user is offline)
* Android's Navigation Component
* Retrofit library for HTTP requests (formerly Volley)
* HTTP requests are made to the "CORONAVIRUS COVID19 API", where data is sourced from Johns Hopkins CSSE. Documentation can be found here: https://documenter.getpostman.com/view/10808728/SzS8rjbc?version=latest
* Recycler View for displaying the list of 186 countries available. The Recycler View has a Search function for filtering to the desired country.

The following features/improvements are being worked on:
* More endpoints from API to display more data for an individual country
* Testing
* GUI design (e.g. themes/different colours, better buttons etc)
* OPTIMIZATION, OPTIMIZATION, OPTIMIZATION!

Keep in mind that I'm likely to come up with more ideas for improvement as I continue to work on it.

Screenshots:

<img src="/screenshots/global-stats.png" width="350" height="650" />
<img src="/screenshots/countries-list.png" width="350" height="650" />
<img src="/screenshots/countries-search.png" width="350" height="650" />
<img src="/screenshots/country-stats.png" width="350" height="650" />

Global Stats             |  Countries List
:-------------------------:|:-------------------------:
![Global Stats](/screenshots/global-stats.png)  |  ![Countries List](/screenshots/countries-list.png)
Global Stats             |  Countries List
:-------------------------:|:-------------------------:
![Global Stats](/screenshots/global-stats.png)  |  ![Countries List](/screenshots/countries-list.png)