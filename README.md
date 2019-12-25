# react-native-android-update-matt

## Getting started

`$ npm install react-native-android-update-matt --save`

### Mostly automatic installation

`$ react-native link react-native-android-update-matt`

### Manual installation


#### Android

1. Open up `android/app/src/main/java/[...]/MainApplication.java`
  - Add `import com.matt.rn.android.update.RNAndroidUpdateMattPackage;` to the imports at the top of the file
  - Add `new RNAndroidUpdateMattPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-android-update-matt'
  	project(':react-native-android-update-matt').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-android-update-matt/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
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

```java server
@RequestMapping("/appUpdate")
public Response appUpdate() {
    Map map = new HashMap();
    map.put("version", "x.x.x");
    map.put("description", new String[] {"xxx","xxx");
    map.put("url", "http://xxx.xxx.xxx.xxx/xx.apk");
    return new Response().success(map);
} 

Response from server:
-----

{
  success: true,
  result {
    version: 'x.x.x',
    description: ["xxx","xxx"],
    url: 'http://xxx.xxx.xxx.xxx/xx.apk'
  }
}

```
  