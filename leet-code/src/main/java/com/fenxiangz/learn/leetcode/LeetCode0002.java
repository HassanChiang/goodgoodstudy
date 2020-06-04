package com.fenxiangz.learn.leetcode;

/**
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。(
 * <p>
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 * <p>
 * 您可以假设除了数字 0之外，这两个数都不会以 0 开头。
 * <p>
 * 示例：
 * <p>
 * 输入：(2->4->3)+(5->6->4)
 * 输出：7->0->8
 * 原因：342+465=807
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/add-two-numbers
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */

public class LeetCode0002 {
    public static void main(String[] args) {
        try {
            ListNode n1 = new ListNode(9);

            ListNode n2 = new ListNode(1);
            n2.next = new ListNode(9);
            n2.next.next = new ListNode(9);
            n2.next.next.next = new ListNode(9);
            n2.next.next.next.next = new ListNode(9);
            n2.next.next.next.next.next = new ListNode(9);
            n2.next.next.next.next.next.next = new ListNode(9);
            n2.next.next.next.next.next.next.next = new ListNode(9);
            n2.next.next.next.next.next.next.next.next = new ListNode(9);
            n2.next.next.next.next.next.next.next.next.next = new ListNode(9);


            ListNode result = new Solution().addTwoNumbers(n1, n2);
            result.print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class Solution {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            if (l1 == null) {
                return l2;
            }
            if (l2 == null) {
                return l1;
            }
            ListNode ret = null;
            ListNode tail = null;
            int carry = 0;
            while (l1 != null || l2 != null) {
                int v1 = l1 == null ? 0 : l1.val;
                int v2 = l2 == null ? 0 : l2.val;
                int sum = carry + v1 + v2;
                int left = sum % 10;
                if (ret == null) {
                    ret = new ListNode(left);
                    tail = ret;
                } else {
                    tail.next = new ListNode(left);
                    tail = tail.next;
                }
                if (l1 != null) {
                    l1 = l1.next;
                }
                if (l2 != null) {
                    l2 = l2.next;
                }
                carry = sum / 10;
            }
            if (carry > 0) {
                tail.next = new ListNode(carry);
            }
            return ret;
        }
    }
}
