![cupcake image](app/src/main/res/mipmap-xxhdpi/ic_launcher.png)


LukeGFerguson take on [Google Codelab practice application Cupcake](https://github.com/google-developer-training/android-basics-kotlin-cupcake-app)


Demonstrates Android development skills, including:

+ Single activity architecture using fragments for each screen 
+ NavGraph to navigate through fragments, including custom backstack behavior 
+ LiveData data type
+ DataBinding to update values and implement button behavior
+ SharedViewModel to record data from each fragment in one persistent store
+ Adding functionality beyond the Codelab while maintaining consistent visual look. Added functionality:
    + Combine functionality of selecting flavor and quantity into single fragment
    + Allow user to order multiple flavors
    + Add flexibility in cupcake quantity (original app only allowed 1, 6, or 12)
    + Add an additional fragment for recording user's name and phone number
    + Display name/number and multiple flavors on summary fragment
    + Include name/number and multiple flavors in email generated through implicit intent
    + Format phone number and handle singular/plural headers (flavor/s, cupcake/s)