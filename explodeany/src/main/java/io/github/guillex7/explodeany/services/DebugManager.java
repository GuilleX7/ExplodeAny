package io.github.guillex7.explodeany.services;

public final class DebugManager {
    private boolean isDebugEnabled;

    private static DebugManager instance;

    private DebugManager() {
        this.isDebugEnabled = false;
    }

    public static DebugManager getInstance() {
        if (DebugManager.instance == null) {
            DebugManager.instance = new DebugManager();
        }
        return DebugManager.instance;
    }

    public boolean isDebugEnabled() {
        return this.isDebugEnabled;
    }

    public void setIsDebugEnabled(final boolean isDebugEnabled) {
        this.isDebugEnabled = isDebugEnabled;
    }
}
