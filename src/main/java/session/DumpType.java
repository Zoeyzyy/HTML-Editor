package session;

import lombok.Getter;

import java.io.Serializable;

public class DumpType implements Serializable {
    @Getter
    private String fileName;
    @Getter
    private boolean isShowID;

    public DumpType(String fileName, boolean isShowID) {
        this.fileName = fileName;
        this.isShowID = isShowID;
    }

    public boolean isActive() {
        return isShowID;
    }
}