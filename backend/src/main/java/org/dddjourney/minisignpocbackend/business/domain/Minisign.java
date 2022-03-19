package org.dddjourney.minisignpocbackend.business.domain;

public interface Minisign {

    MinisignResult version();

    MinisignResult verifyFile(String payloadFile, String signatureFile, String publicKeyFile);

    MinisignResult signFile(String password, String payloadFile, String secretKeyFile, String signatureFile);
}
