package com.fenxiangz.learn.leetcode;

import com.alibaba.fastjson.JSON;

public class LeetCode {
    public static void main(String[] args) {
        int result = new Solution().method();
        System.out.printf("result:" + JSON.toJSONString(result));
    }

    private static class Solution {
        public int method() {
            return 0;
        }
    }
}
