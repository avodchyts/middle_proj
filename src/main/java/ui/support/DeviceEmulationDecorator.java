package ui.support;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chromium.HasCdp;
import org.openqa.selenium.devtools.HasDevTools;
import support.Decorator;
import support.DeviceFactory;


import java.util.Collections;

public class DeviceEmulationDecorator implements Decorator<WebDriver> {

    private final DeviceFactory device;
    public DeviceEmulationDecorator(DeviceFactory device) {
        this.device = device;
    }

    @Override
    public WebDriver decorate(WebDriver driver) {
        if (driver instanceof HasDevTools) {
            HasCdp cdpExecutor = (HasCdp) driver;
            cdpExecutor.executeCdpCommand("Emulation.setDeviceMetricsOverride", Collections.<String, Object>unmodifiableMap(device.getDeviceMetrics()));
            return driver;
        }
        throw new IllegalArgumentException("Driver does not support DevTools");
    }
}
