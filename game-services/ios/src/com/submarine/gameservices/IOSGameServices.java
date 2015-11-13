package com.submarine.gameservices;

import com.badlogic.gdx.Gdx;
import com.submarine.gameservices.events.LoadedEventListener;
import com.submarine.gameservices.quests.LoadedQuestListener;
import com.submarine.gameservices.quests.QuestRewardListener;
import org.robovm.apple.foundation.NSError;
import org.robovm.apple.gamekit.GKAchievement;
import org.robovm.apple.gamekit.GKLeaderboard;
import org.robovm.apple.uikit.*;
import org.robovm.bindings.gamecenter.GameCenterListener;
import org.robovm.objc.block.VoidBlock1;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sargis on 2/26/15.
 */
public class IOSGameServices implements GameServices, GameCenterListener {
    private static final String TAG = "com.submarine.gameservices.IOSGameServices";
    private GCManager gcManager;
    private boolean isSignedIn;
    private GameServicesListener gameServicesListener;

    private HashMap<String, Integer> achieveProgressMap;


    public IOSGameServices() {

        isSignedIn = false;

        achieveProgressMap = new HashMap<String, Integer>();
    }


    @Override
    public void login() {
        if (isSignedIn) {
            return;
        }
        if (gcManager == null) {
            gcManager = new GCManager(UIApplication.getSharedApplication().getKeyWindow(), this);
        }
        gcManager.login();
    }

    @Override
    public void logout() {

    }

    @Override
    public void submitScore(String leaderBoardId, long score) {
        gcManager.reportScore(leaderBoardId, score);
    }

    @Override
    public void showLeaderBoard(String identifier) {
        System.out.println("leadID: "+identifier);

        if (isSignedIn) {
            gcManager.showLeaderboardView(identifier);
        } else {
            showNotSignedInDialog();
        }
    }

    @Override
    public void showLeaderBoards() {
        if (isSignedIn) {
            gcManager.showLeaderboardsView();
        } else {
            showNotSignedInDialog();
        }
    }

    @Override
    public void unlockAchievement(String achievementId) {
        if (isSignedIn) {
            gcManager.reportAchievement(achievementId);
        } else {
            showNotSignedInDialog();
        }
    }

    @Override
    public void incrementAchievement(String achievementId, int incrementAmount, int endValue) {
        if (isSignedIn) {
            //TODO save incremental achievements progress locally (to preferences)
            //TODO change incremental achievements values
            System.out.println("achieveProgressMap: "+achieveProgressMap);
            Integer achieveProgress = achieveProgressMap.get(achievementId) == null
                    ? 0 : achieveProgressMap.get(achievementId);
            System.out.println("progress: "+achieveProgress);
            int newAmount = achieveProgress+incrementAmount;
            achieveProgressMap.put(achievementId, newAmount);
            double percentComplete = achieveProgressMap.get(achievementId) * 100d / endValue;
            System.out.println("PercentComplete: "+percentComplete);

            gcManager.reportAchievement(achievementId, percentComplete);
        } else {
            showNotSignedInDialog();
        }
    }

    @Override
    public void showAchievements() {
        if (isSignedIn) {
            gcManager.showAchievementsView();
        } else {
            showNotSignedInDialog();
        }
    }

    @Override
    public void submitEvent(String eventId, int incrementAmount) {

    }

    @Override
    public void showEvents() {

    }

    @Override
    public void loadEvents(LoadedEventListener listener) {

    }

    @Override
    public void loadEventsByIds(LoadedEventListener listener, String... eventIds) {

    }

    @Override
    public void showQuests() {

    }

    @Override
    public void loadQuests(LoadedQuestListener listener) {

    }

    @Override
    public void loadQuestsByIds(LoadedQuestListener listener, String... questIds) {

    }

    @Override
    public void registerQuestUpdate(QuestRewardListener listener) {

    }

    @Override
    public boolean isSignedIn() {
        return isSignedIn;
    }

    @Override
    public void savedGamesLoad(String snapshotName, boolean createIfMissing) {

    }

    @Override
    public void savedGamesUpdate(String snapshotName, byte[] data, boolean createIfMissing) {

    }

    @Override
    public void setListener(GameServicesListener gameServicesListener) {
        this.gameServicesListener = gameServicesListener;
    }

    /*@Override
    public boolean isSavedGamesLoadDone() {
        return false;
    }*/

    @Override
    public void loadUserInfo() {

    }

    @Override
    public void onSignInSucceeded() {

    }

    @Override
    public void onSignInFailed() {

    }

    @Override
    public void showSavedGamesUI() {

    }

    @Override
    public void playerLoginCompleted() {
        Gdx.app.log(TAG, "Sign in success");

        isSignedIn = true;

        //TODO remove this line!!!
        gcManager.resetAchievements();
//        gcManager.loadLeaderboards();
//        gcManager.loadAchievements();

        if (gameServicesListener != null) {
            gameServicesListener.onSignInSucceeded();
        }
    }

    @Override
    public void playerLoginFailed(NSError nsError) {
        Gdx.app.log(TAG, "Sign in Fail");

        isSignedIn = false;
        if (gameServicesListener != null) {
            gameServicesListener.onSignInFailed();
        }
    }

    private void showNotSignedInDialog() {
        showDialog("Game Center", "You are not signed in", "Ok");
    }

    private void showDialog(String title, String message, String cancelButtonTitle) {
        UIAlertController alertController = new UIAlertController(title, message, UIAlertControllerStyle.Alert);
        UIAlertAction OKAction = new UIAlertAction(cancelButtonTitle, UIAlertActionStyle.Default, new VoidBlock1<UIAlertAction> () {

            @Override
            public void invoke(UIAlertAction uiAlertAction) {

            }
        });
        alertController.addAction(OKAction);
        gcManager.getRootViewController().presentViewController(alertController, true, new Runnable() {
            @Override
            public void run() {
                //any action after cancel is pressed
                System.out.println("cancel");
            }
        });
    }

    @Override
    public void achievementReportCompleted() {

    }

    @Override
    public void achievementReportFailed(NSError nsError) {

    }

    @Override
    public void achievementsLoadCompleted(ArrayList<GKAchievement> arrayList) {

    }

    @Override
    public void achievementsLoadFailed(NSError nsError) {

    }

    @Override
    public void achievementsResetCompleted() {

    }

    @Override
    public void achievementsResetFailed(NSError nsError) {

    }

    @Override
    public void scoreReportCompleted() {

    }

    @Override
    public void scoreReportFailed(NSError nsError) {

    }

    @Override
    public void leaderboardsLoadCompleted(ArrayList<GKLeaderboard> arrayList) {

    }

    @Override
    public void leaderboardsLoadFailed(NSError nsError) {

    }

    @Override
    public void leaderboardViewDismissed() {

    }

    @Override
    public void achievementViewDismissed() {

    }
}
