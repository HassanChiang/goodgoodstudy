package com.fenxiangz.learn.leetcode;


/**
 * 给定一个32位整数 num，你可以将一个数位从0变为1。请编写一个程序，找出你能够获得的最长的一串1的长度。
 *
 * 示例 1：
 * 输入: num = 1775(110111011112)
 * 输出: 8
 *
 * 示例 2：
 * 输入: num = 7(01112)
 * 输出: 4
 */
public class LeetCode01510 {

    public static void main(String[] args) {
        int result = new Solution2().reverseBits(7);
        System.out.printf("result:" + result);
    }


    /**
     * 常规思路
     *
     * 遍历每一位，统计“0分隔”开的前串长度和后串长度；
     * 每次遇到0时，则将前串和后串相加再+1得到当前翻转的最大串（ maxPre + 1 + maxAfter）；
     * 与 max 比较选出最大值保存即可。
     */
    private static class Solution1 {
        public int reverseBits(int num) {
            String[] ss = Integer.toBinaryString(num).split("");
            int max = 0;
            // 0标志前串
            int maxPre = 0;
            // 0标志后串
            int maxAfter = 0;
            for (int i = 0; i < ss.length; i++) {
                if ("0".equals(ss[i])) {
                    max = Math.max(max, maxPre + 1 + maxAfter);
                    maxPre = maxAfter;
                    maxAfter = 0;
                } else {
                    maxAfter++;
                }
            }
            max = Math.max(max, maxPre + 1 + maxAfter);
            return max;
        }
    }

    /**
     * 思路不变，通过位运算实现，提高执行效率
     * https://leetcode-cn.com/problems/reverse-bits-lcci/solution/fan-zhuan-shu-wei-20200525-by-hassan91/
     */
    private static class Solution2 {
        public int reverseBits(int num) {
            int bits = 32;
            int max = 0;
            // 0标志前串
            int maxPre = 0;
            // 0标志后串
            int maxAfter = 0;

            while (bits-- > 0) {
                if ((num & 1) == 0) {
                    max = Math.max(max, maxPre + 1 + maxAfter);
                    maxPre = maxAfter;
                    maxAfter = 0;
                } else {
                    maxAfter++;
                }
                num >>= 1;
            }
            max = Math.max(max, maxPre + 1 + maxAfter);
            return max;
        }
    }
}
