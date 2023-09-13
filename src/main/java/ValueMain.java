public class ValueMain {
    public static void main(String[] args) {
        int a = 10;
        int b = a;

        a = 20;

        System.out.println("a = " + a);
        System.out.println("b = " + b);
        // 자바의 기본 타입 값은 변하지 않음, 공유되지 않음.

        Integer aa = new Integer(10);
        Integer bb = aa;

        // set으로 값 바꾸면 값이 변경됨. 공유가 되고 있음.
    }
}
