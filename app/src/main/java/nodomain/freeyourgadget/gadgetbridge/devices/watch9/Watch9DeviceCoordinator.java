/*  Copyright (C) 2016-2021 Andreas Shimokawa, Carsten Pfeiffer, Daniele
    Gobbetti, mamucho, maxirnilian, Vadim Kaushan

    This file is part of Gadgetbridge.

    Gadgetbridge is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Gadgetbridge is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>. */
package nodomain.freeyourgadget.gadgetbridge.devices.watch9;

import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.le.ScanFilter;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelUuid;

import java.util.Collection;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import nodomain.freeyourgadget.gadgetbridge.GBException;
import nodomain.freeyourgadget.gadgetbridge.devices.AbstractBLEDeviceCoordinator;
import nodomain.freeyourgadget.gadgetbridge.devices.InstallHandler;
import nodomain.freeyourgadget.gadgetbridge.devices.SampleProvider;
import nodomain.freeyourgadget.gadgetbridge.entities.DaoSession;
import nodomain.freeyourgadget.gadgetbridge.entities.Device;
import nodomain.freeyourgadget.gadgetbridge.impl.GBDevice;
import nodomain.freeyourgadget.gadgetbridge.impl.GBDeviceCandidate;
import nodomain.freeyourgadget.gadgetbridge.model.ActivitySample;
import nodomain.freeyourgadget.gadgetbridge.model.DeviceType;

public class Watch9DeviceCoordinator extends AbstractBLEDeviceCoordinator {
    @Override
    protected void deleteDevice(@NonNull GBDevice gbDevice, @NonNull Device device, @NonNull DaoSession session) throws GBException {

    }

    @NonNull
    @Override
    public Collection<? extends ScanFilter> createBLEScanFilters() {
        ParcelUuid watch9Service = new ParcelUuid(Watch9Constants.UUID_SERVICE_WATCH9);
        ScanFilter filter = new ScanFilter.Builder().setServiceUuid(watch9Service).build();
        return Collections.singletonList(filter);
    }

    @NonNull
    @Override
    public DeviceType getSupportedType(GBDeviceCandidate candidate) {
        String macAddress = candidate.getMacAddress().toUpperCase();
        String deviceName = candidate.getName().toUpperCase();
        if (candidate.supportsService(Watch9Constants.UUID_SERVICE_WATCH9)) {
            return DeviceType.WATCH9;
            // add support for Watch X non-plus (same MAC address)
            // add support for Watch X Plus (same MAC address)
        } else if ((macAddress.startsWith("1C:87:79")) && ((!deviceName.equalsIgnoreCase("WATCH X")) && (!deviceName.equalsIgnoreCase("WATCH XPLUS")))) {
            return DeviceType.WATCH9;
        } else if (deviceName.equals("WATCH 9")) {
            return DeviceType.WATCH9;
        }
        return DeviceType.UNKNOWN;
    }

    @Override
    public DeviceType getDeviceType() {
        return DeviceType.WATCH9;
    }

    @Override
    public int getBondingStyle() {
        return BONDING_STYLE_NONE;
    }

    @Nullable
    @Override
    public Class<? extends Activity> getPairingActivity() {
        return Watch9PairingActivity.class;
    }

    @Override
    public boolean supportsActivityDataFetching() {
        return false;
    }

    @Override
    public boolean supportsActivityTracking() {
        return false;
    }

    @Override
    public SampleProvider<? extends ActivitySample> getSampleProvider(GBDevice device, DaoSession session) {
        return null;
    }

    @Override
    public InstallHandler findInstallHandler(Uri uri, Context context) {
        return null;
    }

    @Override
    public boolean supportsScreenshots() {
        return false;
    }

    @Override
    public int getAlarmSlotCount(GBDevice device) {
        return 3; // FIXME - check the real value
    }

    @Override
    public boolean supportsSmartWakeup(GBDevice device) {
        return false;
    }

    @Override
    public boolean supportsHeartRateMeasurement(GBDevice device) {
        return false;
    }

    @Override
    public String getManufacturer() {
        return "Lenovo";
    }

    @Override
    public boolean supportsAppsManagement(final GBDevice device) {
        return false;
    }

    @Override
    public Class<? extends Activity> getAppsManagementActivity() {
        return null;
    }

    @Override
    public boolean supportsCalendarEvents() {
        return false;
    }

    @Override
    public boolean supportsRealtimeData() {
        return false;
    }

    @Override
    public boolean supportsWeather() {
        return false;
    }

    @Override
    public boolean supportsFindDevice() {
        return false;
    }

    @Nullable
    @Override
    public Class<? extends Activity> getCalibrationActivity() {
        return Watch9CalibrationActivity.class;
    }
}
