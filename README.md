# opentelemetry-test

Spring Boot 4.x(4.1.1) 기반의 **OpenTelemetry + OpenObserve** 연동 테스트 프로젝트입니다.
애플리케이션의 3대 관측성 데이터인 **Logs**, **Traces**, **Metrics**를 OTLP(OpenTelemetry Protocol)를 통해 OpenObserve로 실시간 수집·분산 추적합니다.

---

## 🚀 빠른 시작 (Quick Start)

### 1. OpenObserve 실행 (Docker Compose)

프로젝트 루트에서 docker compose로 OpenObserve를 실행합니다:

```bash
docker compose up -d
```

- **OpenObserve Web UI**: [http://localhost:5080](http://localhost:5080)
- **기본 계정**: `test@aimnext.co.kr` / `Test1234!`

---

### 2. Spring Boot 애플리케이션 실행

#### 📌 Local 환경 (기본값: OTLP 전송 비활성화)
로컬 개발 시 OpenObserve 연결 없이 가볍게 구동합니다:

```bash
./gradlew bootRun
```

#### 📌 Dev 환경 (OpenObserve OTLP 연동 활성화)
OpenObserve로 Logs, Traces, Metrics를 실시간 전송합니다:

```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

또는 환경변수로 지정:
```bash
SPRING_PROFILES_ACTIVE=dev ./gradlew bootRun
```

---

## 🧪 테스트 API 및 검증

애플리케이션이 실행된 상태에서 아래 API를 호출하여 트레이스/로그를 발생시킵니다:

```bash
# 1. 기본 인사 요청
curl "http://localhost:8080/hello?name=Alice"

# 2. 비즈니스 지연 처리 시뮬레이션
curl "http://localhost:8080/order?orderId=ORD-001&amount=55000"

# 3. 예외 및 에러 스팬 발생 시뮬레이션
curl "http://localhost:8080/error-test?code=PAYMENT_TIMEOUT"
```

---

## 📊 OpenObserve에서 확인하기

1. [http://localhost:5080](http://localhost:5080) 로그인 (`test@aimnext.co.kr` / `Test1234!`)
2. **Traces**: API 엔드포인트별 지연 시간, Waterfall 차트 및 Error Span 확인
3. **Logs**: `default` 스트림에서 실시간 로그 조회 (로그 클릭 시 연관된 `trace_id`로 분산 추적 연계)
4. **Metrics**: `http_server_requests`, `system_cpu_usage`, `jvm_*` 등 수집 지표 확인

---

## 🛠 주요 기술 스택 & 설정

- **Java**: 25
- **Kotlin**: 2.3.21
- **Spring Boot**: 4.1.1
- **Observability**:
  - `spring-boot-starter-opentelemetry`
  - `spring-boot-starter-actuator`
  - `opentelemetry-logback-appender-1.0:2.16.0-alpha`
- **Backend**: OpenObserve (OTLP HTTP)
- **Profiles**:
  - `local`: OTLP export OFF (`management.opentelemetry.enabled: false`)
  - `dev`: OTLP export ON (Traces, Logs, Metrics 전송)
