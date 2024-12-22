package com.masung_flutter.msprintsdk;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;

public class PrinterService {
    private static final String ACTION_USB_PERMISSION = "com.usb.sample.USB_PERMISSION";
    private UsbDriver usbDriver;
    private UsbDevice device;
    private Context context;
    private final BroadcastReceiver usbReceiver;

    public PrinterService(Context context) {
        this.context = context;
        UsbManager usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        this.usbDriver = new UsbDriver(usbManager, context);
        PendingIntent permissionIntent = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION), 0);
        this.usbDriver.setPermissionIntent(permissionIntent);

        usbReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (ACTION_USB_PERMISSION.equals(action)) {
                    synchronized (this) {
                        UsbDevice usbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                        if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                            if (usbDevice != null && usbDevice.equals(device)) {
                                // Permission granted, try to open the device
                                if (!usbDriver.openUsbDevice(usbDevice)) {
                                    Log.e("PrinterService", "Failed to open USB device after permission granted.");
                                }
                            }
                        } else {
                            Log.d("PrinterService", "Permission denied for device " + usbDevice);
                        }
                    }
                }
            }
        };

        // Register the receiver
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        context.registerReceiver(usbReceiver, filter);

        initializePrinter();
    }

    private void initializePrinter() {
        for (UsbDevice usbDevice : ((UsbManager) context.getSystemService(Context.USB_SERVICE)).getDeviceList().values()) {
            // Check for specific vendor and product IDs
            if ((usbDevice.getProductId() == 8211 && usbDevice.getVendorId() == 1305) ||
                    (usbDevice.getProductId() == 8213 && usbDevice.getVendorId() == 1305)) {
                this.device = usbDevice;
                if (!this.usbDriver.isUsbPermission()) {
                    this.usbDriver.usbAttached(usbDevice); // This will request permission
                } else if (!this.usbDriver.openUsbDevice(usbDevice)) {
                    Log.e("PrinterService", "Failed to open USB device.");
                }
                break;
            }
        }
    }

    public void sendBytesToPrinter(byte[] data) {
        if (this.usbDriver.isConnected()) {
            usbDriver.write(data, device);
        } else {
            throw new RuntimeException("Printer is not connected. Please connect the printer before sending data.");
        }
    }

    public void onDestroy() {
        // Unregister the receiver when the service is destroyed
        if (context != null && usbReceiver != null) {
            context.unregisterReceiver(usbReceiver);
        }
    }
}
