package com.fenxiangz.learn.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
 * 
 * 示例 1:
 * 
 * 输入: s = "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * 示例 2:
 * 
 * 输入: s = "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * 示例 3:
 * 
 * 输入: s = "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *   请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 *  
 * 提示：
 * 
 * 0 <= s.length <= 5 * 104
 * s 由英文字母、数字、符号和空格组成
 * 
 */

public class LeetCode0003 {
    public static void main(String[] args) {
        try {
            int result = new Solution().lengthOfLongestSubstring(" ");
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class Solution {
        public int lengthOfLongestSubstring(String s) {
            int n = s.length();
            List<Integer> tmpArr = new ArrayList<>();
            Integer[] maxArr = new Integer[0];
            for (int i = 0; i < n; i++) {
                int x = (int)s.charAt(i);
                //确认是否有重复的字符，并记录 tmpArr 中的位置（j）
                boolean isInArr = false;
                int j = 0;
                for (; j < tmpArr.size(); j++) {
                    if (tmpArr.get(j).equals(x)) {
                        isInArr = true;
                        break;
                    }
                }
                //如果字符重复
                if (isInArr) {
                    // 保存当前最大的字符串
                    if (maxArr.length < tmpArr.size()) {
                        maxArr = tmpArr.toArray(new Integer[tmpArr.size()]);
                    }
                    // 裁剪动态数组
                    tmpArr.add(x);
                    tmpArr = tmpArr.subList(j + 1, tmpArr.size());
                } else {
                    //不重复，直接存入临时数组
                    tmpArr.add(x);
                }
            }
            return Math.max(maxArr.length, tmpArr.size());
        }
    }
}
