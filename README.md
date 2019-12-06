# react-native-android-update-matt

## Getting started

`$ npm install react-native-android-update-matt --save`

### Mostly automatic installation

`$ react-native link react-native-android-update-matt`

### Manual installation


#### Android

1. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-android-update-matt'
  	project(':react-native-android-update-matt').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-android-update-matt/android')
  	```
2. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      implementation project(':react-native-android-update-matt')
  	```


## Usage
```javascript
import AndroidUpdate from 'react-native-android-update-matt';

componentDidMount() {
    this.upgradeTimer = setTimeout(() => {
      let url = x;
      let version = y;
      let token = z;
      AndroidUpdate.update(url, version, token);
    }, 500);
  }

  componentWillUnmount() {
    clearTimeout(this.upgradeTimer);
    this.upgradeTimer = null;
  }
```
  