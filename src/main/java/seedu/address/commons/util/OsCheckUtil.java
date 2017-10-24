package seedu.address.commons.util;

/**
 * Helps with checking the OS the application is running on.
 */
public class OsCheckUtil {
    private String os;

    public OsCheckUtil() {
        this.os = System.getProperty("os.name").toLowerCase();

        if (isWindows()) {
            this.os = "windows";
        } else if (isMac()) {
            this.os = "mac";
        } else if (isUnix()) {
            this.os = "unix";
        }
    }

    public String getOsName() {
        return os;
    }

    private boolean isWindows() {
        return (os.contains("win"));
    }

    private boolean isMac() {
        return (os.contains("mac"));
    }

    private boolean isUnix() {
        return (os.contains("nix") || os.contains("nux") || os.contains("aix"));
    }

}
