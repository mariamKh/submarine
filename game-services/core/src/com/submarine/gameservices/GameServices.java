package com.submarine.gameservices;

public interface GameServices {
    void login();

    void logout();

    void submitScore(String leaderBoardId, long score);

    void showLeaderBoard(String identifier);

    void unlockAchievement(String achievementId);

    void incrementAchievement(String achievementId, int incrementAmount);

    void showAchievements();

    boolean isSignedIn();
}
