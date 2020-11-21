public class dataType{    
    /**
     * 单精度浮点型 float Float 4个字节
     * @param  args [description]
     * @return      [description]
     */
    public static void main(String[] args) {
        float f = 12.3f;
        System.out.println(f);//会报错，12.3时系统默认为double类型，直接给f赋值可能发生精度丢失。

    }
    /**
     * 双精度浮点型 double Double   8个字节
     * @param  args [description]
     * @return      [description]
     */
    public static void main3(String[] args) {
        double n = 9.5;
        System.out.println(n);
        System.out.println(Double.MIN_VALUE);
        System.out.println(Double.MAX_VALUE);

        int a=1;
        int b=2;
        System.out.println(a /b);

        double num = 1.1;
        System.out.println(num * num);

    }
    /**
     * 长整型   long  Long  8字节  -2^63~(2^63-1) 
     * @param  args [description]
     * @return      [description]
     */
    public static void main2(String[] args) {
        long a = 10L;//10被系统默认为整型，虽然短类型可以给长类型赋值，但用对应类型相互赋值更保证安全性
        System.out.println(a);
        System.out.println(Long.MAX_VALUE);
        System.out.println(Long.MIN_VALUE);
    }
    /**
     * 整型   int  Integer  4字节  -2^31~(2^31-1) 
     * 
     * @param  args [description]
     * @return      [description]
     */
    public static void main1(String[] args) {
        int a = 10;
        System.out.println(a);
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MIN_VALUE);
        System.out.println(Integer.MAX_VALUE+1);
        System.out.println(Integer.MIN_VALUE-1);
    }
}
