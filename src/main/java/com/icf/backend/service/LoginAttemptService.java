package com.icf.backend.service;

public interface LoginAttemptService {

    /**
     * @param key
     */
    void loginSucceeded(String key);

    /**
     * @param key
     */
    void loginFailed(String key);

    /**
     * @param key
     * @return
     */
    boolean isBlocked(String key);
}
