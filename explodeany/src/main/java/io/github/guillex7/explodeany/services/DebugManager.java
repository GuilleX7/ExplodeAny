package io.github.guillex7.explodeany.services;

public class DebugManager {
    private boolean isDebugEnabled;

    private static DebugManager instance;

    private DebugManager() {
        this.isDebugEnabled = false;
    }

    public static DebugManager getInstance() {
        if (instance == null) {
            instance = new DebugManager();
        }
        return instance;
    }

    public boolean isDebugEnabled() {
        return isDebugEnabled;
    }

    public void setIsDebugEnabled(boolean isDebugEnabled) {
        this.isDebugEnabled = isDebugEnabled;
    }
}
