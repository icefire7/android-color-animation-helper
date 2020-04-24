# android-color-animation-helper
An amazing helper class for color animation that can be easily used with another translation (or scale or whatever) animations.

Usage:
```
val colorAnimationHelper = ColorAnimationHelper( 
      ContextCompat.getColor(context, R.color.fromColor), 
      ContextCompat.getColor(context, R.color.toColor) 
    )
//interpolatedTime is float value obtained in any type of animation
yourView.setCardBackgroundColor(colorAnimationHelper.getColor(interpolatedTime))
```
