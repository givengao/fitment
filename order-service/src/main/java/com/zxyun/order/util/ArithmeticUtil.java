package com.zxyun.order.util;

/**
 * @des:
 * @Author: given
 * @Date 2019/11/29 15:22
 */
public class ArithmeticUtil {

    /**
     * 在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，
     * 每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
     * @param target
     * @param array
     * @return
     */
    public static boolean Find(int target, int [][] array) {
        int lenY = array.length;
        int lenX = array[0].length;
        for (int i = 0; i < lenY; i++) {
            for (int j =0; j < lenX; j++) {
                if (array[i][j] == target) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String replaceSpace(StringBuffer str) {
        return str.toString().replaceAll(" ", "%20");
    }

//    public ArrayList<Integer> printListFromTailToHead(List<Node> listNode) {
//
//    }


    public static void main (String[] args) {
        while (true) {

        }
    }
}
