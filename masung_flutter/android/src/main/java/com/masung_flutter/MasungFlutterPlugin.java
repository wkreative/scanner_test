package com.masung_flutter;

import android.content.Context;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/**
 * MasungFlutterPlugin is a Flutter plugin that provides an interface for Flutter applications to interact with a Masung printer.
 * It implements the FlutterPlugin and MethodCallHandler interfaces.
 */
public class MasungFlutterPlugin implements FlutterPlugin, MethodCallHandler {

    // The MethodChannel that will the communication between Flutter and native Android
    // This local reference serves to register the plugin with the Flutter Engine and unregister it
    // when the Flutter Engine is detached from the Activity
    private MethodChannel channel;
    private MasungPrinterCom printerCom;
    private Context context;

    /**
     * This method is called when the plugin is attached to the Flutter engine.
     * It initializes the MethodChannel and the MasungPrinterCom instance.
     *
     * @param flutterPluginBinding the binding with the Flutter plugin
     */
    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "masung_flutter");
        channel.setMethodCallHandler(this);
        context = flutterPluginBinding.getApplicationContext();
        printerCom = new MasungPrinterCom(context);
    }

    /**
     * This method is called when a method call is made from Flutter.
     * It handles the method calls for getting the platform version, printing a string, and cutting paper.
     *
     * @param call   the method call
     * @param result the result to be returned to Flutter
     */
    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        switch (call.method) {
            case "getPlatformVersion":
                result.success("Android " + android.os.Build.VERSION.RELEASE);
                break;
            case "clearCache":
                if (clearCache()) {
                    result.success(true);
                } else {
                    result.error("UNAVAILABLE", "Clearing cache failed.", false);
                }
            case "setMargin":
                if (setMargin(call.argument("left"), call.argument("right"))) {
                    result.success(true);
                } else {
                    result.error("UNAVAILABLE", "Setting margin failed.", false);
                }
                break;
            case "setAlignment":
                if (setAlignment(call.argument("alignment"))) {
                    result.success(true);
                } else {
                    result.error("UNAVAILABLE", "Setting alignment failed.", false);
                }
                break;
            case "setTextSize":
                if (setTextSize(call.argument("width"), call.argument("height"))) {
                    result.success(true);
                } else {
                    result.error("UNAVAILABLE", "Setting text size failed.", false);
                }
                break;
            case "setBold":
                if (setBold(call.argument("bold"))) {
                    result.success(true);
                } else {
                    result.error("UNAVAILABLE", "Setting bold failed.", false);
                }
                break;
            case "setUnderline":
                if (setUnderline(call.argument("underline"))) {
                    result.success(true);
                } else {
                    result.error("UNAVAILABLE", "Setting underline failed.", false);
                }
                break;
            case "setLineSpacing":
                if (setLineSpacing(call.argument("lineSpacing"))) {
                    result.success(true);
                } else {
                    result.error("UNAVAILABLE", "Setting line spacing failed.", false);
                }
                break;
            case "setItalic":
                if (setItalic(call.argument("italic"))) {
                    result.success(true);
                } else {
                    result.error("UNAVAILABLE", "Setting italic failed.", false);
                }
                break;
            case "printString":
                if (printString(call.argument("text"), Boolean.TRUE.equals(call.argument("newLine")))) {
                    result.success(true);
                } else {
                    result.error("UNAVAILABLE", "Printing string failed.", false);
                }
                break;
            case "feedLine":
                if (feedLine(call.argument("line"))) {
                    result.success(true);
                } else {
                    result.error("UNAVAILABLE", "Feeding line failed.", false);
                }
                break;
            case "feedDot":
                if (feedDot(call.argument("dot"))) {
                    result.success(true);
                } else {
                    result.error("UNAVAILABLE", "Feeding dot failed.", false);
                }
                break;
            case "cutPaper":
                if (cutPaper(Boolean.TRUE.equals(call.argument("fullCut")))) {
                    result.success(true);
                } else {
                    result.error("UNAVAILABLE", "Cutting paper failed.", false);
                }
                break;
            default:
                result.notImplemented();
        }
    }

    private boolean clearCache() {
        try {
            if (printerCom == null)
                printerCom = new MasungPrinterCom(context);
            printerCom.clearCache();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean setMargin(int left, int right) {
        try {
            if (printerCom == null)
                printerCom = new MasungPrinterCom(context);
            printerCom.setMargin(left, right);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean setAlignment(int alignment) {
        try {
            if (printerCom == null)
                printerCom = new MasungPrinterCom(context);
            printerCom.setAlignment(alignment);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean setTextSize(int width, int height) {
        try {
            if (printerCom == null)
                printerCom = new MasungPrinterCom(context);
            printerCom.setTextSize(width, height);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean setBold(boolean bold) {
        try {
            if (printerCom == null)
                printerCom = new MasungPrinterCom(context);
            printerCom.setBold(bold);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean setUnderline(int underline) {
        try {
            if (printerCom == null)
                printerCom = new MasungPrinterCom(context);
            printerCom.setUnderline(underline);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean setLineSpacing(int lineSpacing) {
        try {
            if (printerCom == null)
                printerCom = new MasungPrinterCom(context);
            printerCom.setLineSpacing(lineSpacing);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean setItalic(boolean italic) {
        try {
            if (printerCom == null)
                printerCom = new MasungPrinterCom(context);
            printerCom.setItalic(italic);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean printString(String text, boolean newLine) {
        try {
            if (printerCom == null)
                printerCom = new MasungPrinterCom(context);
            printerCom.printString(text, newLine);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean feedLine(int line) {
        try {
            if (printerCom == null)
                printerCom = new MasungPrinterCom(context);
            printerCom.feedLine(line);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean feedDot(int dot) {
        try {
            if (printerCom == null)
                printerCom = new MasungPrinterCom(context);
            printerCom.feedDot(dot);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean cutPaper(boolean fullCut) {
        try {
            if (printerCom == null)
                printerCom = new MasungPrinterCom(context);
            printerCom.cutPaper(fullCut);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * This method is called when the plugin is detached from the Flutter engine.
     * It unregisters the MethodCallHandler from the MethodChannel.
     *
     * @param binding the binding with the Flutter plugin
     */
    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }
}