package com.fenxiangz.learn.leetcode;

/**
 * 合并 k 个排序链表，返回合并后的排序链表。请分析和描述算法的复杂度。
 *
 * 示例:
 *
 * 输入:
 * [
 *   1->4->5,
 *   1->3->4,
 *   2->6
 * ]
 * 输出: 1->1->2->3->4->4->5->6
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/merge-k-sorted-lists
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class LeetCode0023 {
    public static void main(String[] args) {
        try {
            ListNode n1 = new ListNode(-1);
            n1.next = new ListNode(-1);
            n1.next.next = new ListNode(-1);

            ListNode n2 = new ListNode(-2);
            n2.next = new ListNode(-2);
            n2.next.next = new ListNode(-1);

            ListNode[] lists = new ListNode[]{n1, n2};

            ListNode result = new Solution().mergeKLists(lists);
            result.print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class Solution {
        public ListNode mergeKLists(ListNode[] lists) {
            if (lists == null || lists.length == 0) {
                return null;
            } else if (lists.length == 1) {
                return lists[0];
            }
            for (int i = 1; i < lists.length; i++) {
                ListNode pointerA = lists[0];
                ListNode pointerLast = null;
                while (lists[i] != null) {
                    ListNode pointerB = lists[i];
                    if (pointerA == null) {
                        if (pointerLast == null) {
                            lists[0] = pointerB;
                        } else {
                            pointerLast.next = pointerB;
                        }
                        break;
                    }
                    //A链指针往下走，直到遇到next value < B链指针的位置
                    while (pointerA.next != null && pointerA.next.val < pointerB.val) {
                        pointerA = pointerA.next;
                    }
                    if (pointerA.val <= pointerB.val) {
                        ListNode nextA = pointerA.next;
                        if (nextA == null) {
                            //A链已经结束，记录A链最后节点
                            pointerLast = pointerA;
                        }
                        ListNode tmp = pointerB.next;
                        pointerB.next = nextA;
                        pointerA.next = pointerB;
                        pointerA = pointerB;   //指针前进
                        lists[i] = tmp;
                    } else {
                        lists[i] = pointerB.next;
                        pointerB.next = pointerA;
                        pointerA = pointerB;  //指针回退
                        lists[0] = pointerB;
                    }
                }
            }
            return lists[0];
        }
    }
}
