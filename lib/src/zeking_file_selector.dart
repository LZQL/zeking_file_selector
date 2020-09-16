import 'dart:async';
import 'dart:convert';
import 'dart:io';

import 'package:flutter/services.dart';

import '../zeking_file_selector.dart';

class ZekingFileSelector {
  static const MethodChannel _channel =
      const MethodChannel('zeking_file_selector/findFile');

  static const _resultEventChannel =
      EventChannel('zeking_file_selector/findFile_event');

  // 调用原生 得到文件+文件信息
  static Stream<dynamic> getFilesAndroid(
//  static Future<List<ZekingFileUtilModel>> getFilesAndroid(
      List<String> fileTypeEnd) {
    Map<String, Object> map = {
      "type": fileTypeEnd ?? [".pdf", ".docx", ".doc", ".xlsx"]
    };

    _channel.invokeMethod('findFile#getFiles', map);

    // _resultEventChannel.receiveBroadcastStream()
    //   ..map((result) => result as String)
    //    .map((resultJson){
    //     ZekingFileSelectorResultModel.fromJson(jsonDecode(resultJson)
    //   });

    // _resultEventChannel.receiveBroadcastStream().listen((dynamic event) {
    //   print('Received event: $event');
    // }, onError: (dynamic error) {
    //   print('Received error: ${error.message}');
    // }, cancelOnError: true);
    //
    return _resultEventChannel.receiveBroadcastStream();

    // _resultEventChannel.receiveBroadcastStream().listen((event) { })
  }
}
