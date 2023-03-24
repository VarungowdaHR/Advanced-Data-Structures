package src;
class STNode{
    int o;
    int c;
    int t;
    public STNode(int o, int c, int t){
         this.o=o;
        this.c=c;
        this.t=t;
    }

}

public class SegmentTree {
    private int[] st;
    private int size;
    private int[] arr;
    private int[] lazy;
    private STNode[] stb;
    public SegmentTree(int[] arr, int size){
        this.arr=arr;
        this.size=size;
        int height=(int) (Math.ceil(Math.log(size)/Math.log(2)));
        int t=(int) (2*Math.pow(2, height)-1);
        lazy=new int[t];
        st=new int[t];
    }

    public int buildST(int s, int e, int child){
        if(s==e){
            st[child]=arr[s];
            return arr[s];
        }
        int m=s+(e-s)/2;
        int left=buildST(s, m, 2*child+1);
        int right=buildST(m+1, e, 2*child+2);
        st[child]=left+right;
        return st[child];
    }

    public void buildSTWithoutReturn(int s, int e, int child){
        if(s==e){
            st[child]=arr[s];
            return;
        }
        int m=s+(e-s)/2;
        buildSTWithoutReturn(s, m, 2*child+1);
        buildSTWithoutReturn(m+1, e, 2*child+2);
        st[child]=st[2*child+1]+st[2*child+2];
    }

    public void updateST(int s, int e, int val, int index, int degree){
        if(index<s || index>e){
            return;
        }
        if(s<=index&&e>=index){
            st[degree]+=val;
        }
        if(s==e){
            arr[s]+=val;
            return;
        };
        
        int m=s+(e-s)/2;
        updateST(s, m, val, index, 2*degree+1);
        updateST(m+1, e, val, index, 2*degree+2);
    }

    //another approch.
    public void updateST2(int s, int e, int val, int index, int degree){
        if(s==e){
            st[degree]+=val;
            arr[s]+=val;
            return;
        }
        int m=s+(e-s)/2;
        if(index<=m){
            updateST2(s, m, val, index, 2*degree+1);
        }else{
             updateST2(m+1, e, val, index, 2*degree+2);
        }
            st[degree]=st[2*degree+1]+st[2*degree+2];
        }


        public int getSum(int s, int e, int l, int r, int degree){
            if(s>e || r<s || l>e){
                return 0;
            }
            if(s>=l&&e<=r){
                return st[degree];
            }
            int m=s+(e-s)/2;
            int left=getSum(s, m, l, r, 2*degree+1);
            int right=getSum(m+1, e, l, r, 2*degree+2);
            return left+right;
        }


        //range update & range sum

        public void rangeUpdate(int s, int e, int l, int r,int val, int degree){
            if(lazy[degree]!=0){
                int children=e-s+1;
                st[degree]+=children*lazy[degree];
                if(s!=e){
                    lazy[2*degree+1]+=lazy[degree];
                    lazy[2*degree+2]+=lazy[degree];
                }
                lazy[degree]=0;
            }

            if(s>=l&&e<=r){
                int children=e-s+1;
                st[degree]+=children*val;
                if(s!=e){
                    lazy[2*degree+1]+=val;
                    lazy[2*degree+2]+=val;
                }
                return;
            }

            if(s>e || r<s || l>e){
                return;
            }
            int mid=s+(e-s)/2;
            rangeUpdate(s, mid, l, r,val, 2*degree+1);
            rangeUpdate(mid+1, e, l, r,val, 2*degree+2);
            st[degree]=st[2*degree+1]+st[2*degree+2];
        }

        public int getRangeSum(int s, int e, int l, int r, int degree){
            if(lazy[degree]!=0){
                int children=(e-s)+1;
                st[degree]+=children*lazy[degree];
                if(s!=e){
                    st[2*degree+1]+=lazy[degree];
                    st[2*degree+2]+=lazy[degree];
                }
                lazy[degree]=0;
            }
            if(r<s || l>e){
                return 0;
            }
            if(s>=l&&e<=r){
                return st[degree];
            }
            int mid=s+(e-s)/2;
            return getRangeSum(s, mid, l, r, 2*degree+1) + getRangeSum(mid+1, e, l, r, 2*degree+2);
        }


        public void getMin(int l, int r){
            buildMinST(0, size-1, 0);
            System.out.println("min: "+ getMinHelper(0, size-1,l, r, 0));
        
        }


       void buildMinST(int s, int e, int degree){
            if(s==e){
                st[degree]=arr[s];
                return;
            }

            int mid=s+(e-s)/2;
            buildMinST(s, mid, 2*degree+1);
            buildMinST(mid+1, e, 2*degree+2);
            st[degree]=Math.min(st[2*degree+1],st[2*degree+2]);
        }


        int getMinHelper(int s, int e, int l, int r, int degree){
            if(r<s || l>e){
                return 500;
            }
            if(l<=s && r>=e){
                return st[degree];
            }
            int mid=s+(e-s)/2;
            int left=getMinHelper(s, mid, l, r, 2*degree+1);
            int right=getMinHelper(mid+1, e, l, r, 2*degree+2);
            return Math.min(left, right);
        }

        public void getXOR(int l , int r){
            buildXorST(0, size-1, 0);
           
            System.out.println("XOR: "+ getXorHelper(0, size-1, l, r, 0));
        }

        private void buildXorST(int s, int e, int degree){
            if(s==e){
                st[degree]=arr[s];
                return;
            }

            int mid=s+(e-s)/2;
            buildXorST(s, mid, 2*degree+1);
            buildXorST(mid+1, e, 2*degree+2);
            st[degree]=st[2*degree+1]^st[2*degree+2];
        }

        private int getXorHelper(int s, int e, int l, int r, int degree){
            if(l>e || r<s){
                return 0;
            }
            if(l<=s && r>=e){
                return st[degree];
            }

            int mid=s+(e-s)/2;
            int left=getXorHelper(s, mid, l, r, 2*degree+1);
            int right=getXorHelper(mid+1, e, l, r, 2*degree+2);
            return left^right;
        }

        //problem: xor and or operation in alternative level.
        public void XORandOR(){
            boolean t=((int) (Math.log(size)/Math.log(2))%2==0)?false:true;
           
            System.out.println("xor and or : "+ getXORandORHelper(0, size-1, 0, t));
        }

        private int getXORandORHelper(int s, int e, int degree, Boolean flag){
            if(s==e){
                st[degree]=arr[s];
                return arr[s];
            }

            int mid=s+(e-s)/2;
            int left=getXORandORHelper(s, mid, 2*degree+1, !flag);
            int right=getXORandORHelper(mid+1, e, 2*degree+2, !flag);
            if(flag){
                st[degree]=left|right;
            }else{
                st[degree]=left^right;
            }

            return st[degree];

        }

        public void BracketsCount(String s){
        int height=(int) (Math.ceil(Math.log(s.length())/Math.log(2)));
        int t=(int) (2*Math.pow(2, height)-1);
        stb=new STNode[t];
        buildSTB(0, s.length()-1, 0, s);
        }

        public void getBracketsCount(String s, int l, int r){
            if(stb==null){
                System.out.println("build STB");
                return;
            }
            STNode ans= getBracketsCountHelper(0, s.length()-1, l, r, 0, s);
            System.out.println("good Brackets: "+(ans.t*2));
        }


        private STNode merge(STNode left, STNode right){
            STNode newnode=new STNode(0, 0, 0);
            newnode.o=left.o+right.o-Math.min(left.o, right.c);
            newnode.c=left.c+right.c-Math.min(left.o, right.c);
            newnode.t=left.t+right.t+Math.min(left.o, right.c);
            return newnode;
        }

        private void buildSTB(int s, int e, int degree, String str){

            if(s==e){
                STNode newnode=new STNode(0,0,0);
                if(str.charAt(s)=='('){
                    newnode.o=1;
                    stb[degree]=newnode;
                }else{
                    newnode.c=1;
                    stb[degree]=newnode;
                }
                return;
            }

                int mid=s+(e-s)/2;
                buildSTB(s, mid, 2*degree+1, str);
                buildSTB(mid+1, e, 2*degree+2, str);

                STNode left =stb[2*degree+1];
                STNode right =stb[2*degree+2];
                stb[degree]=merge(left, right);
            }


            private STNode getBracketsCountHelper(int s, int e, int l, int r, int degree, String str){

                if(r<s || l>e){
                    return new STNode(0, 0, 0);
                }

                if(l<=s && r>=e){
                    return stb[degree];
                }
                int mid=s+(e-s)/2;
                STNode left=getBracketsCountHelper(s, mid, l, r, 2*degree+1, str);
                STNode right=getBracketsCountHelper(mid+1, e, l, r, 2*degree+2, str);
                return merge(left, right);
        }

        

}
