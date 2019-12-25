package com.matt.rn.android.update;

import android.util.Log;
import android.widget.Toast;

import com.allenliu.versionchecklib.core.http.HttpHeaders;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.RequestVersionBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

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
  public void update(final String url, final String version,
                     final ReadableMap map) {

    RequestVersionBuilder builder = AllenVersionChecker.getInstance().requestVersion().setRequestUrl(url);
    if(map != null && map.hasKey("token")) {
      String token = map.getString("token");
      if(token != null) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("accessToken", token);
        builder.setHttpHeaders(headers);
      }
    }
    builder.request(new RequestVersionListener() {
      @Override
      public UIData onRequestVersionSuccess(DownloadBuilder downloadBuilder, String response) {
        Log.d("rn-android-update-matt", response);
        downloadBuilder.setShowDownloadingDialog(true);
        downloadBuilder.setShowNotification(true);
        downloadBuilder.setForceRedownload(false);
        try {
          JSONObject jsRes = new JSONObject(response);
          // JSONObject meta = jsRes.getJSONObject("meta");
          if (jsRes.getBoolean("success")) {
            JSONObject data = jsRes.getJSONObject("result");
            String latestVersion = data.getString("version");
            if (CommonUtils.compareAppVersion(latestVersion, version) > 0) {
              String dlUrl = data.getString("url");
              if(map != null && map.hasKey("dlUrlPrefix")) {
                String dlUrlPrefix = map.getString("dlUrlPrefix");
                if(dlUrlPrefix != null) {
                  dlUrl = dlUrlPrefix + dlUrl;
                }
              }
              UIData uiData = UIData.create().setDownloadUrl(dlUrl)
                  .setTitle("安装新版本" + data.getString("version"))
                      .setContent(data.getString("description"));
              return uiData;
            }
          }
        } catch (Exception e) {
          Log.d("rn-android-update-matt", e.getMessage());
        }
        return null;
      }

      @Override
      public void onRequestVersionFailure(String message) {
        Toast.makeText(reactContext, message, Toast.LENGTH_SHORT).show();
      }
    }).executeMission(getReactApplicationContext());
  }
}