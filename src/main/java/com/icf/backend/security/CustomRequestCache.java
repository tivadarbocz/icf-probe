package com.icf.backend.security;

import com.icf.backend.util.SecurityUtil;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Saves unauthenticated requests so we can redirect the user to the page they were trying to access once they’re logged in.
 */
class CustomRequestCache extends HttpSessionRequestCache {

    @Override
    public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
        if (!SecurityUtil.isFrameworkInternalRequest(request)) {
            super.saveRequest(request, response);
        }
    }
}