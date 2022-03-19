package org.dddjourney.minisignpocbackend.business.domain;

import org.dddjourney.minisignpocbackend.infrastructure.process.InternalMinisignResult;

public interface Minisign {

    InternalMinisignResult version();

    InternalMinisignResult verifyFile(String payloadFile, String signatureFile, String publicKeyFile);

    InternalMinisignResult signFile(String password, String payloadFile, String secretKeyFile, String signatureFile);
}
