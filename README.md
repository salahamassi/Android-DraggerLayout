# Android DraggerLayout

# What is it? 

Simple kotlin class provide android activty ability to close by swipe down (pull to down).


# How to use it? 

copy dragger layout class to your project and then inside your activity xml file put dragerLayout as a parent layout.

dragerLayout like scroll view must have just one directly child.

Your Xml will look like: 

```xml
<DraggerLayout
   xmlns:android="http://schemas.android.com/apk/res/android"
   android:layout_width="match_parent"
   android:layout_height="match_parent">
    
   <android.support.constraint.ConstraintLayout
       android:id="@+id/container"
       android:layout_width="match_parent"
       android:layout_height="match_parent"
       android:background="@android:color/white">

         <!-- put your views here -->
   <android.support.constraint.ConstraintLayout/>
  
</DraggerLayout>

```

# ScreenShot

![swip](https://user-images.githubusercontent.com/17902030/41172834-c1eb88c6-6b5d-11e8-97fd-729037d85c4d.gif)

# Developer contact 
   * [Facebook](https://www.facebook.com/profile.php?id=100006656534009)
   * [Twitter](https://twitter.com/salahamassi)
