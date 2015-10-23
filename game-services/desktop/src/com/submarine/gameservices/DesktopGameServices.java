package com.submarine.gameservices;

import com.badlogic.gdx.math.MathUtils;
import com.submarine.gameservices.events.LoadedEventListener;
import com.submarine.gameservices.quests.LoadedQuestListener;
import com.submarine.gameservices.quests.QuestRewardListener;

import java.util.HashMap;

/**
 * Created by sargis on 2/25/15.
 */
public class DesktopGameServices implements GameServices {
    private boolean isSavedGamesLoadDone;
    private GameServicesListener<Object> gameServicesListener;

    private HashMap<String, Integer> achieveProgressMap;

    public DesktopGameServices() {
        isSavedGamesLoadDone = false;

        System.out.println("init!");
        achieveProgressMap = new HashMap<String, Integer>();
    }

    @Override
    public void login() {
        gameServicesListener.onSignInSucceeded();
    }

    @Override
    public void logout() {

    }

    @Override
    public void submitEvent(String eventId, int count) {

    }

    @Override
    public void showEvents() {

    }

//    @Override
//    public void submitEvent(String eventId, int count) {
//
//    }
//
//    @Override
//    public void showEvents() {
//
//    }

    @Override
    public void submitScore(String leaderBoardId, long score) {

    }

    @Override
    public void showLeaderBoard(String string) {

    }

    @Override
    public void showLeaderBoards() {

    }

    @Override
    public void unlockAchievement(String achievementId) {

    }

    @Override
    public void incrementAchievement(String achievementId, int incrementAmount, int endValue) {
//TODO remove all these!!!
        System.out.println("achieveProgressMap: "+achieveProgressMap);
        Integer achieveProgress = achieveProgressMap.get(achievementId);
        System.out.println("progress: "+achieveProgress);
        if (achieveProgress == null) {
            achieveProgressMap.put(achievementId, incrementAmount);
        } else {
            int newAmount = achieveProgress+incrementAmount;
            System.out.println(achieveProgressMap+" "+achievementId+" "+newAmount);
            achieveProgressMap.put(achievementId, newAmount);
        }
        double percentComplete = achieveProgressMap.get(achievementId) * 100d / endValue;
        System.out.println("EndValue: "+endValue+", AchieveProgress: "+achieveProgress);
        System.out.println("PercentComplete: "+percentComplete);
    }

    @Override
    public void showAchievements() {

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
        return false;
    }

    @Override
    public void savedGamesLoad(String snapshotName, boolean createIfMissing) {
        int status = MathUtils.random(0, 3);
        switch (status) {
            case 0:
            case 1:
                gameServicesListener.savedGamesLoadSucceeded(new Object());
                break;
            case 2:
                gameServicesListener.savedGamesLoadConflicted(new Object(), 0);
                break;
            default:
                gameServicesListener.savedGamesLoadFailed(new Object());
                break;
        }
        isSavedGamesLoadDone = true;
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
        return isSavedGamesLoadDone;
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
}
