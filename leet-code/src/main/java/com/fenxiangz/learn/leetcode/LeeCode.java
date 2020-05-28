package com.fenxiangz.learn.leetcode;

import com.alibaba.fastjson.JSON;

public class LeeCode {
    public static void main(String[] args) {
        int result = new Solution2().method();
        System.out.printf("result:" + JSON.toJSONString(result));
    }

    private static class Solution1 {

    }

    private static class Solution2 {
        public int method() {
            return 0;
        }
    }
}
