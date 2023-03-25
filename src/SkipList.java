// Implementation of skip list-insert, delete, search operation
package src;
class ListNode{
    int key;
    ListNode[] next;
    ListNode(int k, int s){
        key=k;
        next=new ListNode[s];
    }
}

public class SkipList {
    private final int SIZE=10;
    private int h=(int)(Math.log(SIZE)/Math.log(2));
    ListNode head=new ListNode(-1, h);

    public void insert(int k){
        ListNode n=head;
        ListNode[] update=new ListNode[h];
        for(int i=h-1;i>=0;i--){
            while(n.next[i]!=null&&n.next[i].key<k){
                n=n.next[i];
            }
            update[i]=n;
        }
        int r=(int)(Math.random()*(h)+1);
        ListNode newNode=new ListNode(k, r);
        for(int i=0;i<newNode.next.length;i++){
            newNode.next[i]=update[i].next[i];
            update[i].next[i]=newNode;
        }
    }

    public int search(int key){
        ListNode n=head;
        for(int i=h-1;i>=0;i--){
            while(n.next[i]!=null && n.next[i].key<key){
                n=n.next[i];
            }
        }
        if(n.next[0]!=null && n.next[0].key==key){
            return key;
        }
        return -1;
    }

    public int delete(int key){
        ListNode n=head;
        ListNode[] update=new ListNode[h];
        for(int i=h-1;i>=0;i--){
            while(n.next[i]!=null && n.next[i].key<key){
                n=n.next[i];
            }
            update[i]=n;
        }
        if(n.next[0]!=null&&n.next[0].key==key){
            ListNode r=n.next[0];
            for(int i=0;i<r.next.length;i++){
                update[i].next[i]=r.next[i];
            }
            return r.key;
        }
        return -1;
    }

    public void print(){
        ListNode n=head;
        for(int i=h-1;i>=0;i--){
            System.out.print("Level "+i+": ");
            while(n.next[i]!=null){
                System.out.print(n.next[i].key+" => ");
                n=n.next[i];
            }
            System.out.println();
            n=head;
        }
    }
}
