import 'dart:convert';

import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:zeking_file_selector/zeking_file_selector.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  List<ZekingFileModel> files = [];
  bool isSearchEnd = false;

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Container(
          child: Column(
            children: [
              RaisedButton(
                onPressed: () {
                  ZekingFileSelector.getFilesAndroid(null).listen((event) {
                    ZekingFileSelectorResultModel model =
                        ZekingFileSelectorResultModel.fromJson(
                            jsonDecode(event));
                    setState(() {
                      this.isSearchEnd = model.isSearchEnd;
                      this.files = model.files;
                    });
                  });
                },
                child: Text('开始获取'),
              ),
              Text(!isSearchEnd
                  ? '正在搜索，已经搜多到${files?.length}个文件'
                  : '搜索完成${files?.length}个文件'),
              Expanded(
                child: ListView.builder(
                  padding: EdgeInsets.only(top: 0),
                    shrinkWrap:true,
                  itemBuilder: (context, index) {
                    return buildItem(index);
                  },
                  itemCount:
                      files == null || files.length == 0 ? 0 : files.length,
                ),
              )
            ],
          ),
        ),
      ),
    );
  }

  Widget buildItem(int index) {
    return Container(
      height: 45,
      child: Expanded(child: Text(files[index].fileName)),
    );
  }
}
