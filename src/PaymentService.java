public class PaymentService {
    
    // 결제 처리 메서드
    public void pay(String type, int amount) {
        
        // 결제 정보를 담는 객체 생성
        Payment payment = new Payment();
        payment.type = type;
        payment.amount = amount;

        // 결제 처리 로직
        if (amount <= 0) {
            System.out.println("금액이 올바르지 않습니다.");
            return;
        }

        // 결제 방식에 따른 처리
        if (type.equals("card")) {
            System.out.println("카드 결제: " + amount);
        } else if (type.equals("kakao")) {
            System.out.println("카카오페이 결제: " + amount);
        } else {
            System.out.println("지원하지 않는 결제 방식");
        }
    }
}