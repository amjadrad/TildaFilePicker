# Tilda File Picker

**How To Use.**

1. Add it in your root build.gradle at the end of repositories:

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

2. Add the dependency

```
dependencies {
	        implementation 'com.github.amjadrad:TildaFilePicker:1.0'
}
```

3. Use in code
```
                TildaFilePicker tildaFilePicker = new TildaFilePicker(MainActivity.this);
                tildaFilePicker.setOnTildaFileSelectListener(list -> {});
                tildaFilePicker.show();
```