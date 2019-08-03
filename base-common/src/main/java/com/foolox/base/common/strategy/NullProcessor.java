package com.foolox.base.common.strategy;

import com.alibaba.fastjson.JSONObject;
import com.foolox.base.constant.annotation.Strategy;
import com.foolox.base.constant.annotation.ProcessorType;
import lombok.extern.slf4j.Slf4j;

/**
 * comment: 默认策略，当根据key找不到策略时使用
 *
 * @author: lipengfei
 * @date: 30/07/2019
 */
@Slf4j
@Strategy(ProcessorType.NULL)
public class NullProcessor extends Processor {
    @Override
    public void process(Long userId, JSONObject param) {
        log.error("incorrect invoke for null processor, userId is [{}]", userId);
    }
}
