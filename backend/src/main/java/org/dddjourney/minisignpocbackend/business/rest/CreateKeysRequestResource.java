package org.dddjourney.minisignpocbackend.business.rest;

import lombok.Data;

@Data
public class CreateKeysRequestResource {
    private String password;
    private String fileName;
}
