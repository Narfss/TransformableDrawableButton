TransformableDrawableButton
=================

Animated button with transitions between symbols.

you need more explanation?


![](https://raw.githubusercontent.com/Narfss/TransformableDrawableButton/master/buttoncrossmenudemosvg.gif)


### Attributes ###
* unCheckDraw : Values "plus", "minus", "x", "arrow_down", "arrow_left", "arrow_up", "arrow_right". Default value "plus"
* checkDraw : Values "plus", "minus", "x", "arrow_down", "arrow_left", "arrow_up", "arrow_right". Default value "minus"
* strokeColor : Color value. Default value dark gray.
* backgroundColor : Color value. Default value white.

### Show me the code ###

```
#!java
    //add on fathers layout: xmlns:tdb="http://schemas.android.com/apk/res-auto"

    <com.fmsirvent.TransformableDrawableButton.library.TransformableButton
        android:layout_width="60dp"
        android:layout_height="60dp"
        android:layout_gravity="center"
        android:layout_margin="30dp"
        tdb:unCheckDraw="plus"
        tdb:checkDraw="x"
        tdb:strokeColor="@android:color/holo_red_dark"
        tdb:backgroundColor="@android:color/black"/>

```

## Author

Francisco M Sirvent narfss@gmail.com
Linkedin: es.linkedin.com/in/fmsirvent/


Based on HamburgerMenuDrawable, by:
Thibault Guégan, thibault.guegan@gmail.com
Linkedin: https://www.linkedin.com/profile/view?id=93515047

## License

TransformableDrawableButton is available under the MIT license. See the LICENSE file for more info.
