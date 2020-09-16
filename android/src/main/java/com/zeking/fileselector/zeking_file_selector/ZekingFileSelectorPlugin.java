package com.zeking.fileselector.zeking_file_selector;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * ZekingFileSelectorPlugin
 */
public class ZekingFileSelectorPlugin implements FlutterPlugin, MethodCallHandler {


    private static MethodChannel channel;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {

//        FlutterEngine flutterEngine = new  FlutterEngine(flutterPluginBinding.getApplicationContext());
//        flutterEngine.getDartExecutor().getBinaryMessenger();


        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "zeking_file_selector/findFile");
        channel.setMethodCallHandler(new FindFilePlugin(flutterPluginBinding));
    }

    public static void registerWith(Registrar registrar) {
//        channel = new MethodChannel(registrar.messenger(), "zeking_file_selector/findFile");
//        channel.setMethodCallHandler(new FindFilePlugin(registrar.messenger()));
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {

    }
}
