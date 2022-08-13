ColorSlider Library
=====================

Lightweight color-picker library for Android.

[![](https://jitpack.io/v/emkayDauda/ColorSlider.svg)](https://jitpack.io/#emkayDauda/ColorSlider)



Screenshots
=====================

![Slider in action](https://docs.google.com/uc?export=download&id=1V4dkOnsWjn9Fc8FNiI1pbXUgvjV-cMoZ)

Getting started
=====================

**Adding library**

_1) Using JitPack_

Add maven url to root level build.gradle file.

```
    allprojects {
    	repositories {
    		...
    		maven { url 'https://jitpack.io' }
    	}
    }
}
```

_2) Import module to your project level build.gradle file_

```
	dependencies {
	    ...
	    implementation 'com.github.emkayDauda:ColorSlider:$current_version'
	}

```

**Using library**

_1) Create String Array of colors in strings.xml file_

```
<resources>
    //other strings
    ...

    <string-array name="slider_colors">
        <item>#E57373</item>
        <item>#BA68C8</item>
        <item>#64B5F6</item>
        <item>#81C784</item>
        <item>#FFF176</item>
        <item>#FFB74D</item>
        <item>#90A4AE</item>
        <item>#454849</item>
    </string-array>
</resources>

```

_2) Add ColorSlider to layout_

```
<com.emkaydauda.colorslider.ColorSlider
        android:id="@+id/colorSelector"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginEnd="20dp"
        android:layout_marginStart="20dp"
        app:colors="@array/slider_colors"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/colorSelectorLabel"/>
```

_3) Finally, listen for color changes in activity_

```
    colorSelector.addListener { color ->
        someView.setBackgroundColor(color)
    }
```

Examples
=====================

Complete code for the app featured in screenshots is available in the `app` folder
