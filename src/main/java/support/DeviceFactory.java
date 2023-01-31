package ui.support;


import org.apache.commons.lang3.EnumUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.lang.String.format;

public enum DeviceFactory implements Device {
    IPHONE_11_PRO("375", "812", "true", "50");
    private final String deviceWidth;
    private final String deviceHeight;
    private final String deviceScale;
    private final String mobileDevice;
    private final Map<String, String> deviceMetrics;

    private DeviceFactory(String deviceWidth, String deviceHeight, String mobileDevice, String deviceScale) {
        this.deviceWidth = deviceWidth;
        this.deviceHeight = deviceHeight;
        this.deviceScale = deviceScale;
        this.mobileDevice = mobileDevice;
        this.deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", deviceWidth);
        deviceMetrics.put("height", deviceHeight);
        deviceMetrics.put("scale", deviceScale);
        deviceMetrics.put("mobile", mobileDevice);
    }

    private static final Logger LOGGER = Logger.getLogger(DeviceFactory.class);

    public static DeviceFactory selectDeviceByName(String deviceName) {
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
        return deviceMetrics;
    }
}
