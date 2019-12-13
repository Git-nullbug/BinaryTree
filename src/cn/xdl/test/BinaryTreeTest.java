package cn.xdl.test;

import cn.xdl.binarytree.BinaryTree;

public class BinaryTreeTest {
    public static void main(String[] args) {
        BinaryTree bt = new BinaryTree();
        bt.add(60);
        bt.add(100);
        bt.add(80);
        bt.add(150);
        bt.add(130);
        bt.add(140);
        bt.add(120);
        bt.add(110);
        bt.add(87);
        bt.add(9);
        bt.add(56);
        /*for(int i = 5;i<100;i+=5){
            bt.add(i);
        }*/
        bt.findAll();
        int size = bt.size();
        System.out.println("size："+size);
        //BinaryTree.Node node = bt.getNode(23);
        //System.out.println(node);
        boolean remove = bt.remove(150);
        System.out.println(remove);
        bt.findAll();
        //System.out.println(node);
    }
}
