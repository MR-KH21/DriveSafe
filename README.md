# DriveSafe
Library detects if the user is driving a car or riding a bicycle ( using the awareness API ) and alert him/her.

# How to use :

* Add the liberary as a module to your android studio project.
* Add it as a module dependency to your app.
* Add the meta data to your app manifiest as below :

            <application ...
                    <meta-data
                           android:name="com.google.android.awareness.API_KEY"
                           android:value="-your awarness api key-"/>
            </application>

* In your Custom Application class call :

             DriveSafe.init(this);
     

The dev branch has a sample app please give it a look.


# Note :
The library is still young and many features to be added. Please report any bug you face and feel free to contact me if you have any question.
