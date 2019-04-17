package cn.vacuumflask.commonlib.entity;

public class AppInfo {
    private String appName;
    private String pkg;
    private String clsName;

    public AppInfo(String appName, String pkg, String clsName) {
        this.appName = appName;
        this.pkg = pkg;
        this.clsName = clsName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public String getClsName() {
        return clsName;
    }

    public void setClsName(String clsName) {
        this.clsName = clsName;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "appName='" + appName + '\'' +
                ", pkg='" + pkg + '\'' +
                ", clsName='" + clsName + '\'' +
                '}';
    }
}
