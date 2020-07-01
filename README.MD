# The Brief:

Kotlin app for viewing statistical data relating to the COVID-19 disease. 

## Description

COVID Tracker allows users to view statistical data relating to the COVID-19 disease, by two scales - world or country. The application includes the following features:
* MVVM architecture (formerly MVP)
* Single Activity design
* Data Binding
* View Models
* Use of Android's Navigation Component
* Use of the Volley library for HTTP requests
* HTTP requests are made to the "CORONAVIRUS COVID19 API", where data is sourced from Johns Hopkins CSSE
* Recycler View for displaying the list of 186 countries available. The Recycler View has a Search function for filtering to the desired country. 
* Refresh button for retrying HTTP requests

The following features/improvements are being worked on:
- [ ] Use more endpoints from API to display more data for an individual country
- [ ] Add testing
- [ ] Utilize Kotlin Coroutines
- [ ] Better formatting
- [x] Refactor to save world stats. At present, when a user goes to the country list fragment, and then presses back, the api is called again.
- [ ] Improve GUI design (e.g. themes/different colours, better buttons etc)
- [ ] Reduce duplicated code

Keep in mind that I'm likely to come up with more ideas for improvement as I continue to work on it.
