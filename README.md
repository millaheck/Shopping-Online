# Shopping-Online
ONLINE SHOPPING APP

1.	AUTHENTICATION
In the first part, authentication was performed where the user can enter his username
and password and register in the online shopping application.

2.	PRODUCT LISTING
To develop this part, first the recycler view dependencies were taken from Android
website and placed in the build.gradle(:app) file in Android Studio. Then the design was
created using xml files. The activity_product_categories.xml file was created where
there is the recycler view to create the list of categories. The viewholder_category.xml files
and viewholder_product.xml were created to build an item list cell.

To make this list dynamic it was necessary to create the logical part of it that would give
framework for everything to work. It was written in the ProductCategoriesActivity. Then the
Adapters (ProductAdapter, CategoryAdapter and CartListAdapter) were created, they are
responsible for managing all dynamic items and taking each item from them, each view
responsible, manages these specific visualization support projects. These dynamic collection 
lists will be inside the adapter and the adapter will define that every time a recyclable item
comes regardless of the position of the cell if it is item 10 or 30 it will put this
specific information within this viewholder.

3.	CART
To make this list it was necessary to create a recycler view, an adapter and also the layout
file where we can see the cart summary, the total amount and the purchase button to place a
order.

4.	ORDER HISTORY AND 5. USER DETAILS
This I created in the first version of the app I was developing that can be seen at
millaheck/Shopping-Online, in the main branch with the description "Shopping". But I also had
many problems with this file, then I decided to restart again and created the latest version
located at millaheck/Shopping-Online, at the branch called "App_My_Shopping_Online" with
the description "My shopping" and "Assignment Mobile App 1 - My online purchases". I tried
send it to the main branch, but I couldn't, so I created another branch.
I left the old file on GitHub even though I uploaded a new version because it represents the entire
process and the effort put into it, I had other files and tries but I didn't upload everything on
GitHub, because I didn't get that far into the projects with these files.
The old version of the project is more complete (it has all the requested items) than the one I 
am sending last, however, the application was not running. I asked some classmates if they could 
help me, I researched, but it was unsuccessful. Then I decided to start a new project.

6. UI/IMPLEMENTATIONAL REQUIREMENTS
RecyclerView was used for all lists: categories, products, orders. If logged in, there was
an authentication token for all requests until logout.

CONCLUSION
I had a lot of difficulties doing the project but I learned a lot doing it, researching to find 
out how to solve the problems I had during the process. I'm happy with the result,
I gave the best I could according to my possibilities of time (deadline) and knowledge.




