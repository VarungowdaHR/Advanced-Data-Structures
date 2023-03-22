package src;

class Snode{
    int key;
    Snode left, right;
    Snode(int key){
        this.key=key;
    }
}

public class SplayTree {
    Snode root;
    public SplayTree(){
        root=null;
    }
    


    private Snode rightRotate(Snode node){
        Snode newnode=node.left;
        node.left=newnode.right;
        newnode.right=node;
        return newnode;
    }

    private Snode leftRotate(Snode node){
        Snode newnode=node.right;
        node.right=newnode.left;
        newnode.left=node;
        return newnode;
    }
    

    // insertion in splay tree................
    private Snode insert(Snode node, int key){
        if(node==null){
            return new Snode(key);
        }
        if(key<node.key){
            node.left=insert(node.left, key);
        }else if(key>node.key){
            node.right=insert(node.right, key);
        }else{
            return node;
        }

        if(key<node.key){
            node=rightRotate(node);

        }else if(key>node.key){
            node=leftRotate(node);
        }

        return node;
    }

    // searching in splay tree.....................
    private Snode search(Snode node, int key){
        if(node==null){
            System.out.println("key not present");
            return null;
        }

        if(key<node.key){
            node.left=search(node.left, key);
        }else if(key>node.key){
            node.right=search(node.right, key);
        }else{
            return node;
        }

        if(node.left!=null && node.left.key==key){
            node=rightRotate(node);

        }else if(node.right!=null && node.right.key==key){
            node=leftRotate(node);
        }

        return node;

    }

    // deleting a node in splay tree(same as bst);.............
    private Snode delete(Snode root, int key){
        if(root==null){
            System.out.println("key not present");
            return null;
        }

        if(key<root.key){
            root.left=delete(root.left, key);
        }else if(key>root.key){
            root.right=delete(root.right, key);
        }else{
            if(root.left==null){
                return root.right;
            }
            if(root.right==null){
                return root.left;
            }
            Snode parent =root;
            Snode replace=root.right;
            while(replace.left!=null){
                parent =replace;
                replace=replace.left;
            }
            if(parent==root){
                replace.left=parent.left;
                parent.left=null;
                return replace;
            }else{
                root.key=replace.key;
                parent.left=replace.right;
                return root;
            }
        }
        return root;

    }
    //printing inorder traversal.................
    private void inorder(Snode root){
        if(root==null){
            return;
        }

        inorder(root.left);
        System.out.print(root.key+" ==> ");
        inorder(root.right);
    }

    //printing pattern..................

    private void print(Snode root, int n){
        if(root==null){
            return;
        }
        print(root.left, n-3);
        for(int i=0;i<n;i++){
            System.out.print("-");
        }
        System.out.print(root.key);
        System.out.println();
        print(root.right, n-3);
    }

    public void splayinsert(int key){
        this.root=insert(root, key);
    }

    public void splaysearch(int key){
        this.root=search(root, key);
    }

    public void splaydelete(int key){
        this.root=delete(root, key);
    }

    public void splayinorder(){
        inorder(root);
        System.out.println();
    }

    public void splayprint(int n){
        print(root, n);
    }


}
