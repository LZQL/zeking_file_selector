import 'dart:io';

/// file信息实体
class ZekingFileUtilModel{
  File file;
  String fileName;
  int fileSize;
  String filePath;
  int fileDate;
  ZekingFileUtilModel(
      {
        this.fileDate,
        this.fileName,
        this.filePath,
        this.fileSize,
        this.file,
      });
}