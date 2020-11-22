import java.util.*;

public class practice{
    /**
     * 输出 1000 - 2000 之间所有的闰年
     * @param  args [description]
     * @return      [description]
     */
    public static void main(String[] args) {
        int i;
        for (i = 1000; i <= 2000; i++)
        {
            if (i % 100 != 0) 
            {
                if (i % 4 == 0){//根据闰年定义，首先不能被100整除的普通年，如果能被4整除则为闰年
                    System.out.println(i);
                }
            }else if (i % 400 == 0){//如果是能被100整除的世纪年，若能被400整除，则为闰年
                System.out.println(i);
            }
        }
    }
    /**
     * 打印 1 - 100 之间所有的素数
     * @param  args [description]
     * @return      [description]
     */
    public static void main3(String[] args) {
        int i = 0;
        int j = 0;
        for (i = 1;i <= 100;i++) {
            for (j = 2;j < i;j++) {
                if(i % j == 0){
                    break;
                }
            }
            if (j >= i) {
                System.out.println(i);
            }
        }
    }
    /**
     * 判定一个数字是否是素数
     * @param  args [description]
     * @return      [description]
     */
    public static void main2(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();

        int i = 2;
        for(;i< num;i++){
            if(num % i == 0){
                break;
            }
        }
        if(i >= num){
            System.out.println(true);
        }else{
            System.out.println(false);
        }
    }

    /**
     * 根据年龄, 来打印出当前年龄的人是少年(低于18), 青年(19-28), 中年(29-55), 老年(56以上)
     * @param  args [description]
     * @return      [description]
     */
    public static void main1(String[] args) {
        Scanner sc = new Scanner(System.in);
        int age = sc.nextInt();

        if(age <= 18){
            System.out.println("少年");
        }else if(age>18 && age<29){
            System.out.println("青年");
        }else if(age>=29 && age<=55){
            System.out.println("中年");
        }else if(age>55 && age <= 120){
            System.out.println("老年");
        }else{
            System.out.println("erro!");
        }
    }
}