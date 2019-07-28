package com.foolox.base.common.result;

import lombok.Data;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 27/07/2019
 */
@Data
public class CommonError implements OutMessage {
    private String command;
    private CodeMessage message;
}
