package com.example.proxy_setting;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

import java.util.HashMap;
import java.util.Map;
import android.net.ProxyInfo;

import android.net.ConnectivityManager;
import android.os.Build;
import android.content.Context;


/** ProxySettingPlugin */
public class ProxySettingPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private ConnectivityManager manager;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "proxy_setting");
    channel.setMethodCallHandler(this);
    manager = (ConnectivityManager)flutterPluginBinding.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getProxySettings")) {
      getNetworkType(manager, result);
    } else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  private void getNetworkType(ConnectivityManager manager,  Result result) {
    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      ProxyInfo defaultProxy = manager.getDefaultProxy();

      if (defaultProxy != null) {
        Map map = new HashMap<String, String>();
        map.put("host", defaultProxy.getHost());
        map.put("port", Integer.toString(defaultProxy.getPort()));
        result.success(map);
        return;
      }
    }
    result.success(null);
  }

}
