package com.submarine.gameservices;

import android.app.Activity;
import android.content.Intent;
import com.badlogic.gdx.Gdx;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;

public class AndroidGameServices implements GameHelper.GameHelperListener, GameServices {
    private static final String TAG = "com.submarine.gameservices.AndroidGameServices";
    private Activity activity;
    private GameHelper mHelper;


    public AndroidGameServices(Activity activity) {
        this.activity = activity;
        mHelper = new GameHelper(this.activity, (GameHelper.CLIENT_GAMES));
        mHelper.setup(this);
        mHelper.enableDebugLog(true);

    }

    public void onActivityResult(int request, int response, Intent data) {
        mHelper.onActivityResult(request, response, data);
    }


    // // ************** GOOGLE PART ***************\\\\\\\

    @Override
    public void onSignInFailed() {
        Gdx.app.log(TAG, "Sing in Fail");
        mHelper.showFailureDialog();
    }

    @Override
    public void onSignInSucceeded() {
        Gdx.app.log(TAG, "Sign in success");
    }

    // // ************** END GOOGLE PART ***************\\\\\\\

    @Override
    public boolean isSignedIn() {
        return mHelper.isSignedIn();
    }

    @Override
    public void login() {
        if (mHelper.isConnecting()) {
            return;
        }
        mHelper.onStart(activity);
    }

    @Override
    public void logout() {
        mHelper.onStop();
    }

    @Override
    public void submitScore(final String leaderBoardId, final long score) {
        if (isSignedIn()) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Games.Leaderboards.submitScore(mHelper.getApiClient(), leaderBoardId, score);
                }
            });
        }
    }

    @Override
    public void showLeaderBoard(final String leaderBoardId) {
        Gdx.app.log(TAG, "Show Leaderboard : " + isSignedIn());
        if (isSignedIn()) {
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Gdx.app.log(TAG, "Show Leaderboard");
                    activity.startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mHelper.getApiClient(), leaderBoardId), 2);
                }
            });
        }
    }

    @Override
    public void unlockAchievement(final String achievementId) {
        if (isSignedIn()) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Games.Achievements.unlock(mHelper.getApiClient(), achievementId);
                }
            });
        }
    }

    @Override
    public void incrementAchievement(final String achievementId, final int incrementAmount) {
        if (isSignedIn()) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Games.Achievements.increment(mHelper.getApiClient(), achievementId, incrementAmount);
                }
            });
        }
    }

    @Override
    public void showAchievements() {
        Gdx.app.log(TAG, "Show Achievements : " + isSignedIn());
        if (isSignedIn()) {
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Gdx.app.log(TAG, "Show Achievements");
                    activity.startActivityForResult(Games.Achievements.getAchievementsIntent(mHelper.getApiClient()), 1);
                }
            });
        }
    }
}
