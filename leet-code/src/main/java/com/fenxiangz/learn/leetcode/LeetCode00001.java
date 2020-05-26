package com.fenxiangz.learn.leetcode;


import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个整数数组 nums?和一个目标值 target，请你在该数组中找出和为目标值的那?两个?整数，并返回他们的数组下标。
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
 * 示例:
 * 给定 nums = [2, 7, 11, 15], target = 9
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 */
public class LeetCode00001 {
    public static void main(String[] args) {
        int[] result = new Solution2().twoSum(new int[]{2, 7, 11, 15}, 9);
        System.out.printf("result:" + JSON.toJSON(result));
    }

    private static class Solution1 {
        public int[] twoSum(int[] nums, int target) {
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                map.put(nums[i], i);
            }
            int[] ret = new int[2];
            for (int i = 0; i < nums.length; i++) {
                int a = nums[i];
                int b = target - a;
                Integer index = map.get(b);
                if (index != null && !index.equals(i)) {
                    ret[0] = i;
                    ret[1] = index;
                    return ret;
                }
            }
            throw new IllegalArgumentException("No two sum solution");
        }
    }

    private static class Solution2 {
        public int[] twoSum(int[] nums, int target) {
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                int complement = target - nums[i];
                if (map.containsKey(complement)) {
                    return new int[]{map.get(complement), i};
                }
                map.put(nums[i], i);
            }
            throw new IllegalArgumentException("No two sum solution");
        }
    }
}