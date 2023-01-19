package utils;


import org.apache.commons.lang3.EnumUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.lang.String.format;

public enum DeviceFactory implements Device {
    IPHONE_11_PRO("375", "812", "true", "50");
    public String deviceWidth;
    public String deviceHeight;
    public String deviceScale;
    public String mobileDevice;
    public Map<String, String> deviceMetrics;
    private static String device;

    private DeviceFactory(String deviceWidth, String deviceHeight, String mobileDevice, String deviceScale) {
        this.deviceWidth = deviceWidth;
        this.deviceHeight = deviceHeight;
        this.deviceScale = deviceScale;
        this.mobileDevice = mobileDevice;
        this.deviceMetrics = new HashMap<>();
    }

    private static final Logger LOGGER = Logger.getLogger(DeviceFactory.class);

    public static DeviceFactory selectDeviceByName(String deviceName) {
        device = deviceName;
        try {
            return Objects.requireNonNull(
                    EnumUtils.getEnum(DeviceFactory.class, deviceName.toUpperCase())
            );
        } catch (NullPointerException e) {
            LOGGER.error(format("Could not find device for %s", deviceName));
            LOGGER.info(format("Could not find device for %s", deviceName));
            return DeviceFactory.IPHONE_11_PRO;
        }
    }

    @Override
    public Map<String, String> getDeviceMetrics() {
        deviceMetrics.put("width", deviceWidth);
        deviceMetrics.put("height", deviceHeight);
        deviceMetrics.put("scale", deviceScale);
        deviceMetrics.put("mobile", mobileDevice);
        return deviceMetrics;
    }
}
