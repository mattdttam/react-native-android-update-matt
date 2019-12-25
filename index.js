'use strict';
import {
    NativeModules,
    ToastAndroid,
    PermissionsAndroid
} from 'react-native'

class AndroidUpdate {
    update(url="",version="",options={}) {
		if(url.length <= 0){
			ToastAndroid.show("更新地址不能为空", ToastAndroid.SHORT);
			return;
		}
		if(version.length <= 0) {
			ToastAndroid.show("当前版本不能为空", ToastAndroid.SHORT);
			return;
		}
		PermissionsAndroid.request(
			PermissionsAndroid.PERMISSIONS.WRITE_EXTERNAL_STORAGE,
			{
			  'title': '获取读写权限',
			  'message': '请授权读写权限'
			}
		  ).then((granted)=>{
			if (granted === PermissionsAndroid.RESULTS.GRANTED) {
				NativeModules.RNAndroidUpdateMatt.update(url,version,options);
			}else{
				ToastAndroid.show("获取读写权限失败", ToastAndroid.SHORT);
			}
		  }).catch((err)=>{
			ToastAndroid.show(err.toString(), ToastAndroid.SHORT);
		  });
	}
}
export default new AndroidUpdate();