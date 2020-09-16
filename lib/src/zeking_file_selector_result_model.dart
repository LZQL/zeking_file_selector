class ZekingFileSelectorResultModel {
  List<ZekingFileModel> files;
  bool isSearchEnd;

  ZekingFileSelectorResultModel({this.files, this.isSearchEnd});

  ZekingFileSelectorResultModel.fromJson(Map<String, dynamic> json) {
    if (json['files'] != null) {
      files = new List<ZekingFileModel>();
      json['files'].forEach((v) {
        files.add(new ZekingFileModel.fromJson(v));
      });
    }
    isSearchEnd = json['isSearchEnd'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    if (this.files != null) {
      data['files'] = this.files.map((v) => v.toJson()).toList();
    }
    data['isSearchEnd'] = this.isSearchEnd;
    return data;
  }
}

class ZekingFileModel {
  int fileDate;
  String fileName;
  String filePath;
  int fileSize;

  ZekingFileModel({this.fileDate, this.fileName, this.filePath, this.fileSize});

  ZekingFileModel.fromJson(Map<String, dynamic> json) {
    fileDate = json['fileDate'];
    fileName = json['fileName'];
    filePath = json['filePath'];
    fileSize = json['fileSize'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['fileDate'] = this.fileDate;
    data['fileName'] = this.fileName;
    data['filePath'] = this.filePath;
    data['fileSize'] = this.fileSize;
    return data;
  }
}
