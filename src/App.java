public class App {
    public static void main(String[] args) {
        PaymentService service = new PaymentService();
        service.pay("card", 10000);
        service.pay("kakao", 15000);
        service.pay("bank", 20000);
    }
}