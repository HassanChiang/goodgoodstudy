package com.fenxiangz.learn.leetcode;

import com.alibaba.fastjson.JSON;

/**
 * 给定一个经过编码的字符串，返回它解码后的字符串。
 *
 * 编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
 *
 * 你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
 *
 * 此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。
 *
 * 示例:
 *
 * s = "3[a]2[bc]", 返回 "aaabcbc".
 * s = "3[a2[c]]", 返回 "accaccacc".
 * s = "2[abc]3[cd]ef", 返回 "abcabccdcdcdef".
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/decode-string
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class LeetCode00394 {
    public static void main(String[] args) {
        String result = new Solution().decodeString("23[a]56[b]89[c]");
        System.out.printf("result: " + JSON.toJSONString(result));
    }

    private static class Solution {
        private int index = 0;
        private char[] chars;

        public String decodeString(String s) {
            chars = s.toCharArray();
            StringBuilder result = decode();
            return result.toString();
        }

        private StringBuilder decode() {
            StringBuilder layer = new StringBuilder();
            int times = 0;
            for (; index < chars.length; index++) {
                char a = chars[index];
                if (48 <= a && a <= 57) {
                    //数字 0-9 , 记录下来，以便后续进行重复计算
                    times = (times * 10 + Integer.parseInt(String.valueOf(a)));
                } else if (a == 91) {
                    //遇到起始符号：[
                    index++;
                    layer.append(calTimes(decode(), times));
                    times = 0;
                } else if (a == 93) {
                    //遇到结束符号：]
                    return layer;
                } else {
                    //字符内容，追加到 layerResult
                    layer.append(a);
                }
            }
            return layer;
        }

        private StringBuilder calTimes(StringBuilder sb0, int times) {
            StringBuilder s = new StringBuilder();
            for (int j = 0; j < times; j++) {
                s.append(sb0);
            }
            return s;
        }
    }
}
