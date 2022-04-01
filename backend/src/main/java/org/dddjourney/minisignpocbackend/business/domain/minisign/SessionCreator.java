package org.dddjourney.minisignpocbackend.business.domain.minisign;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SessionCreator {

    public String createSessionId() {
        return UUID.randomUUID().toString();
    }
}
