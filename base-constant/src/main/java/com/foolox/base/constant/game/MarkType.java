package com.foolox.base.constant.game;

import java.util.Arrays;
import java.util.List;

/**
 * comment: 叫分类型游戏的底分
 *
 * @author: lipengfei
 * @date: 30/07/2019
 */
public interface MarkType {
    List<Integer> MARK_1 = Arrays.asList(1, 2);
    List<Integer> MARK_2 = Arrays.asList(2, 4);
    List<Integer> MARK_3 = Arrays.asList(3, 6);
    List<Integer> MARK_4 = Arrays.asList(4, 8);
    List<Integer> MARK_5 = Arrays.asList(5, 10);
    List<Integer> MARK_11 = Arrays.asList(5, 10, 20);
    List<Integer> MARK_12 = Arrays.asList(10, 20, 40);
    List<Integer> MARK_13 = Arrays.asList(20, 40, 80);
    List<Integer> MARK_14 = Arrays.asList(30, 60, 120);
    List<Integer> MARK_15 = Arrays.asList(40, 80, 160);
    List<Integer> MARK_16 = Arrays.asList(50, 100, 200);
}
