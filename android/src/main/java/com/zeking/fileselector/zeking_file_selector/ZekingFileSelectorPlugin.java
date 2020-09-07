package com.zeking.fileselector.zeking_file_selector;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** ZekingFileSelectorPlugin */
public class ZekingFileSelectorPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private static Context context;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    context = flutterPluginBinding.getApplicationContext();
    channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "zeking_file_selector");
    channel.setMethodCallHandler(this);
  }

  // This static function is optional and equivalent to onAttachedToEngine. It supports the old
  // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
  // plugin registration via this function while apps migrate to use the new Android APIs
  // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
  //
  // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
  // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
  // depending on the user's project. onAttachedToEngine or registerWith must both be defined
  // in the same class.
  public static void registerWith(Registrar registrar) {
    context = registrar.activeContext();
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "zeking_file_selector");
    channel.setMethodCallHandler(new ZekingFileSelectorPlugin());
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
//    if (call.method.equals("getPlatformVersion")) {
//      result.success("Android " + android.os.Build.VERSION.RELEASE);
//    } else {
//      result.notImplemented();
//    }
    if (call.method.equals("getFile")) {

      Log.d("安卓端接收参数：",call.arguments.toString());

      // 接收的参数
      List<String> type = call.argument("type");

      // 返回的参数
      List<Map> listMap = new ArrayList<>();

      try{
        // 得到对应类型文件  type.toArray(new String[type.size()])转换类型
        List<String> list   = FileUtilFlutter.getTypeOfFile(context, type.toArray(new String[type.size()]));
        File f ;
        for(String item : list){
          f = new File(item);
          // 拼接参数
          Map<String,Object> m = new HashMap();
          m.put("fileName",f.getName());
          m.put("filePath",f.getAbsolutePath());
          m.put("fileSize",f.length());
          m.put("fileDate",f.lastModified());
          listMap.add(m);
        }
      }finally {
        // 返回给flutter
        result.success(listMap);
      }

    } else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
}