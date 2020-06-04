package com.fenxiangz.learn.leetcode;

public class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }

    public void print() {
        ListNode n = this;
        while (n != null) {
            System.out.print(" -> " + n.val);
            n = n.next;
        }
    }
}