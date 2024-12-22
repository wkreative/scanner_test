import 'package:masung_flutter/model/alignment_enum.dart';
import 'package:masung_flutter/masung_flutter_method_channel.dart';
import 'package:masung_flutter/model/under_line_enum.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

/// A platform interface for the Masung Flutter plugin.
///
/// This interface defines the methods that can be called on the Masung Flutter plugin
/// across different platforms (Android, iOS, etc.).
abstract class MasungFlutterPlatform extends PlatformInterface {
  /// Constructs a MasungFlutterPlatform.
  MasungFlutterPlatform() : super(token: _token);

  static final Object _token = Object();

  static MasungFlutterPlatform _instance = MethodChannelMasungFlutter();

  /// The default instance of [MasungFlutterPlatform] to use.
  ///
  /// Defaults to [MethodChannelMasungFlutter].
  static MasungFlutterPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [MasungFlutterPlatform] when
  /// they register themselves.
  static set instance(MasungFlutterPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  /// Retrieves the platform version.
  ///
  /// Returns a [Future] that completes with the platform version as a [String].
  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  /// Clears the cache and resets the formatting.
  ///
  /// Returns a [Future] that completes with a [bool] value indicating whether the cache was successfully cleared.
  /// Throws an [UnimplementedError] if the method has not been implemented.
  Future<bool?> clearCache() {
    throw UnimplementedError('clearCache() has not been implemented.');
  }

  /// Sets the margin, either by specifying the margin or by specifying the left and right margins.
  /// This margin applies to each character.
  /// Unit: 0.125mm
  /// Range: 0-576
  ///
  /// The [margin] parameter specifies the margin size.
  /// The [left] parameter specifies the left margin size.
  /// The [right] parameter specifies the right margin size.
  ///
  /// Returns a [Future] that completes with a [bool] value indicating whether the margin was successfully set.
  /// Throws an [UnimplementedError] if the method has not been implemented.
  Future<bool?> setMargin({int? margin, int? left = 0, int? right = 0}) {
    throw UnimplementedError('setMargin() has not been implemented.');
  }

  /// Sets the alignment.
  ///
  /// The [alignment] parameter specifies the alignment value.
  ///
  /// Returns a [Future] that completes with a [bool] value indicating whether the alignment was successfully set.
  /// Throws an [UnimplementedError] if the method has not been implemented.
  Future<bool?> setAlignment(AlignmentEnum alignment) {
    throw UnimplementedError('setAlignment() has not been implemented.');
  }

  /// Sets the font size, either by specifying the font size or by specifying the width and height.
  /// Range: 1-8
  ///
  /// The [fontSize] parameter specifies the font size.
  /// The [width] parameter specifies the width size.
  /// The [height] parameter specifies the height size.
  ///
  /// Returns a [Future] that completes with a [bool] value indicating whether the font size was successfully set.
  /// Throws an [UnimplementedError] if the method has not been implemented.
  Future<bool?> setFontSize({int? fontSize, int? width = 1, int? height = 1}) {
    throw UnimplementedError('setFontSize() has not been implemented.');
  }

  /// Sets the font bold.
  ///
  /// The [bold] parameter specifies whether the font should be bold.
  ///
  /// Returns a [Future] that completes with a [bool] value indicating whether the font bold was successfully set.
  /// Throws an [UnimplementedError] if the method has not been implemented.
  Future<bool?> setFontBold(bool bold) {
    throw UnimplementedError('setFontBold() has not been implemented.');
  }

  /// Sets the font underline.
  ///
  /// The [underline] parameter specifies the underline style.
  ///
  /// Returns a [Future] that completes with a [bool] value indicating whether the font underline was successfully set.
  /// Throws an [UnimplementedError] if the method has not been implemented.
  Future<bool?> setFontUnderline(UnderLineEnum underline) {
    throw UnimplementedError('setFontUnderline() has not been implemented.');
  }

  /// Sets the line space.
  /// Unit: 0.125mm
  /// Range: 0-127
  ///
  /// The [lineSpace] parameter specifies the line space size.
  ///
  /// Returns a [Future] that completes with a [bool] value indicating whether the line space was successfully set.
  /// Throws an [UnimplementedError] if the method has not been implemented.
  Future<bool?> setLineSpace(int lineSpace) {
    throw UnimplementedError('setLineSpace() has not been implemented.');
  }

  /// Sets the italic font.
  ///
  /// The [italic] parameter specifies whether the font should be italic.
  ///
  /// Returns a [Future] that completes with a [bool] value indicating whether the italic font was successfully set.
  /// Throws an [UnimplementedError] if the method has not been implemented.
  Future<bool?> setItalic(bool italic) {
    throw UnimplementedError('setItalic() has not been implemented.');
  }

  /// Prints a string.
  ///
  /// The [text] parameter specifies the text to be printed.
  /// The [newLine] parameter specifies whether a new line should be added after printing the text.
  ///
  /// Returns a [Future] that completes with a [bool] value indicating whether the string was successfully printed.
  /// Throws an [UnimplementedError] if the method has not been implemented.
  Future<bool?> printString(String text, {bool newLine = false}) {
    throw UnimplementedError('printString() has not been implemented.');
  }

  /// Feeds the paper by the specified number of lines.
  ///
  /// The [line] parameter specifies the number of lines to feed.
  ///
  /// Returns a [Future] that completes with a [bool] value indicating whether the paper was successfully fed.
  /// Throws an [UnimplementedError] if the method has not been implemented.
  Future<bool?> feedLine(int line) {
    throw UnimplementedError('feedLine() has not been implemented.');
  }

  /// Feeds the paper by the specified number of dots.
  ///
  /// The [dot] parameter specifies the number of dots to feed.
  ///
  /// Returns a [Future] that completes with a [bool] value indicating whether the paper was successfully fed.
  /// Throws an [UnimplementedError] if the method has not been implemented.
  Future<bool?> feedDot(int dot) {
    throw UnimplementedError('feedDot() has not been implemented.');
  }

  /// Cuts the paper.
  ///
  /// The [fullCut] parameter specifies whether a full cut should be performed.
  ///
  /// Returns a [Future] that completes with a [bool] value indicating whether the paper was successfully cut.
  /// Throws an [UnimplementedError] if the method has not been implemented.
  Future<bool?> cutPaper(bool fullCut) {
    throw UnimplementedError('cutPaper() has not been implemented.');
  }
}
