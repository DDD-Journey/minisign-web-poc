package org.dddjourney.minisignpocbackend.business.domain.minisign;

import java.io.File;

public interface Minisign {

    MinisignResult version();

    MinisignResult verifyFile(String payloadFile, String signatureFile, String publicKeyFile);

    MinisignResult signFile(String password, String payloadFile, String secretKeyFile, File signatureFile);

    MinisignResult createKeys(File pubKeyFile, String password, File secretKeyFile);
}
