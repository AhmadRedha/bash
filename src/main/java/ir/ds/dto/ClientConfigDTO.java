package ir.ds.dto;


public class ClientConfigDTO {

    private String clientId;
    private String ipAddress;
    private int totalMemory;
    private double cpuUsage;
    private int width;
    private int height;
    private String wifiStatus;
    private String bluetoothStatus;
    private String lanStatus;
    private String soundVolume;
    private int brightness;
    private String monitorName;
    private String orientation;
    private String macAddress;

    public ClientConfigDTO(){

    }

    // Getters and setters for each field

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(int totalMemory) {
        this.totalMemory = totalMemory;
    }

    public double getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getWifiStatus() {
        return wifiStatus;
    }

    public void setWifiStatus(String wifiStatus) {
        this.wifiStatus = wifiStatus;
    }

    public String getBluetoothStatus() {
        return bluetoothStatus;
    }

    public void setBluetoothStatus(String bluetoothStatus) {
        this.bluetoothStatus = bluetoothStatus;
    }

    public String getLanStatus() {
        return lanStatus;
    }

    public void setLanStatus(String lanStatus) {
        this.lanStatus = lanStatus;
    }

    public String getSoundVolume() {
        return soundVolume;
    }

    public void setSoundVolume(String soundVolume) {
        this.soundVolume = soundVolume;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    // Optional: constructor accepting all fields
    public ClientConfigDTO(String clientId, String ipAddress, int totalMemory, double cpuUsage, int width, int height, String wifiStatus, String bluetoothStatus, String lanStatus, String soundVolume, int brightness, String monitorName, String orientation,String macAddress) {
        this.clientId = clientId;
        this.ipAddress = ipAddress;
        this.totalMemory = totalMemory;
        this.cpuUsage = cpuUsage;
        this.width = width;
        this.height = height;
        this.wifiStatus = wifiStatus;
        this.bluetoothStatus = bluetoothStatus;
        this.lanStatus = lanStatus;
        this.soundVolume = soundVolume;
        this.brightness = brightness;
        this.monitorName = monitorName;
        this.orientation = orientation;
        this.macAddress=macAddress;
    }

    // Optional: toString method for printing object content
    @Override
    public String toString() {
        return "SystemData{" +
                "clientId='" + clientId + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", totalMemory=" + totalMemory +
                ", cpuUsage=" + cpuUsage +
                ", width=" + width +
                ", height=" + height +
                ", wifiStatus='" + wifiStatus + '\'' +
                ", bluetoothStatus='" + bluetoothStatus + '\'' +
                ", lanStatus='" + lanStatus + '\'' +
                ", soundVolume='" + soundVolume + '\'' +
                ", brightness=" + brightness +
                ", monitorName='" + monitorName + '\'' +
                ", orientation='" + orientation + '\'' +
                '}';
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
