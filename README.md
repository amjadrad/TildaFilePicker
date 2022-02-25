**What's new**

Fix problem: 
- CursorIndexOutOfBoundsException


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
	        implementation 'com.github.amjadrad:TildaFilePicker:1.4'
}
```

3. Use in code
```
                TildaFilePicker tildaFilePicker = new TildaFilePicker(MainActivity.this , new FileType[]{FileType.FILE_TYPE_FILE , FileType.FILE_TYPE_MUSIC});
                tildaFilePicker.setSingleChoice();
                tildaFilePicker.setOnTildaFileSelectListener(list -> {});
                tildaFilePicker.show();
```
