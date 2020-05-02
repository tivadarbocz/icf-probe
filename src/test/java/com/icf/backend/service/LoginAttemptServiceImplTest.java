package com.icf.backend.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoginAttemptServiceImplTest {

    private static final String KEY_127_0_0_1 = "127.0.0.1";
    private static final String KEY_127_0_0_2 = "127.0.0.2";
    private static final String KEY_127_0_0_3 = "127.0.0.3";
    private static final String KEY_127_0_0_4 = "127.0.0.4";

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Test
    void isBlockedTest() {
        Assert.assertFalse(this.loginAttemptService.isBlocked(KEY_127_0_0_1));
    }

    @Test
    void isBlockedTestWith2WrongAttempts() {
        this.loginAttemptService.loginFailed(KEY_127_0_0_2);
        this.loginAttemptService.loginFailed(KEY_127_0_0_2);

        Assert.assertFalse(this.loginAttemptService.isBlocked(KEY_127_0_0_2));
    }

    @Test
    void isBlockedTestWith3WrongAttempts() {
        this.loginAttemptService.loginFailed(KEY_127_0_0_3);
        this.loginAttemptService.loginFailed(KEY_127_0_0_3);
        this.loginAttemptService.loginFailed(KEY_127_0_0_3);

        Assert.assertTrue(this.loginAttemptService.isBlocked(KEY_127_0_0_3));
    }

    @Test
    void loginSucceededTest() {
        this.loginAttemptService.loginFailed(KEY_127_0_0_4);
        this.loginAttemptService.loginFailed(KEY_127_0_0_4);
        this.loginAttemptService.loginFailed(KEY_127_0_0_4);
        this.loginAttemptService.loginSucceeded(KEY_127_0_0_4);

        Assert.assertFalse(this.loginAttemptService.isBlocked(KEY_127_0_0_4));
    }
}