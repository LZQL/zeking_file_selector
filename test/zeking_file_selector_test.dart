import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:zeking_file_selector/zeking_file_selector.dart';

void main() {
  const MethodChannel channel = MethodChannel('zeking_file_selector');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await ZekingFileSelector.getFilesAndroid(null), '42');
  });
}
