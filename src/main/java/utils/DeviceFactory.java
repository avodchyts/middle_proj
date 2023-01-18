package utils;


import org.apache.commons.lang3.EnumUtils;
import org.apache.log4j.Logger;

import java.util.Objects;

import static java.lang.String.format;

public enum DeviceFactory {
    IPHONE_11_PRO(375, 812, 50, true);
    public int deviceWidth;
    public int deviceHeight;
    public int deviceScale;
    public boolean isMobileDevice;

   private DeviceFactory(int deviceWidth, int deviceHeight, int deviceScale, boolean isMobileDevice) {
        this.deviceWidth = deviceWidth;
        this.deviceHeight = deviceHeight;
        this.deviceScale = deviceScale;
        this.isMobileDevice = isMobileDevice;
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
}
