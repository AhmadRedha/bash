package ir.ds.dto;

public class MyJwtClaims {
    private String clientId;
    private String hardwareId;

    public MyJwtClaims(String clientId, String hardwareId) {
        this.clientId = clientId;
        this.hardwareId = hardwareId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(String hardwareId) {
        this.hardwareId = hardwareId;
    }
}