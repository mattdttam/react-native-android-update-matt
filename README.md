
# react-native-android-update-matt

## Getting started

`$ npm install react-native-android-update-matt --save`

### Mostly automatic installation

`$ react-native link react-native-android-update-matt`

### Manual installation


#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.matt.rn.android.update.RNAndroidUpdateMattPackage;` to the imports at the top of the file
  - Add `new RNAndroidUpdateMattPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-android-update-matt'
  	project(':react-native-android-update-matt').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-android-update-matt/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-android-update-matt')
  	```


## Usage
```javascript
import RNAndroidUpdateMatt from 'react-native-android-update-matt';

// TODO: What to do with the module?
RNAndroidUpdateMatt;
```
  