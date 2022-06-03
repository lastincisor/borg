package org.borg.linknode;

import org.borg.vo.ListNode;

import java.util.ArrayDeque;
import java.util.Deque;

public class LinkNodePlush {

    public ListNode andTowNumber(ListNode l1, ListNode l2){
        Deque<Integer> stack1 = new ArrayDeque<Integer>();
        Deque<Integer> stack2 = new ArrayDeque<Integer>();
        while (l1 != null) {
            stack1.push(l1.getVal());
            l1 = l1.getNext();
        }
        while (l2 != null) {
            stack2.push(l2.getVal());
            l2 = l2.getNext();
        }
        int carry = 0;
        ListNode ans = null;
        while (!stack1.isEmpty() || !stack2.isEmpty() || carry != 0) {
            int a = stack1.isEmpty() ? 0 : stack1.pop();
            int b = stack2.isEmpty() ? 0 : stack2.pop();
            int cur = a + b + carry;
            carry = cur / 10;
            cur %= 10;
            ListNode curnode = new ListNode(cur);
            curnode.setNext(ans);
            ans = curnode;
        }
        return ans;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(4);
        ListNode l4 = new ListNode(5);
        l1.setNext(l2);
        l2.setNext(l3);
        l3.setNext(l4);

        ListNode ll1 = new ListNode(3);
        ListNode ll2 = new ListNode(4);
        ListNode ll3 = new ListNode(7);
        ll1.setNext(ll2);
        ll2.setNext(ll3);

        LinkNodePlush lp = new LinkNodePlush();
        ListNode listNode = lp.andTowNumber(l1, ll1);
        while (true){
            if(listNode != null){
                System.out.println(listNode.getVal());
                listNode = listNode.getNext();
            }else {
                break;
            }
        }
    }
}
