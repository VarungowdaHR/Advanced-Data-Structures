package src;

import java.util.LinkedList;
import java.util.Queue;

class Bnode{
    int[] keys;
    Bnode[] childrens;
    boolean isLeaf;
    int n;
    Bnode(int order){
        keys=new int[order];
        childrens=new Bnode[order+1];
        isLeaf=false;
        n=0;
    }
}

public class BTree {
    Bnode root;
    private final int ORDER=5;
    public BTree(){
        root=null;
    }

    private void traversal(Bnode node){
       if(node.isLeaf){
            for(int i=0;i<node.n;i++){
                System.out.print(node.keys[i]+" ");
            }
        return;
       }

       for(int i=0;i<node.n;i++){
        traversal(node.childrens[i]);
        System.out.print(node.keys[i]+" ");
       }

       if(!node.isLeaf){
        traversal(node.childrens[node.n]);
       }
    }

    private void levelorder(Bnode node){
        Queue<Bnode> q=new LinkedList<>();
        q.add(node);
        while(!q.isEmpty()){
            Bnode r=q.poll();
            for(int i=0;i<r.n;i++){
                System.out.print(r.keys[i]+" ");
            }
            System.out.println();
            if(!r.isLeaf){
                for(int i=0;i<=r.n;i++){
                    q.add(r.childrens[i]);
                }
            }
            
        }
    }

    private boolean search(Bnode node, int key){
        int i=0;
        while(i<node.n&&key>node.keys[i]){
            i++;
        }
        if(i<node.n && node.keys[i]==key){
            return true;
        }
        if(i<=node.n && !node.isLeaf){
            return search(node.childrens[i], key);
        }
        return false;
    }

    private void insert(Bnode node, int key){
        if(node==null){
            Bnode newnode=new Bnode(ORDER);
            newnode.keys[0]=key;
            newnode.isLeaf=true;
            newnode.n++;
            this.root=newnode;
            return;
        }

        if(node.n==ORDER){
            Bnode newnode=new Bnode(ORDER);
            newnode.childrens[0]=node;
            splitNode(newnode, node, 0);
            insertIfNotFull(newnode, key);
            this.root=newnode;
            return;
        }

        insertIfNotFull(node, key);
    }

    private void insertIfNotFull(Bnode node, int key){
        int j=0;
        while(j<node.n && key>node.keys[j]){
            j++;
        }

        if(node.isLeaf){
            for(int i=node.n;i>j;i--){
                node.keys[i]=node.keys[i-1];
            }
            node.keys[j]=key;
            node.n++;
            return;
        }
       
        
        if(!node.isLeaf){
            if(node.childrens[j].n==ORDER){
                splitNode(node, node.childrens[j], j);

                if(key<node.keys[j]){
                    insertIfNotFull(node.childrens[j], key);
               }else{
                   insertIfNotFull(node.childrens[j+1], key);
               }
                
            }else{
                insertIfNotFull(node.childrens[j], key);
            }
           
        }
    }



    private void splitNode(Bnode parent, Bnode full, int index){
        int mid=full.n/2;
        Bnode right=new Bnode(ORDER);

        if(full.isLeaf) right.isLeaf=true;

        for(int i=mid+1;i<full.n;i++){
            right.keys[i-(mid+1)]=full.keys[i];
            right.n++;
        }
        for(int i=mid+1;i<=full.n;i++){
            right.childrens[i-(mid+1)]=full.childrens[i];
        }
        full.n=mid;

        int k=full.keys[mid];
        for(int i=parent.n;i>index;i--){
            parent.keys[i]=parent.keys[i-1];
            parent.childrens[i+1]=parent.childrens[i];
        }
        parent.keys[index]=k;
        parent.childrens[index+1]=right;
        parent.n++;
    }

    public void Btreeinsert(int key){
        insert(root, key);
    }

    public void Btreesearch(int key){
        System.out.println(search(root, key));
    }

    public void Btreeinorder(){
        traversal(root);
        System.out.println();
    }

    public void Btreelevel(){
        levelorder(root);
    }
    
}
