public class Payment {
    // 결제 방식 (카드, 카카오페이 등)
    private String type;
    
    // 결제 금액
    private int amount;

    public Payment(String type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public void pay() {
        if(!isValidAmount()) { 
            System.out.println("금액이 올바르지 않습니다.");
            return;
        }

        if(isCard()){
            System.out.println("카드 결제: " + amount);        
        } else if (isKakao()){
            System.out.println("카카오페이 결제: " + amount);
        } else {
            System.out.println("지원하지 않는 결제 방식");
        }
    }

    public boolean isValidAmount() { 
        return amount > 0;
    }

    public boolean isCard() {
        return "card".equals(type);
    }

    public boolean isKakao() { 
        return "kakao".equals(type);
    }

}