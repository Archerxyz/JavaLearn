package LeetCode;

//  从点 (x, y) 可以转换到 (x, x+y)  或者 (x+y, y)。
//
//  给定一个起点 (sx, sy) 和一个终点 (tx, ty)，如果通过一系列的转换可以从起点到达终点，则返回 True ，否则返回 False。

import javafx.util.Pair;

import java.util.List;

public class Solution_Desination {

    List<Pair> xyList;

    public Solution_Desination() {
    }

    private static boolean reachingPoints(int sx, int sy, int tx, int ty) {
        return fromBehind(sx, sy, tx, ty);
    }

    // 方法一：遍历，很蠢
//    private static boolean creatPair(int x, int y, int tx, int ty) {
//        if (x == tx && y == ty) {
//            return true;
//        } else if (x > tx || y > ty) {
//            return false;
//        }
//
//       return (creatPair(x + y, y, tx, ty) || creatPair(x, x + y, tx, ty));
//    }


    // 方法二，有那味儿了，但是不能递归
//    private static boolean fromBehind(int x, int y, int tx, int ty) {
//        if (x == tx && y == ty) {
//            return true;
//        }
//
//        if (tx > ty) {
//            int tempTx = tx - ty;
//            return fromBehind(x, y, tempTx, ty);
//        } else {
//            return fromBehind(x, y, tx, ty - tx);
//        }
//    }

    // 对了，但是终止条件如何写呢？
    private static boolean fromBehind(int sx, int sy, int tx, int ty) {
        while (tx >= sx && ty >= sy) {
            if (tx == ty) break;
            if (tx > ty) {
                if (ty > sy) tx %= ty;
                else return (tx - sx) % ty == 0;
            } else {
                if (tx > sx) ty %= tx;
                else return (ty - sy) % tx == 0;
            }
        }

        return (tx == sx && ty == sy);
    }

    public static void main(String[] args) {
        System.out.println(reachingPoints(6, 5, 11, 16));
        System.out.println(reachingPoints(35, 13, 455955547, 420098884));
        System.out.println(reachingPoints(1, 16, 999999985, 16));
        System.out.println(reachingPoints(1, 10, 999999985, 10));
        System.out.println(reachingPoints(10, 5, 15, 5));
          System.out.println(reachingPoints(3, 3, 12, 9));
    }
}
