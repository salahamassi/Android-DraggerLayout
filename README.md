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
