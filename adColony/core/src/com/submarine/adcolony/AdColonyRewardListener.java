package com.submarine.adcolony;

/**
 * Created by mariam on 3/9/15.
 */
public interface AdColonyRewardListener {
    void reward(boolean success, String currencyName, int amount);
}
