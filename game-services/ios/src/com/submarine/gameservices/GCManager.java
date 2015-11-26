package com.submarine.gameservices;

import org.robovm.apple.foundation.NSArray;
import org.robovm.apple.foundation.NSData;
import org.robovm.apple.foundation.NSError;
import org.robovm.apple.gamekit.GKLocalPlayer;
import org.robovm.apple.gamekit.GKPlayer;
import org.robovm.apple.gamekit.GKSavedGame;
import org.robovm.apple.gamekit.GKSavedGameListener;
import org.robovm.apple.uikit.UIViewController;
import org.robovm.apple.uikit.UIWindow;
import org.robovm.bindings.gamecenter.GameCenterListener;
import org.robovm.bindings.gamecenter.GameCenterManager;
import org.robovm.objc.block.VoidBlock2;

/**
 * Created by mariam on 10/16/15.
 */
public class GCManager extends GameCenterManager {

    private UIWindow keyWindow;
    private GKLocalPlayer localPlayer;
    private GameServicesListener<NSData> gameServicesListener;

    public GCManager(UIWindow keyWindow, GameCenterListener listener) {
        super(keyWindow, listener);

        this.keyWindow = keyWindow;

        localPlayer = GKLocalPlayer.getLocalPlayer();
    }

    public void saveGameData(String savedGameName, byte[] data) {
        final NSData result = new NSData(data);
        localPlayer.saveGameData(result, savedGameName, new VoidBlock2<GKSavedGame, NSError>() {
            @Override
            public void invoke(GKSavedGame gkSavedGame, NSError nsError) {
                System.out.println("saved game: "+gkSavedGame.getName()+" "+gkSavedGame.getModificationDate()
                        +" "+gkSavedGame.getDeviceName()+" error: "+nsError);
                if (nsError == null)  {
                    gameServicesListener.savedGamesUpdateSucceeded();
                } else {
                    gameServicesListener.savedGamesUpdateFailed();
                    return;
                }

            }
        });
    }

    public void loadGameData(final String savedGameName) {
        localPlayer.fetchSavedGames(new VoidBlock2<NSArray<GKSavedGame>, NSError>() {
            @Override
            public void invoke(NSArray<GKSavedGame> gkSavedGames, NSError nsError) {
                final NSArray<GKSavedGame> matchingGames = new NSArray<GKSavedGame>();
                for(GKSavedGame savedGame : gkSavedGames) {
                    if(savedGame.getName().equals(savedGameName)) {
                        matchingGames.add(savedGame);
                    }
                }
                switch (matchingGames.size()) {
                    case 0:
                        gameServicesListener.savedGamesLoadFailed();
                        break;
                    case 1:
                        GKSavedGame theGame = matchingGames.get(0);
                        theGame.loadData(new VoidBlock2<NSData, NSError>() {
                            @Override
                            public void invoke(NSData nsData, NSError nsError) {
                                if (nsError == null)  {
                                    gameServicesListener.savedGamesLoadSucceeded(nsData);
                                } else {
                                    gameServicesListener.savedGamesLoadContentsUnavailable();
                                    return;
                                }
                            }
                        });
                        break;
                    default:
                        localPlayer.resolveConflictingSavedGames(matchingGames, new NSData(), new VoidBlock2<NSArray<GKSavedGame>, NSError>() {
                            @Override
                            public void invoke(NSArray<GKSavedGame> gkSavedGames, NSError nsError) {
                                for (final GKSavedGame game : gkSavedGames) {
                                    game.loadData(new VoidBlock2<NSData, NSError>() {
                                        @Override
                                        public void invoke(NSData nsData, NSError nsError) {
                                            if (nsError == null)  {
                                                System.out.println("Conflicted (resolved) games: " +
                                                        ", " + game.getName() + " " + new String(nsData.getBytes()));
                                                gameServicesListener.savedGamesLoadConflicted(nsData, 0);
                                            } else {
                                                gameServicesListener.savedGamesLoadContentsUnavailable();
                                                return;
                                            }
                                        }
                                    });
                                }
                            }
                        });
                        break;
                }
            }
        });
    }

    public void setGameServicesListener(GameServicesListener<NSData> gameServicesListener) {
        this.gameServicesListener = gameServicesListener;
    }

    public UIWindow getKeyWindow() {
        return keyWindow;
    }

    public UIViewController getRootViewController() {
        return keyWindow.getRootViewController();
    }
}
