# Stage 1: Build
FROM openjdk:17-jdk-slim AS builder

# 작업 디렉토리 설정
WORKDIR /app

# Gradle Wrapper와 설정 파일 복사
COPY gradle /app/gradle
COPY gradlew /app/gradlew
COPY build.gradle /app/
COPY settings.gradle /app/

# Gradle 의존성 캐시를 먼저 준비
RUN ./gradlew build -x test --no-daemon || return 0

# 나머지 소스 코드 복사
COPY src /app/src
COPY .env /app/.env

# Gradle Wrapper를 사용하여 clean과 build 실행
RUN ./gradlew clean build -x test --no-daemon

# Stage 2: Run
FROM openjdk:17-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# 빌드 결과물 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 포트 노출
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]