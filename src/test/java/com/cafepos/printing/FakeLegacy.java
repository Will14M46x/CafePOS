package com.cafepos.printing;

public class FakeLegacy extends vendor.legacy.LegacyThermalPrinter {
    int lastLen = -1;
    @Override public void legacyPrint(byte[] payload) {
        this.lastLen = (payload == null) ? -1 : payload.length;
    }
}
