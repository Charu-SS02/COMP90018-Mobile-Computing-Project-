# COMP90018-Mobile-Computing-Project Submission
# FindCoffee

### Group No: # W01/10-5

### Group Members:

agohil       agohil@student.unimelb.edu.au

charusmitas  charusmitas@student.unimelb.edu.au

dhairyakamal dhairyakamal.shah@student.unimelb.edu.au

rgadsby      rgadsby@student.unimelb.edu.au

Salyahya     salyahya@student.unimelb.edu.au

xixiangw.    xixiangw@student.unimelb.edu.au

### YouTube Link:


### Compilation instructions:
Extract the project and import into Android studio as Gradle project.	
Build the project.

### Application execution:
Directly build to 

### Project Description
Melbourne's love affair with a coffee can be traced back to the arrival of Italian and Greek immigrants after World War II. As a generation of migrants brought their beloved European-style espresso machines to Melbourne, the espresso boom of the 1950s soon became a way of life.
Melbourne is known globally for its coffee obsession. It offers more than 2,000 cafés as well as some of the world's best baristas. 

So, many coffee goers want to enjoy a new place every day, but the truth is, searching for a good coffee shop takes time.

The solution is : Find Coffee App 
In the Find Coffee app, it makes it easier for the customer to find the best coffee shops near them in terms of distance, rating and price. It will display lists of coffee shops nearby the customer. Whenever a customer chooses a coffee shop, it will show a page of details, which contains an image, shop name, rating, price, address, a google map view, opening hour, a menu of shop, events, store. When the customer presses on the map, the direction will be shown directly in Google Map. 

Find Coffee app achieved many features:
- Search feature: This feature allows the customer for searching specific coffee shops.
- Navigation feature: This feature enables the customer to navigate to one of the suggested coffee shops. 
- AR feature: Augmented Reality feature to allow the customer to have fun when he walked to the selected coffee shop.
-Game feature: This feature gives the user to play a simple game while he waits for the coffee.

### Background and strategic fit
Development Environment: Android Studio
Sensor Used: GPS, camera, gyroscope Touch, Text Input
Access information from the internet/cloud: Zomato API https://developers.zomato.com/documentation , Google Maps
Innovative: AR Integration

#### Home page:
The home page of the app consists of a map view showing nearby coffee shops through the integration of Google Maps API. The blue marker denotes the user’s current location which has been obtained using a GPS sensor. The coffee shops fetched from the API are being filtered out by excluding any coffee shop that lies outside a 1km radius from the user’s current location.On clicking the location marker of our choice, it redirects us to the Google Maps navigation providing the directions to reach the shop. This has been achieved using Intent.

Just below the map, recycler view of the nearby coffee shops is also presented. The data for the coffee shops displayed has been fetched through the Zomato API. The data includes an overview of each shop including its name,address, quality and price rating and the distance from the user's current location.  On clicking on any of the shops, the user will be redirected to the shop details page which we will talk about later in this video.

#### Search page
Moving onto the Search page which helps the user find specific coffee shops. It requires a minimum of three search characters to be entered and returns a result list containing all the best matched coffee shops. When the user clicks on any of  the coffee shops it will again redirect the user to the shop details page.

#### Explore page
The Explore page helps the user in choosing the suggested famous coffee shops. It differs from the home page in the fact that it includes all the top rated coffee shops for a region without filtering them out by the radius. However, the recycler view contents are similar to the home page. 

#### The Shop Details Page
Upon clicking any of the shops in recycler view, we are navigated to the shop details page which contains individualized information about the selected coffee shop. Google map has again been displayed in this view. On clicking the location marker in the map, the user is presented with two options - the first option displays different modes of travel to reach the shop and the second option is the navigation.
On scrolling down, other details such as opening and closing hours of the shop, price range of how expensive or affordable it is , is given along with links to the zomato website if a user wants to browse the menu, events and all the stores related to the particular coffee shop.

#### AR finding coffee shop

The shop details page also includes the most attractive feature of this app which is Augmented Reality or AR. On clicking the blue AR icon, the user is redirected to the AR activity page.



The AR feature of FindCoffee didn’t use a library, which is quite surprising. When you are browsing the coffee shops from the home page, search page and explore page, a small green AR button is on the bottom-right side of the app. The button will redirect you to the AR view. Now, rotate your camera, try to find the black bubble with the coffee shop name, down below the name, it shows outdoor seats and indoor seats number retrieved from Melbourne government database. Walk towards the black bubble’s direction, you will soon find the coffee shop.

How did we achieve the function? Without the help from the AR library like Google ARCore Library or Mapbox, we did two things to achieve the similar function with minimum error. Firstly, we retrieve the geo location from the phone's GPS sensor, afterwards, we use an algorithm to convert from World Geodetic System (longitude and latitude) to Earth-Centred, Earth-Fixed coordinates, then again from ECEF to East North up coordinates system which is what we called "screen space". After finding the anchor point (by using similar conversion as phone's coordinate) of the shop in the screen space, we use the gyroscope sensor to measure the movement of the camera and change the direction by adding the delta of gyroscope movement in x,y,z direction. That’s how we achieve the AR function.  

#### Game page
Last but not the least, the game page of the app. This page gives the user an option to pass his time while he travels to the coffee shop in a tram or simply waits while his coffee is being prepared. It presents the user with the retro game of snakes which is loved and played by all age groups alike. The game serves the purpose of keeping the user occupied and is a nice distraction.

To conclude, the find coffee app not only makes it easier to find nearby or top-rated coffee shops, it is set apart from similar apps through the provision of an AR functionality as well as a mini game to pass the time while waiting for coffee. It promises a fun packed experience in a single app. In future, we can also integrate a discussion forum where coffee lovers can connect and the AR functionality can be improvised further to include various forms of latte art.We hope you enjoyed our presentation for Find Coffee app. Thanks for watchi
