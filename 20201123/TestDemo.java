public class TestDemo{
    /**
     * 写代码实现: 给定三个 int 变量, 求其中的最大值和最小值.
     * @param  args [description]
     * @return      [description]
     */
    public static void main(String[] args) {
        int a = 10;
        int b = 24;
        int c = 21;


        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        if (a > max) {
            max = a;
        }
        if (a < min) {
            min = a;
        }

        if (b > max) {
            max = b;
        }
        if (b < min) {
            min = b;
        }

        if (c > max) {
            max = c;
        }
        if (c < min) {
            min = c;
        }
        System.out.println("max = "+max);
        System.out.println("min = "+min);
    }
    /**
     * 写代码实现: 给定两个 int 变量, 交换变量的值.
     * @param  args [description]
     * @return      [description]
     */
    public static void main1(String[] args) {
        int a = 10;
        int b = 20;
        int temp = 0;

        temp = a;
        a = b;
        b = temp;

        System.out.println(a + "  " +b);
    }
}