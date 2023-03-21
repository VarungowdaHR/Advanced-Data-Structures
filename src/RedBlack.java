package src;

import java.util.LinkedList;
import java.util.Queue;

// Note : insert2 function will not work, use insert
class RDnode{
    int key;
    char color;
    RDnode left, right, parent;
    RDnode(int key){
        this.key=key;
        color='R';
    }
}

public class RedBlack {
    RDnode root;

    private boolean fixred=false;
    private boolean ll=false;
    private boolean rr=false;
    private boolean rl=false;
    private boolean lr=false;

    RDnode LeftRotate(RDnode node){
        RDnode newnode=node.right;
        node.right=newnode.left;
        if(node==this.root){
            this.root=newnode;
        }
        if(node.right!=null){
            node.right.parent=node;
        }
        newnode.left=node;
        newnode.parent=node.parent;
        node.parent=newnode;
        return newnode;
    }

    RDnode rightRotate(RDnode node){
        RDnode newnode=node.left;
        node.left=newnode.right;
        if(node==this.root){
            this.root=newnode;
        }
        if(node.left!=null){
            node.left.parent=node;
        }
        newnode.right=node;
        newnode.parent=node.parent;
        node.parent=newnode;
        return newnode;
    }

    private RDnode insert(RDnode node, int key){
        if(node==null){
            RDnode newnode=new RDnode(key);
            if(this.root==null){
                newnode.color='B';
            }
            return newnode;
        }

        if(key<node.key){
            node.left=insert(node.left, key);
            node.left.parent=node;
            if(node.color=='R' && node.left.color=='R'){
                fixred=true;
            }

        }else if(key>node.key){
            node.right=insert(node.right, key);
            node.right.parent=node;
            if(node.color=='R'&&node.right.color=='R'){
                fixred=true;
            }
        }else{
            return node;
        }

        if(rr){
            node=rightRotate(node);
            node.color='B';
            node.right.color='R';
            rr=false;


        }else if(ll){
            node=LeftRotate(node);
            node.color='B';
            node.left.color='R';
            ll=false;


        }else if(lr){
            node.left=LeftRotate(node.left);
            node.left.parent=node;
            node=rightRotate(node);
            node.color='B';
            node.right.color='R';
            lr=false;
            
        }else if(rl){
            node.right=rightRotate(node.right);
            node.right.parent=node;
            node=LeftRotate(node);
            node.color='B';
            node.left.color='R';
            rl=false;
        }

        if(fixred){
            if(node==node.parent.left){
                if(node.parent.right!=null && node.parent.right.color=='R'){
                    node.color='B';
                    node.parent.right.color='B';
                    if(node.parent!=this.root){
                        node.parent.color='R';
                    }
                }else{
                    if(node.left!=null && node.left.color=='R'){
                        rr=true;
                    }else{
                        lr=true;
                    }
                }

            }else{
                if(node.parent.left!=null&&node.parent.left.color=='R'){
                    node.color='B';
                    node.parent.left.color='B';
                    if(node.parent!=this.root){
                        node.parent.color='R';
                    }
                }else{
                    if(node.right!=null && node.right.color=='R'){
                        ll=true;
                    }else{
                        rl=true;
                    }
                }
            }

            fixred=false;

        }

        return node;
    
    }


    private RDnode getSuccessor(RDnode node){
        RDnode u=node.right;
        if(node.left==null){
            return u;
        }
        if(node.right==null){
            return node.left;
        }
        while(u!=null&&u.left!=null){
            u=u.left;
        }
        return u;
        
    }

    private RDnode getSibling(RDnode node){
        if(node==node.parent.left){
            return node.parent.right;
        }
        return node.parent.left;
    }

    private boolean isLeft(RDnode node){
        if(node.parent.left==node){
            return true;
        }
        return false;
    }

    private void fixdoubleBlack(RDnode node){
        if(node==root){
            root.color='B';
            return;
        }
        RDnode sibling=getSibling(node);
        RDnode parent=node.parent;
        if(sibling==null){
            fixdoubleBlack(node.parent);
            return;
        }

        if(sibling.color=='R'){
            sibling.color='B';
            parent.color='R';
            if(isLeft(sibling)){
                if(parent!=root){
                    if(isLeft(parent)){
                        parent.parent.left=rightRotate(parent);
                    }else{
                        parent.parent.right=rightRotate(parent);
                    }
                }else{
                    rightRotate(parent);
                }
            }else{
                if(parent!=root){
                    if(isLeft(parent)){
                        parent.parent.left=LeftRotate(parent);
                    }else{
                        parent.parent.right=LeftRotate(parent);
                    }
                }else{
                    LeftRotate(parent);
                }
            }
            fixdoubleBlack(node);
            return;
        }

        if(sibling.left!=null&&sibling.left.color=='R' || sibling.right!=null&&sibling.right.color=='R'){
            if(isLeft(sibling)){
                if(sibling.left!=null&&sibling.left.color=='R'){
                    sibling.left.color=sibling.color;
                    sibling.color=parent.color;
                }else{
                    sibling.right.color=parent.color;
                    parent.left=LeftRotate(sibling);
                }
                if(parent!=root){
                    if(isLeft(parent)){
                        node.parent.parent.left=rightRotate(parent);
                    }else{
                        node.parent.parent.right=rightRotate(parent);
                    }
                }else{
                    rightRotate(parent);
                }
            }else{
                if(sibling.right!=null&&sibling.right.color=='R'){
                    sibling.right.color=parent.color;
                    sibling.color=parent.color;  
                }else{
                    sibling.left.color=parent.color;
                    parent.right=rightRotate(sibling);
                }
                if(parent!=root){
                    if(isLeft(parent)){
                        node.parent.parent.left= LeftRotate(parent);
                    }else{
                        node.parent.parent.right= LeftRotate(parent);
                    }
                }else{
                    LeftRotate(parent);
                } 
            }
            parent.color='B';
            return;
        }

        sibling.color='R';
        if(parent.color=='R'){
            parent.color='B';
        }else{
            fixdoubleBlack(parent);
        }
        return;
    }




    private void deleteNode(RDnode node){
        RDnode u =getSuccessor(node);
        RDnode parent=node.parent;
        if(u==null){
            if(root==node){
                this.root=null;
                return;
            }
            if(node.color=='B'){
                fixdoubleBlack(node);
            }
            if(isLeft(node)){
                parent.left=null;
            }else{
                parent.right=null;
            }
            return;
        }
        if(node.left==null || node.right==null){
            if(isLeft(node)){
                parent.left=u;
            }else{
                parent.right=u;
            }
            u.parent=parent;

            if(u.color=='B'&&node.color=='B'){
                fixdoubleBlack(u);
            }else{
                u.color='B';
            }
            return;
        }

        node.key=u.key;
        deleteNode(u);
    }

    private void delete(RDnode node, int key){
        if(node==null){
            System.out.println("key is not present");
            return;
        }
        if(key<node.key){
            delete(node.left, key);
        }else if(key>node.key){
            delete(node.right, key);
        }else{
             deleteNode(node);
        }
        return;
    }

    private void insert2(RDnode node, int key){
        if(node==null){
            this.root=new RDnode(key);
            this.root.color='B';
            return;
        }
        RDnode n=node;
        RDnode prev=node;
        while(n!=null){
            prev=n;
            if(key<n.key){
                n=n.left;
            }else if(key>n.key){
                n=n.right;
            }else{
                break;
            }
        }

        if(prev.key==key){
            return;
        }
        RDnode newnode=new RDnode(key);
        if(key<prev.key){
            prev.left=newnode;
        }else{
            prev.right=newnode;
        }
        newnode.parent=prev;
        fixredred(newnode);
    }

    private RDnode getUncle(RDnode node){
        RDnode gpa=getGrandParent(node);
        if(gpa!=null&&gpa.left==node.parent){
            return gpa.right;
        }
        return (gpa!=null)?gpa.left:null;
    }

    private RDnode getGrandParent(RDnode node){
        return node.parent.parent;
    }

    private void swapColor(RDnode l, RDnode r){
        char temp=l.color;
        l.color=r.color;
        r.color=temp;
    }

    void fixredred(RDnode node){
        if(node==this.root){
            root.color='B';
            return;
        }
        RDnode parent=node.parent;
        if(parent.color=='B') return;
        RDnode grandparent=getGrandParent(node);
        RDnode uncle=getUncle(node);
        if(uncle!=null && uncle.color=='R'){
            uncle.color='B';
            parent.color='B';
            grandparent.color='R';
            fixredred(grandparent);
            return;
        }

        if(isLeft(parent)){
            if(isLeft(node)){
                swapColor(parent, grandparent);
                rightRotate(grandparent);
            }else{
                LeftRotate(parent);
                swapColor(node, grandparent);
                rightRotate(grandparent);
            }
        }else{ 
            if(!isLeft(node)){
                swapColor(parent, grandparent);
                  LeftRotate(grandparent);
            }else{
                rightRotate(parent);
                swapColor(node, grandparent);
                    LeftRotate(grandparent);
            }

        }
    }




    private void inorder(RDnode node){
        if(node==null){
            return;
        }
        inorder(node.left);
        System.out.print(node.key+" => ");
        inorder(node.right);
    }

    private void levelOrder(RDnode node){
        if(node==null){
            System.out.println("empty tree");
            return;
        }
        Queue<RDnode> q=new LinkedList<>();
        q.add(node);
        while(!q.isEmpty()){
            int size=q.size();
            System.out.println();
            while(size!=0){
                RDnode r=q.poll();
                System.out.print(r.key);
                if(r.left!=null){
                    q.add(r.left);
                }
                if(r.right!=null){
                    q.add(r.right);
                }
                size--;
            }
        }
    }

    private void print(RDnode node, int n){
        if(node==null){
            return;
        }
        print(node.left, n-3);
        for(int i=0;i<n;i++){
            System.out.print("-");
        }
        System.out.print(node.key+" "+node.color);
        System.out.println();
        print(node.right, n-3);
    }


    public void RBinsert(int key){
        this.root=insert(root, key);
    }


    public void RBdelete(int key){
        delete(root, key);
    }


    public void RBinsert2(int key){
        insert2(root, key);
    }


    public void RBinorder(){
        inorder(root);
        System.out.println();
    }


    public void RBlevelorder(){
        levelOrder(root);
    }


    public void RBprint(int n){
        print(root, n);
    }


}
