package src;

class Node{
    int key;
    Node left;
    Node right;
    int height;
    public Node(int key){
        this.key=key;
        this.height=1;
        left=null;
        right=null;
    }

}

public class AVLTree {
    public Node root;

    private Node rightRotate(Node root){
        Node newRoot=root.left;
        Node temp=newRoot.right;

        newRoot.right=root;
        root.left=temp;
        

        root.height=getHeight(root)+1;
        newRoot.height=getHeight(newRoot)+1;
        return newRoot;
    }

    private Node leftRotate(Node root){
        Node newRoot=root.right;
        Node temp=newRoot.left;
        newRoot.left=root;
        root.right=temp;
        root.height=getHeight(root)+1;
        newRoot.height=getHeight(newRoot)+1;
        return newRoot;

    }

    private int getBalanceFactor(Node root){
        if(root.left==null){
            return (-root.right.height);
        }
        if(root.right==null){
            return root.left.height;
        }
        return root.left.height-root.right.height;
    }

    private int getHeight(Node root){
        if(root.left==null&&root.right==null){
            return 0;
        }
        if(root.left==null){
            return root.right.height;
        }
        if(root.right==null){
            return root.left.height;
        }

        return Math.max(root.left.height, root.right.height);
    }

    public Node insert(Node root, int key){
        if(root==null){
            return new Node(key);
        }
        
        if(key<root.key){
            root.left=insert(root.left, key);
        }else if(key>root.key){
            root.right=insert(root.right, key);
        }else{
            return root;
        }
        root.height=getHeight(root)+1;

        int balanceFactor=getBalanceFactor(root);
        if(balanceFactor>1){
            if(root.left.key>key){
                root=rightRotate(root);
            }else{
                root.left=leftRotate(root.left);
                root=rightRotate(root);
            }

        }else if(balanceFactor<-1){
            if(root.right.key<key){
                root=leftRotate(root);
            }else{
                root.right=rightRotate(root.right);
                root=leftRotate(root);
            }
        }

        return root;

        }



        private Node getSuccssor(Node root){
            while(root.left!=null){
                root=root.left;
            }
            return root;
        }


        public Node delete(Node root, int key){
            if(root==null){
                return root;
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

                root.key=getSuccssor(root.right).key;
                root.right=delete(root.right, root.key);
            }

            root.height=getHeight(root)+1;

            int balanceFactor=getBalanceFactor(root);
            if(balanceFactor>1){
                if(getBalanceFactor(root.left)>=0){
                    root=rightRotate(root);
                }else{
                    root.left=leftRotate(root.left);
                    root=rightRotate(root);
                }
                
            }else if(balanceFactor<-1){
                if(getBalanceFactor(root.right)>0){
                    root.right=rightRotate(root.right);
                    root=leftRotate(root);
                }else{
                    root=leftRotate(root);
                }

            }

            return root;

        }

        public boolean search(Node root, int key){
            if(root==null){
                return false;
            }
            if(root.key>key){
                return search(root.left, key);
            }else if(root.key<key){
                return search(root.right, key);
            }else{
                return true;
            }
        }

        public void inorder(Node root){
            if(root==null){
                return;
            }

            inorder(root.left);
            System.out.print(root.key+" => ");
            inorder(root.right);
        }

        public void print(Node root, int n){
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


        public void AVLinsert(int key){
            this.root=insert(root, key);
        }

        public void AVLdelete(int key){
            this.root=delete(root, key);
        }

        public void AVLsearch(int key){
            System.out.println(search(root, key));
        }

        public void AVLinorder(){
            inorder(root);
            System.out.println();
        }

        public void AVLprint(int n){
            print(root, n);
        }
    




    }

