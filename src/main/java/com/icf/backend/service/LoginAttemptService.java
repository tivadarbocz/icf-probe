package com.icf.backend.service;

public interface LoginAttemptService {

    /**
     * If the login was successful, then we remove the ip address from the cache.
     *
     * @param key IP address
     */
    void loginSucceeded(String key);

    /**
     * If the login failed, then we add the ip address to the cache or
     * increment the number of failure if it was already there.
     *
     * @param key IP address
     */
    void loginFailed(String key);

    /**
     * Determine a given IP address is blocked or not.
     *
     * @param key IP address
     */
    boolean isBlocked(String key);

}
