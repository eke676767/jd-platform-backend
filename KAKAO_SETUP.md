# 카카오 로그인 설정 가이드

## 1. 카카오 개발자 콘솔 설정

1. [카카오 개발자 콘솔](https://developers.kakao.com) 접속
2. 애플리케이션 생성 또는 선택
3. **앱 키** 메뉴에서 **REST API 키** 복사
4. **카카오 로그인** > **활성화** 설정
5. **Redirect URI** 등록: `http://localhost:8080/login/oauth2/code/kakao`
6. **동의 항목** > 이메일, 프로필(닉네임/프로필 사진) 필수 동의 설정
7. **제품 설정** > **카카오 로그인** > **Client Secret** 생성 (웹 플랫폼)

## 2. 애플리케이션 설정

`src/main/resources/application-local.yaml` 파일 생성:

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          kakao:
            client-id: YOUR_REST_API_KEY
            client-secret: YOUR_CLIENT_SECRET
```

또는 환경 변수로 설정:
- `KAKAO_CLIENT_ID`: REST API 키
- `KAKAO_CLIENT_SECRET`: Client Secret

## 3. 실행

`application-local.yaml` 생성 후:

```bash
.\run.bat
```

또는 `application.yaml`에 카카오 키를 직접 추가해도 됩니다.
