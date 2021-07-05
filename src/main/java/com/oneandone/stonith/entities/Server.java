package com.oneandone.stonith.entities;

public class Server {
    private Integer id;
    private Integer stonithGroupId;
    private String serialNumber;
    private String managementUrl;
    private String dialect;
    private String loginUser;
    private String loginPassword;
    private String apiToken;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getManagementUrl() {
        return managementUrl;
    }

    public void setManagementUrl(String managementUrl) {
        this.managementUrl = managementUrl;
    }

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    public Integer getStonithGroupId() {
        return stonithGroupId;
    }

    public void setStonithGroupId(Integer stonithGroupId) {
        this.stonithGroupId = stonithGroupId;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }
}
