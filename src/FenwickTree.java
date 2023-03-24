package src;


//Binary Indexed tree or Fenwick tree.

public class FenwickTree {
    int[] bit;
    int[][] bit2d;
    int size;
    int size2d;
    int[] arr;
    int[][] arr2d;
    public FenwickTree(int[] arr, int size){
        this.arr=arr;
        this.size=size;
        bit=new int[size+1];
        
    }
    public FenwickTree(int[][] arr2d, int size){
        this.arr2d=arr2d;
        bit2d=new int[size+1][size+1];
        this.size2d=size;
    }

    public void buildBIT(){
        for(int i=0;i<size;i++){
            update(i, arr[i]);
        }
    }

    // point update and range or point query

    public void update(int index, int val){
        index++;
        while(index<=size){{
            bit[index]+=val;
            index=index+(index&(-index));
        }}
    }

    public void getSum(int l, int r){
        if(l<0|| l>r || r>size){
            System.out.println("Invalid input");
            return;
        }
        int ll=0;
        if(l!=0){
            ll=getSumUtil(l-1);
        }
        int rr=getSumUtil(r);
        System.out.println("sum in range: "+(rr-ll));
    }

    private int getSumUtil(int index){
        index++;
        int sum=0;
        while(index>0){
            sum+=bit[index];
            index=index-(index&(-index));
        }
        return sum;
    }


    //range update and point query;

    public void updateRange(int l , int r, int val){
        update(l, val);
        update(r+1, -val);
    }

    public void pointSum(int index){
        int sum=0;
        index++;
        while(index>0){
            sum+=bit[index];
            index-=(index&(-index));
        }
        System.out.println("point sum : "+sum);
    }

    //2d binary indexed tree

    public void buildBIT2D(){
        for(int i=0;i<size2d;i++){
            for(int j=0;j<size2d;j++){
                update2d(i, j, arr2d[i][j]);
            }
        }
    }

    public void update2d(int f, int s, int val){
        f++;
        s++;
        for(int i=f;i<size2d+1;i=i+(i&(-i))){
            for(int j=s;j<size2d+1;j=j+(j&(-j))){
                bit2d[i][j]+=val;
            }
        }
        
    }

    public void getSum2d(int l1, int r1, int l2, int r2){
        int ans=getsum2dutil(l2, r2)-getsum2dutil(l1-1, r2)-getsum2dutil(l2, r1-1)+getsum2dutil(l1-1, r1-1);
        System.out.println("sum in 2d array: "+ans); 
    }

    public int getsum2dutil(int l, int r){
        int sum=0;
        l++;
        r++;
        for(int i=l;i>0;i=i-(i&(-i))){
            for(int j=r;j>0;j=j-(j&(-j))){
                sum+=bit2d[i][j];
            }
        }
        return sum;
    }






    
}
