# zeking_file_selector

## 1. 介绍

根据文件后缀 获取 指定的文件，只支持Android。
如果要支持IOS请使用插件 file_picker （IOS只能调用原生的文件app来进行过滤）


## 2. 使用：

```

ZekingFileSelector.getFilesAndroid([".pdf",'.doc','.docx']).listen((event) {

   ZekingFileSelectorResultModel model =
           ZekingFileSelectorResultModel.fromJson(
                jsonDecode(event));

});
```

如果 getFilesAndroid 的参数传null，默认是 ".pdf", ".docx", ".doc",".xlsx"

## 3. 注意：

这个插件没有包含权限申请，请先自己申请权限