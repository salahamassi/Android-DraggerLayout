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

# Video

[![Adnroid activity Pull Down to dismiss
](https://user-images.githubusercontent.com/17902030/41181914-37e8bc24-6b7c-11e8-9b75-362da6b30c09.png)](https://www.youtube.com/watch?v=KwGH2_iqMBo "Adnroid activity Pull Down to dismiss
")

# Thanks To
   * [ViewDragHelper](https://developer.android.com/reference/android/support/v4/widget/ViewDragHelper)
   * [Fedepaol Blog](http://fedepaol.github.io/blog/2014/09/01/dragging-with-viewdraghelper/)

# Developer contact 
   * [Facebook](https://www.facebook.com/profile.php?id=100006656534009)
   * [Twitter](https://twitter.com/salahamassi)
