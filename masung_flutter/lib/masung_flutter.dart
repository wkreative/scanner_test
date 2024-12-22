import 'package:masung_flutter/model/alignment_enum.dart';
import 'package:masung_flutter/masung_flutter_method_channel.dart';
import 'package:masung_flutter/model/under_line_enum.dart';

/// A class that provides methods for interacting with the Masung Flutter plugin.
class MasungFlutter {
  final MethodChannelMasungFlutter _channel = MethodChannelMasungFlutter();

  /// Retrieves the platform version.
  ///
  /// Returns a [Future] that completes with a [String] representing the platform version.
  Future<String?> getPlatformVersion() {
    return _channel.getPlatformVersion();
  }

  /// Clears the cache.
  ///
  /// Returns a [Future] that completes with a [bool] value indicating whether the cache was successfully cleared.
  Future<bool?> clearCache() {
    return _channel.clearCache();
  }

  /// Sets the margin for each carachter.
  /// - Unit: 0.125mm
  /// - Range: 0~576
  ///
  /// The[margin] parameter specifies the overall margin, while the [left] and [right] parameters specify the left and right margins respectively.
  /// Returns a [Future] that completes with a [bool] value indicating whether the margin was successfully set.
  Future<bool?> setMargin({int? margin, int? left = 0, int? right = 0}) {
    return _channel.setMargin(margin: margin, left: left, right: right);
  }

  /// Sets the alignment for the text.
  ///
  /// The [alignment] parameter specifies the alignment of the text.
  /// Returns a [Future] that completes with a [bool] value indicating whether the alignment was successfully set.
  Future<bool?> setAlignment(AlignmentEnum alignment) {
    return _channel.setAlignment(alignment);
  }

  /// Sets the font size for the text.
  /// - Range: 1~8
  ///
  /// The [fontSize] parameter specifies the font size, while the [width] and [height] parameters specify the width and height scaling factors respectively.
  /// Returns a [Future] that completes with a [bool] value indicating whether the font size was successfully set.
  Future<bool?> setFontSize({int? fontSize, int? width = 1, int? height = 1}) {
    return _channel.setFontSize(
        fontSize: fontSize, width: width, height: height);
  }

  /// Sets the font style to bold or normal.
  ///
  /// The [bold] parameter specifies whether the font should be bold.
  /// Returns a [Future] that completes with a [bool] value indicating whether the font style was successfully set.
  Future<bool?> setFontBold(bool bold) {
    return _channel.setFontBold(bold);
  }

  /// Sets the underline style for the text.
  ///
  /// The [underline] parameter specifies the underline style.
  /// Returns a [Future] that completes with a [bool] value indicating whether the underline style was successfully set.
  Future<bool?> setFontUnderline(UnderLineEnum underline) {
    return _channel.setFontUnderline(underline);
  }

  /// Sets the line spacing for the text.
  /// - Unit: 0.125mm
  /// - Range: 0~127
  ///
  /// The [lineSpace] parameter specifies the line spacing.
  /// Returns a [Future] that completes with a [bool] value indicating whether the line spacing was successfully set.
  Future<bool?> setLineSpace(int lineSpace) {
    return _channel.setLineSpace(lineSpace);
  }

  /// Sets the font style to italic or normal.
  ///
  /// The [italic] parameter specifies whether the font should be italic.
  /// Returns a [Future] that completes with a [bool] value indicating whether the font style was successfully set.
  Future<bool?> setItalic(bool italic) {
    return _channel.setItalic(italic);
  }

  /// Prints a string of text.
  ///
  /// The [text] parameter specifies the text to be printed, while the [newLine] parameter specifies whether a new line should be added after printing the text.
  /// Returns a [Future] that completes with a [bool] value indicating whether the text was successfully printed.
  Future<bool?> printString(String text, {bool newLine = false}) {
    if (newLine) return _channel.printString(text, newLine: newLine);
    return _channel.printString(text);
  }

  /// Feeds the paper by the specified number of lines.
  ///
  /// The [line] parameter specifies the number of lines to feed.
  /// Returns a [Future] that completes with a [bool] value indicating whether the paper was successfully fed.
  Future<bool?> feedLine(int line) {
    return _channel.feedLine(line);
  }

  /// Feeds the paper by the specified number of dots.
  ///
  /// The [dot] parameter specifies the number of dots to feed.
  /// Returns a [Future] that completes with a [bool] value indicating whether the paper was successfully fed.
  Future<bool?> feedDot(int dot) {
    return _channel.feedDot(dot);
  }

  /// Cuts the paper.
  ///
  /// The [fullCut] parameter specifies whether a full cut should be performed.
  /// Returns a [Future] that completes with a [bool] value indicating whether the paper was successfully cut.
  Future<bool?> cutPaper(bool fullCut) {
    return _channel.cutPaper(fullCut);
  }
}
