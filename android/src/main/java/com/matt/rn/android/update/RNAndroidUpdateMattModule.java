
package com.matt.rn.android.update;

import android.support.annotation.Nullable;
import android.util.Log;

import com.allenliu.versionchecklib.core.http.HttpHeaders;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import org.json.JSONException;
import org.json.JSONObject;

public class RNAndroidUpdateMattModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNAndroidUpdateMattModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNAndroidUpdateMatt";
  }

  @ReactMethod
  public void update(final String url, final String version, final String token) {
      HttpHeaders headers = new HttpHeaders();
      headers.put("token", token);
      AllenVersionChecker
            .getInstance()
            .requestVersion()
            .setRequestUrl(url)
            .setHttpHeaders(headers)
            .request(new RequestVersionListener() {

              @Nullable
              @Override
              public UIData onRequestVersionSuccess(DownloadBuilder downloadBuilder, String response) {
                Log.d("rn-android-update-matt",response);
                downloadBuilder.setShowDownloadingDialog(true);
                downloadBuilder.setShowNotification(true);
                downloadBuilder.setForceRedownload(false);
                try {
                  JSONObject jsRes = new JSONObject(response);
                  JSONObject meta = jsRes.getJSONObject("meta");
                  if(meta.getBoolean("success")) {
                      JSONObject data = jsRes.getJSONObject("data");
                      String latestVersion = data.getString("version");
                      if(CommonUtils.compareAppVersion(latestVersion, version) > 0) {
                          UIData uiData = UIData
                                  .create()
                                  .setDownloadUrl(data.getString("url"))
                                  .setTitle("安装新版本" + data.getString("version"))
                                  .setContent(data.getString("description"));
                          return uiData;
                      }
                  }
                } catch (JSONException e) {
                  Log.d("rn-android-update-matt",e.getMessage());
                }
                return null;
              }

              @Override
              public void onRequestVersionFailure(String message) {

              }
            })
            .executeMission(getReactApplicationContext());
  }
}