TransformableDrawableButton
=================

Animated button with transitions between symbols.

you need more explanation?

Round buttons Example.

![](https://raw.githubusercontent.com/Narfss/TransformableDrawableButton/master/draw_round_buttons_example.gif)

Drawable on a side of button.

![](https://raw.githubusercontent.com/Narfss/TransformableDrawableButton/master/draw_sides_buttons_example.gif)


### Show me the code ###

Gradle dependencies:

```groovy
compile 'com.fmsirvent:transformabledrawablebutton:1.0.0'
```

Code in layout:

```xml
<!-- add on fathers layout: xmlns:tdb="http://schemas.android.com/apk/res-auto" -->

<com.fmsirvent.transformabledrawablebutton.TransformableButton
    android:layout_width="60dp"
    android:layout_height="60dp"
    android:layout_gravity="center"
    android:layout_margin="30dp"
    tdb:unCheckDraw="plus"
    tdb:checkDraw="x"
    tdb:strokeColor="@android:color/holo_red_dark"
    tdb:backgroundColor="@android:color/black"/>
```


### Attributes ###
* **unCheckDraw** : Values "plus", "minus", "x", "arrow_down", "arrow_left", "arrow_up", "arrow_right", "check". Default value "plus"
* **checkDraw** : Values "plus", "minus", "x", "arrow_down", "arrow_left", "arrow_up", "arrow_right", "check". Default value "minus"
* **strokeColor** : Color value. Default value dark gray.
* **backgroundColor** : Color value. Default value white.
* **drawablePosition** : Values "background", "left", "top", "right", "bottom". Default value "background".

### TODO ###
* Check function
* Check listener
* Improve animations.

## Author

Francisco M Sirvent narfss@gmail.com
Linkedin: https://es.linkedin.com/in/fmsirvent/


Based on HamburgerMenuDrawable, by:
Thibault Guégan, thibault.guegan@gmail.com
Linkedin: https://www.linkedin.com/profile/view?id=93515047

## License

TransformableDrawableButton is available under the MIT license. See the LICENSE file for more info.
