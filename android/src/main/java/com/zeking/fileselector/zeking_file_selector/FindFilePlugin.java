package com.zeking.fileselector.zeking_file_selector;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

class FindFilePlugin implements MethodChannel.MethodCallHandler {

    public FlutterPlugin.FlutterPluginBinding flutterPluginBinding;

    /*扫描线程*/
    private Thread scanThread;
    /*定时器  用于定时检测扫描线程的状态*/
    private Timer scanTimer;

    /*检测扫描线程的任务*/
    private TimerTask scanTask;

    ArrayList<File> scanedFiles = new ArrayList<>();

    EventChannel eventChannel;

    EventChannel.EventSink eventSink;
    BinaryMessenger binaryMessenger;

//    public FindFilePlugin(BinaryMessenger binaryMessenger) {
//        this.binaryMessenger = binaryMessenger;
//        init();
//    }

    public FindFilePlugin(FlutterPlugin.FlutterPluginBinding flutterPluginBinding){
        this.flutterPluginBinding  = flutterPluginBinding;
        init();
    }

    public void init() {
        eventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), "zeking_file_selector/findFile_event");
        eventChannel.setStreamHandler(new EventChannel.StreamHandler() {
            @Override
            public void onListen(Object o, EventChannel.EventSink sink) {
                eventSink = sink;
                Log.d("Android", "EventChannel onListen called");
            }

            @Override
            public void onCancel(Object o) {
                Log.d("Android", "EventChannel onCancel called");

            }
        });
        Log.d("Android", "init 方法调用");
    }


    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {

        if (call.method.equals("findFile#getFiles")) {
            List<String> type = call.argument("type");
            startScan(type);
            result.success("开始查找文件");
        } else {
            result.notImplemented();
        }
    }


    // 开始扫描
    private void startScan(final List<String> endFilters) {
        // 根目录
        final String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();

        final File dir = new File(rootPath);

        scanThread = new Thread(new Runnable() {
            @Override
            public void run() {
                scanFile(dir, endFilters);
            }
        });


    /*判断扫描是否完成 其实就是个定时任务 时间可以自己设置
    每2s获取一下扫描线程的状态  如果线程状态为结束就说明扫描完成*/
        scanTimer = new Timer();
        scanTask = new TimerTask() {
            @Override
            public void run() {
//        Log.i("线程状态",scanThread.getState().toString());

                final ZekingFileSlectorResultModel model = new ZekingFileSlectorResultModel();

                if (!scanedFiles.isEmpty()) {
                    model.files = new ArrayList<>();
                    for (File file : scanedFiles) {
                        ZekingFileSelectionItem m = new ZekingFileSelectionItem();
                        m.fileName = file.getName();
                        m.filePath = file.getAbsolutePath();
                        m.fileSize = file.length();
                        m.fileDate = file.lastModified();
                        model.files.add(m);
                    }
                }

                if (scanThread.getState() == Thread.State.TERMINATED) {
                    model.isSearchEnd = true;
                    if(eventSink!=null){
                        Handler mainThread = new Handler(Looper.getMainLooper());
                        mainThread.post(new Runnable() {
                            @Override
                            public void run() {
                                String jsonString = new Gson().toJson(model);
                                eventSink.success(jsonString);
                            }
                        });
                    }

                    cancelTask();
                } else {

                    model.isSearchEnd = false;
                    if(eventSink!=null){
                        Handler mainThread = new Handler(Looper.getMainLooper());
                        mainThread.post(new Runnable() {
                            @Override
                            public void run() {
                                String jsonString = new Gson().toJson(model);
                                eventSink.success(jsonString);
                            }
                        });
                    }


                }
            }
        };
        scanTimer.schedule(scanTask, 0, 1000);
        scanedFiles.clear();
        /*开始扫描*/
        scanThread.start();

    }

   // 扫描
    private void scanFile(File dir, List<String> endFilters) {

        File[] files = dir.listFiles();

        if (files != null && files.length > 0) {
            for (final File file : files) {
                for (String endStr : endFilters) {
                    if (file.getName().toUpperCase().endsWith(endStr.toUpperCase())) {
                        scanedFiles.add(file);
//                        Log.i("x", "已扫描出" + scanedFiles.size() + "个文件");
                        break;
                    }
                }

                // 是目录
                if (file.isDirectory()) {
                    /*递归扫描*/
                    scanFile(file, endFilters);
                }
            }

        }
    }

    private void cancelTask() {
//        Log.i("cancelTask", "结束任务");
        if (scanTask != null) {
            scanTask.cancel();
        }

        if (scanTimer != null) {
            scanTimer.purge();
            scanTimer.cancel();
        }
    }
}
