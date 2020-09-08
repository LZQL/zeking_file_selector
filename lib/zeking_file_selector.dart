import 'dart:async';
import 'dart:io';

import 'package:flutter/services.dart';

import 'zeking_file_util_model.dart';
export 'zeking_file_util_model.dart';

class ZekingFileSelector {
  static const MethodChannel _channel =
      const MethodChannel('zeking_file_selector');

//  static Future<String> get platformVersion async {
//    final String version = await _channel.invokeMethod('getPlatformVersion');
//    return version;
//  }

  static List<ZekingFileUtilModel> list = [];

  // 调用原生 得到文件+文件信息
  static Future<List<ZekingFileUtilModel>> getFilesAndroid(
      List<String> fileTypeEnd) async {


    Map<String, Object> map = {
      "type": fileTypeEnd ?? [".pdf", ".docx", ".doc",".xlsx"]
    };

    List<dynamic> listFileStr =
        await _channel.invokeMethod('getFile', map);

    /// 如果原生返回空 return掉
    if (listFileStr == null || listFileStr.length == 0) {
      return null;
    }
    list.clear();
    listFileStr.forEach((f) {
      list.add(ZekingFileUtilModel(
        fileDate: f["fileDate"],
        fileName: f["fileName"],
        filePath: f["filePath"],
        fileSize: f["fileSize"],
        file: File(f["filePath"]),
      ));
    });

    return list;
  }
}
