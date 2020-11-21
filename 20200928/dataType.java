public class dataType{ 
    /**
     * 布尔类型  boolean 
     * 1、在Java中，布尔类型没有明确大小
     * 2、在Java中，没有所谓的0和非0代表真假
     *    只有true false
     * @param  args [description]
     * @return      [description]
     */
    public static void main(String[] args) {
        boolean n = true;
        System.out.println(n);
    }
    /**
     * 短整型 short 2字节 
     * @param  args [description]
     * @return      [description]
     */
    public static void main7(String[] args) {
        short b = 12;
        System.out.println(b);
        System.out.println(Short.MIN_VALUE);
        System.out.println(Short.MAX_VALUE);
    }
    /**
     * 字节类型 byte 1字节 -128~127
     * @param  args [description]
     * @return      [description]
     */
    public static void main6(String[] args) {
        byte b = 12;
        byte c = 21;
        System.out.println(b+" "+c);
        System.out.println(Byte.MIN_VALUE);
        System.out.println(Byte.MAX_VALUE);
    }
    /**
     * 字符数据类型 char Character 2个字节 0-65535
     * @param  args [description]
     * @return      [description]
     */
    public static void main5(String[] args) {
        char ch = 'a';
        System.out.println(ch);

        char ch2 = '高';
        System.out.println(ch2);

        char ch3 = 97;//Unicode 字符集代码，相比ASCII包含字符更多
        System.out.println(ch3);
    }
    /**
     * 单精度浮点型 float Float 4个字节
     * @param  args [description]
     * @return      [description]
     */
    public static void main4(String[] args) {
        float f = 12.3f;
        System.out.println(f);//会报错，12.3时系统默认为double类型，直接给f赋值可能发生精度丢失。
        //体现了Java的安全性，保证了数据的精度安全，C语言只会警告，Java会禁止通过
        
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
