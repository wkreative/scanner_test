package com.masung_flutter.msprintsdk;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.util.Log;
import java.util.Iterator;


public class UsbDriver {
    public static final int BAUD115200 = 115200;
    public static final int BAUD19200 = 19200;
    public static final int BAUD38400 = 38400;
    public static final int BAUD57600 = 57600;
    public static final int BAUD9600 = 9600;
    private final UsbManager a;
    private PendingIntent b;
    private final UsbDevice[] d = new UsbDevice[2];
    private final UsbInterface[] e = new UsbInterface[2];
    private final UsbDeviceConnection[] f = new UsbDeviceConnection[2];
    private int g = -1;
    private final UsbEndpoint[] h = new UsbEndpoint[2];
    private final UsbEndpoint[] i = new UsbEndpoint[2];

    public UsbDriver(UsbManager usbManager, Context context) {
        this.a = usbManager;
        for (int i2 = 0; i2 < 4; i2++) {
            int[] c = new int[4];
            c[i2] = 8;
        }
    }

    private static int a(UsbDevice usbDevice) {
        if (usbDevice == null) {
            return -1;
        }
        try {
            if (usbDevice.getProductId() == 8211 && usbDevice.getVendorId() == 1305) {
                return 0;
            }
            if (usbDevice.getProductId() == 8213 && usbDevice.getVendorId() == 1305) {
                return 1;
            }
            Log.i("UsbDriver", "Not support device : " + usbDevice);
            return -1;
        } catch (Exception e2) {
            Log.i("UsbDriver", "getUsbDevIndex exception: " + e2.getMessage());
            return -1;
        }
    }

    public void closeUsbDevice() {
        if (this.g >= 0) {
            closeUsbDevice(this.d[this.g]);
        }
    }

    public boolean closeUsbDevice(UsbDevice usbDevice) {
        try {
            this.g = a(usbDevice);
            if (this.g < 0) {
                return false;
            }
            if (!(this.f[this.g] == null || this.e[this.g] == null)) {
                this.f[this.g].releaseInterface(this.e[this.g]);
                this.e[this.g] = null;
                this.f[this.g].close();
                this.f[this.g] = null;
                this.d[this.g] = null;
                this.h[this.g] = null;
                this.i[this.g] = null;
            }
            return true;
        } catch (Exception e2) {
            Log.i("UsbDriver", "closeUsbDevice exception: " + e2.getMessage());
            return false;
        }
    }

    public boolean isConnected() {
        return this.g >= 0 && this.d[this.g] != null && this.h[this.g] != null && this.i[this.g] != null;
    }

    public boolean isUsbPermission() {
        try {
            if (this.g >= 0 && this.a != null) {
                return this.a.hasPermission(this.d[this.g]);
            }
            return false;
        } catch (Exception e2) {
            return false;
        }
    }

    public boolean openUsbDevice() {
        if (this.g < 0) {
            Iterator<UsbDevice> it = this.a.getDeviceList().values().iterator();
            while (true) {
                if (it.hasNext()) {
                    UsbDevice next = it.next();
                    Log.i("UsbDriver", "Devices : " + next.toString());
                    this.g = a(next);
                    if (this.g >= 0) {
                        this.d[this.g] = next;
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        if (this.g < 0) {
            return false;
        }
        return openUsbDevice(this.d[this.g]);
    }

    public boolean openUsbDevice(UsbDevice usbDevice) {
        this.g = a(usbDevice);
        if (this.g < 0) {
            return false;
        }
        int interfaceCount = this.d[this.g].getInterfaceCount();
        Log.i("UsbDriver", " m_Device[m_UsbDevIdx].getInterfaceCount():" + interfaceCount);
        if (interfaceCount == 0) {
            return false;
        }
        if (interfaceCount > 0) {
            this.e[this.g] = this.d[this.g].getInterface(0);
        }
        if (this.e[this.g].getEndpoint(1) != null) {
            this.i[this.g] = this.e[this.g].getEndpoint(1);
        }
        if (this.e[this.g].getEndpoint(0) != null) {
            this.h[this.g] = this.e[this.g].getEndpoint(0);
        }
        this.f[this.g] = this.a.openDevice(this.d[this.g]);
        if (this.f[this.g] == null) {
            return false;
        }
        if (this.f[this.g].claimInterface(this.e[this.g], true)) {
            return true;
        }
        this.f[this.g].close();
        return false;
    }

    public int read(byte[] bArr, byte[] bArr2) {
        if (this.g < 0) {
            return -1;
        }
        return read(bArr, bArr2, this.d[this.g]);
    }

    public int read(byte[] bArr, byte[] bArr2, UsbDevice usbDevice) {
        if (write(bArr2, bArr2.length, usbDevice) < 0) {
            return -1;
        }
        int bulkTransfer = this.f[this.g].bulkTransfer(this.i[this.g], bArr, bArr.length, 100);
        Log.i("UsbDriver", "mFTDIEndpointOUT:" + bulkTransfer);
        return bulkTransfer;
    }

    public void setPermissionIntent(PendingIntent pendingIntent) {
        this.b = pendingIntent;
    }

    public boolean usbAttached(Intent intent) {
        return usbAttached((UsbDevice) intent.getParcelableExtra("device"));
    }

    public boolean usbAttached(UsbDevice usbDevice) {
        this.g = a(usbDevice);
        this.d[this.g] = usbDevice;
        if (this.g < 0) {
            Log.i("UsbDriver", "Not support device : " + usbDevice.toString());
            return false;
        } else if (this.a.hasPermission(this.d[this.g])) {
            return true;
        } else {
            this.a.requestPermission(this.d[this.g], this.b);
            return false;
        }
    }

    public boolean usbDetached(Intent intent) {
        return closeUsbDevice(intent.getParcelableExtra("device"));
    }

    public int write(byte[] bArr) {
        return write(bArr, bArr.length);
    }

    public int write(byte[] bArr, int i2) {
        if (this.g < 0) {
            return -1;
        }
        return write(bArr, bArr.length, this.d[this.g]);
    }

    public int write(byte[] bArr, int i2, UsbDevice usbDevice) {
        this.g = a(usbDevice);
        if (this.g < 0) {
            return -1;
        }
        byte[] bArr2 = new byte[4096];
        int i3 = 0;
        while (i3 < i2) {
            int i4 = i3 + 4096 > i2 ? i2 - i3 : 4096;
            System.arraycopy(bArr, i3, bArr2, 0, i4);
            int bulkTransfer = this.f[this.g].bulkTransfer(this.h[this.g], bArr2, i4, 3000);
            Log.i("UsbDriver", "-----Length--------" + bulkTransfer);
            if (bulkTransfer < 0) {
                return -1;
            }
            i3 += bulkTransfer;
        }
        return i3;
    }

    public int write(byte[] bArr, UsbDevice usbDevice) {
        return write(bArr, bArr.length, usbDevice);
    }
}
