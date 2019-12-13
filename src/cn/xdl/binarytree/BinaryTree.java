package cn.xdl.binarytree;

public class BinaryTree {

    private Node root = null;
    private int size = 0;

    /* 增加 */
    public void add(int data){
        if(root == null){
            root = new Node(data);
            size++;
        }else{
           root.add(data);
        }
    }
    /* 节点个数 */
    public int size(){
        return size;
    }
    /* 删除 */
    public boolean remove(int data){
        Node node = root;   //要删除的节点
        Node parentNode = root;     //要删除的父节点
        boolean isLeft = true;      //判断要删除的结点是否是左子树
        //循环找到要删除的节点并通过isLeft判断他是左子树还是右子树
        while(node.data != data){
            parentNode = node;      //父节点与要删除的节点同步后移
            if(node.data > data){       //如果当前节点的值大于要删除的节点的值则是向左查找
                node = node.left;
                isLeft = true;
            }else{                  // 否则就是当前节点值小于要删除的节点值 则是向右查找
                node = node.right;
                isLeft = false;
            }
            if(node == null){       //如果查找到最后node为空说明该数据不存在
                return false;
            }
        }
        // while循环执行完以后得到的node结点的值一定跟要删除的值一致
        // 此时得到3个值：删除结点(node)、删除结点的父结点(parentNode)、删除结点是父结点的左子树还是父子树(isLeft)

        // 此时有4中情况：结点的左右子树都为空、只有左子树、只有右子树、左右子树全有
        if(node.left == null && node.right == null){    //node结点（要删除结点）左右子树都为空
            if(node == root){       //如果删除结点是根节点，直接将根结点指为空
                root = null;
            }else if(isLeft){       //否则就不是根结点，通过isLeft来判断是父结点的左子树还是右子树，由于删除结点没有子树用父节点直接将删除结点设置为空
                parentNode.left = null;
            }else{
                parentNode.right = null;
            }
        }else if(node.right != null){           //当删除结点只有右子树时
            if(node == root){                   //如果删除结点是根节点，直接将根结点指向自己的右子树
                root = root.right;
            }else if(isLeft){                   //当删除结点为父结点的左子树时，
                parentNode.left = node.right;   //父节点的左子树指向删除结点的右子树
                node.right = null;              //删除的结点的右子树指向空 垃圾回收
            }else{                              //当删除结点为父结点的右子树时，
                parentNode.right = node.right;  //父节点的右子树指向删除结点的右子树
                node.right = null;              //删除的结点的右子树指向空 垃圾回收
            }
        }else if(node.left != null){            //当删除结点只有左子树时
            if(node == root){                   //如果删除结点是根节点，直接将根结点指向自己的左子树
                root = root.left;
            }else if(isLeft){                   //当删除结点为父结点的左子树时
                parentNode.left = node.left;    //父节点的左子树指向删除结点的左子树
                node.left = null;               //删除结点的左子树指向空，垃圾回收
            }else{                              //当删除的结点为父结点的右子树时
                parentNode.right = node.left;   //父结点的右子树指向删除结点的左子树
                node.left = null;               //删除结点的左子树指向空，垃圾回收
            }
        }else{                                  //当删除结点左右子树都有时
            Node successor = getSuccessor(node);//调用获取继承点方法得到继承结点，此时的继承结点是把删除结点删除掉的并且删除结点下的子节点也都是按规律分叉好的
            if(node == root){                   //当删除结点等于根节点时
                root = successor;               //将继承结点的值赋给根节点
                root.left = node.left;
                System.out.println(111111);
                System.out.println(node.left);
            }else if(isLeft){                   //当删除结点是父结点的左子树
                parentNode.left = successor;    //父结点的左子树赋值为继承结点
            }else{                              //当删除结点是父结点的右子树
                parentNode.right = successor;   //父结点的右子树赋值为继承结点
            }
            successor.left = node.left;         //继承结点的左子树分支赋值为删除结点的左分支
            node.left = null;
        }

        return true;
    }

    /* 找到继承结点 */
    public Node getSuccessor(Node node){
        Node successor = node;                      //继承结点
        Node successorParent = node;                //继承结点的父结点
        Node curr = node.right;                     //当前值为删除结点的右结点（下结点）
        while(curr != null){                        //循环找最小值 此时时通过循环查找curr结点左子树来找到最小值的结点
            successorParent = successor;            //将继承结点赋值给继承结点的父结点    （继承结点的父结点与继承结点同步后移）
            successor = curr;                       //将下结点赋值给继承结点             （继承结点的父结点与继承结点同步后移）
            curr = curr.left;                       //下结点指向自己的左子树，形成循环，直到下结点为空时循环结束
        }                                           //循环结束后得到继承结点，此时继承结点为删除结点下值最小的结点，下结点为空
       if(successor != node.right){                 //当继承结点与删除结点的右子树不相等时
           successorParent.left = successor.right;  //将继承结点的父结点的左子树赋值为继承结点的右子树
           successor.right = node.right;            //将继承结点的右子树指向删除结点的右子树
           System.out.println("删除结点的值："+node);
           System.out.println("继承结点的左子树："+successor.left);
           node.right = null;
       }                                            //此时继承结点(删除结点下值最小的结点)替代了删除结点的位置，此时删除结点已经被垃圾回收，然后吧继承结点返回出去即可
       return successor;                            //返回继承结点
    }


    /* 获取节点 */
    public Node getNode(int data){
        if(root == null){
            return null;
        }else{
            Node node = root;
            while(node.data != data){
                if(node.data > data){
                    node = node.left;
                }else{
                    node = node.right;
                }
                if(node == null){
                    return null;
                }
            }
            return node;
        }
    }



    /* 查询所有 */
    public void findAll(){
        Node temp = root;
        if(temp == null){
            System.out.println("null");
        }else{
            System.out.print("前序遍历：");
            root.prePrint();
            System.out.println();

            System.out.print("中序遍历：");
            root.centrePrint();
            System.out.println();

            System.out.print("后序遍历：");
            root.sufPrint();
            System.out.println();
        }
    }



    public class Node{
        private int data;
        private Node left;
        private Node right;

        public Node() {
        }

        public Node(int data) {
            this.data = data;
        }

        public Node(int data, Node left, Node reight) {
            this.data = data;
            this.left = left;
            this.right = reight;
        }
        /* 添加 */
        public void add(int data){
            // 如果要存入的数据小于等于当前的数据则存在左边否则存在右边
            if(data <= this.data){
                //左边：如果左边的下一个节点为空则直接存在左边，否则就让下一个节点递归调用此方法形成循环
                //让下个节点来判断该数据应该存在它的左边还是右边直到找到空节点的或者数据相等的节点
                if(this.left == null){
                    this.left = new Node(data);
                    size++;
                }else{
                    this.left.add(data);
                }
            }else{
                //右边：如果右边的下一个节点为空则直接存在左边，否则就让下一个节点递归调用此方法形成循环
                //让下个节点来判断该数据应该存在它的左边还是右边直到找到空节点的或者数据相等的节点
                if(this.right == null){
                    this.right = new Node(data);
                    size++;
                }else{
                    this.right.add(data);
                }
            }
        }



        /* 前序遍历 */
        public void prePrint(){
            System.out.print(this.data+" ");
            if(this.left != null){
                this.left.prePrint();
            }
            if(this.right != null){
                this.right.prePrint();
            }
        }

        /* 中序遍历 */
        public void centrePrint(){
            if(this.left != null){
                this.left.centrePrint();
            }
            System.out.print(this.data+" ");
            if(this.right != null){
                this.right.centrePrint();
            }
        }
        /* 后序遍历 */
        public void sufPrint(){
            if(this.right != null){
                this.right.sufPrint();
            }
            System.out.print(this.data+" ");
            if(this.left != null){
                this.left.sufPrint();
            }
        }
        /* 层序遍历 */
        public void tierPrint(){

        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data;
        }
    }

}
