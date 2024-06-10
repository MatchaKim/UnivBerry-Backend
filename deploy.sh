#!/bin/bash

# 설정
TARGET_JAR="target/myapp-1.0.jar"     # 생성될 JAR 파일 경로
REMOTE_USER="root"                # 서버 사용자 이름
REMOTE_HOST="49.247.25.154"          # 서버 IP 주소
REMOTE_DIR="/root/"         # 서버에서 JAR 파일을 배치할 디렉토리

# 현재 디렉토리에서 프로젝트 빌드
echo "Building project..."
mvn clean package || { echo "Maven build failed"; exit 1; }

# JAR 파일 전송
echo "Transferring JAR file to server..."
scp $TARGET_JAR $REMOTE_USER@$REMOTE_HOST:$REMOTE_DIR || { echo "Failed to transfer JAR file"; exit 1; }

echo "JAR file successfully transferred to server."
