package com.submarine.gameservices;

import org.robovm.apple.uikit.UIViewController;
import org.robovm.apple.uikit.UIWindow;
import org.robovm.bindings.gamecenter.GameCenterListener;
import org.robovm.bindings.gamecenter.GameCenterManager;

/**
 * Created by mariam on 10/16/15.
 */
public class GCManager extends GameCenterManager {

    private UIWindow keyWindow;

    public GCManager(UIWindow keyWindow, GameCenterListener listener) {
        super(keyWindow, listener);

        this.keyWindow = keyWindow;
    }

    public UIWindow getKeyWindow() {
        return keyWindow;
    }

    public UIViewController getRootViewController() {
        return keyWindow.getRootViewController();
    }
}
