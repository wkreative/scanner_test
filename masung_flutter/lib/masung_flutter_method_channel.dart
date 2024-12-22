import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:masung_flutter/model/alignment_enum.dart';
import 'package:masung_flutter/model/under_line_enum.dart';

import 'masung_flutter_platform_interface.dart';

/// An implementation of [MasungFlutterPlatform] that uses method channels
///  to communicate with the native Android Masung printer library.
class MethodChannelMasungFlutter extends MasungFlutterPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('masung_flutter');

  /// Retrieves the platform version from the native platform.
  ///
  /// Returns a [Future] that completes with a [String] representing the platform version.
  @override
  Future<String?> getPlatformVersion() async {
    final version =
        await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  @override
  Future<bool?> clearCache() async {
    return await methodChannel.invokeMethod<bool>('clearCache');
  }

  @override
  Future<bool?> setMargin({int? margin, int? left = 0, int? right = 0}) async {
    if (margin != null) {
      return await methodChannel.invokeMethod<bool>('setMargin', {
        'left': margin,
        'right': margin,
      });
    } else {
      return await methodChannel.invokeMethod<bool>('setMargin', {
        'left': left,
        'right': right,
      });
    }
  }

  @override
  Future<bool?> setAlignment(AlignmentEnum alignment) async {
    return await methodChannel.invokeMethod<bool>('setAlignment', {
      'alignment': alignment.value,
    });
  }

  @override
  Future<bool?> setFontSize(
      {int? fontSize, int? width = 1, int? height = 1}) async {
    if (fontSize != null) {
      return await methodChannel.invokeMethod<bool>('setTextSize', {
        'width': fontSize,
        'height': fontSize,
      });
    } else {
      return await methodChannel.invokeMethod<bool>('setTextSize', {
        'width': width,
        'height': height,
      });
    }
  }

  @override
  Future<bool?> setFontBold(bool bold) async {
    return await methodChannel.invokeMethod<bool>('setBold', {
      'bold': bold,
    });
  }

  @override
  Future<bool?> setFontUnderline(UnderLineEnum underline) async {
    return await methodChannel.invokeMethod<bool>('setUnderline', {
      'underline': underline.value,
    });
  }

  @override
  Future<bool?> setLineSpace(int lineSpace) async {
    return await methodChannel.invokeMethod<bool>('setLineSpacing', {
      'lineSpace': lineSpace,
    });
  }

  @override
  Future<bool?> setItalic(bool italic) async {
    return await methodChannel.invokeMethod<bool>('setItalic', {
      'italic': italic,
    });
  }

  @override
  Future<bool?> printString(String text, {bool newLine = false}) {
    return methodChannel.invokeMethod<bool>('printString', {
      'text': text,
      'newLine': newLine,
    });
  }

  @override
  Future<bool?> feedLine(int line) async {
    return await methodChannel.invokeMethod<bool>('feedLine', {
      'line': line,
    });
  }

  @override
  Future<bool?> feedDot(int dot) {
    return methodChannel.invokeMethod<bool>('feedDot', {
      'dot': dot,
    });
  }

  @override
  Future<bool?> cutPaper(bool fullCut) async {
    return await methodChannel.invokeMethod<bool>('cutPaper', {
      'fullCut': fullCut,
    });
  }
}
