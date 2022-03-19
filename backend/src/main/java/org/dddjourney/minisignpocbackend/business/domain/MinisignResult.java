package org.dddjourney.minisignpocbackend.business.domain;

import java.util.List;

public interface MinisignResult {

    int getExitValue();

    boolean isExitedGraceful();

    String getProcessFeedback();

    String getProcessError();

    List<String> getCreatedFiles();

}
