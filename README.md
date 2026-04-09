# 📌 3주차 객체지향 & DI 과제

---

## 1️⃣ 문제 상황 분석

현재 구조에서는 서비스 내부에서 `EmailSender`를 직접 생성해서 사용하고 있다.  
이 구조의 가장 큰 문제는 **구현체에 직접 의존하고 있다는 점**이다.

즉, 서비스는 “알림을 보낸다”라는 기능만 필요하지만  
실제로는 `EmailSender`라는 **구체 클래스에 강하게 결합되어 있는 상태**이다.

### ❌ 문제점
- 구현체에 직접 의존 → 유연성이 없음
- 새로운 알림 방식 추가 시 기존 코드 수정 필요
- 테스트 시 특정 구현체에 종속됨
- 유지보수 어려움 증가

---

### 📌 기능 확장 시 문제

SMS, 푸시 알림 등이 추가될 경우 다음과 같은 코드가 발생할 수 있다:

    if(type.equals("email")) {
        sender = new EmailSender();
    } else if(type.equals("sms")) {
        sender = new SmsSender();
    }

👉 문제점:
- 조건문 증가
- 코드 복잡도 증가
- 기능 추가 시 기존 코드 수정 필요

➡️ 이는 **OCP(Open-Closed Principle)**를 위반하는 구조이다.

---

## 2️⃣ 인터페이스 도입 이유

알림 기능에 인터페이스를 도입하면 구현체와의 결합을 줄일 수 있다.

    interface NotificationSender {
        void send();
    }

각 알림 방식은 이를 구현한다:

    class EmailSender implements NotificationSender {}
    class SmsSender implements NotificationSender {}

---

### ✅ 장점
- 구현체 교체가 쉬움
- 코드 확장성 증가
- 테스트 용이 (Mock 사용 가능)
- 유지보수 편리

---

### ❗ 인터페이스만으로는 부족한 이유

    NotificationSender sender = new EmailSender();

👉 여전히 서비스 내부에서 구현체를 선택하고 있음

즉,
- 의존 대상은 인터페이스지만  
- 생성 책임은 여전히 서비스에 있음  

➡️ **완전한 의존성 분리가 아님**

---

## 3️⃣ DIP & DI

### ✅ DIP (의존성 역전 원칙)

> “고수준 모듈은 저수준 모듈에 의존하면 안 되고  
둘 다 추상화에 의존해야 한다.”

👉 기존 구조  
- Service → EmailSender (구현체 의존)

👉 개선 구조  
- Service → NotificationSender (인터페이스 의존)

---

### ✅ DI (의존성 주입)

DI는 객체를 직접 생성하지 않고  
👉 **외부에서 주입받는 방식**

    class NotificationService {
        private final NotificationSender sender;

        public NotificationService(NotificationSender sender) {
            this.sender = sender;
        }
    }

---

### 🔥 직접 생성 vs DI

| 방식 | 특징 |
|------|------|
| 직접 생성 | new EmailSender() → 강한 결합 |
| DI 방식 | 외부 주입 → 느슨한 결합 |

👉 DI의 장점:
- 구현체 교체 쉬움
- 테스트 용이
- 확장성 증가

---

## 4️⃣ 수동 DI 설계

### ✅ 필요한 구성 요소

1. NotificationSender (인터페이스)  
2. EmailSender / SmsSender (구현체)  
3. NotificationService (서비스)  
4. AppConfig (설정 클래스)  

---

### ✅ 역할 정리

- 인터페이스 → 기능 정의  
- 구현체 → 실제 기능 수행  
- 서비스 → 비즈니스 로직 처리  
- AppConfig → 객체 생성 및 의존성 연결  

---

### ✅ 객체 생성 위치

객체 생성과 의존성 연결은  
👉 반드시 **외부(AppConfig)**에서 수행해야 한다.

    class AppConfig {
        public NotificationService notificationService() {
            return new NotificationService(new EmailSender());
        }
    }

👉 이렇게 하면
- 서비스는 구현체를 알 필요 없음
- 구조가 유연해짐

---

## 5️⃣ Spring DI의 필요성

### ❌ 수동 DI의 한계

- 객체 생성 코드 증가  
- 설정 관리 복잡  
- 의존성 관계가 많아질수록 유지보수 어려움  
- 싱글톤 관리 직접 필요  

---

### ✅ Spring 사용 시 장점

- 객체 생성 자동화  
- 의존성 자동 주입  
- 싱글톤 자동 관리  
- 설정 간소화  

---

### ✅ 주요 어노테이션

- @Configuration  
  → 설정 클래스 지정  

- @Bean  
  → 객체를 Spring 컨테이너에 등록  

- @ComponentScan  
  → 자동으로 컴포넌트 탐색 및 등록  

---

## 6️⃣ 느낀 점

이번 과제를 통해 단순한 객체지향 개념이 아니라  
👉 **왜 이런 구조를 사용하는지** 이해할 수 있었다.

특히  
- 인터페이스만으로는 부족하고  
- DI까지 적용해야 진짜 유연한 구조가 된다는 점이 중요했다.

또한 Spring은 단순한 프레임워크가 아니라  
👉 **객체 생성과 의존성 관리 문제를 해결해주는 도구**라는 것을 알게 되었다.

앞으로는 기능 구현뿐만 아니라  
👉 **변경에 강한 설계**를 항상 고민해야겠다고 느꼈다.
