# Docker image cơ sở
FROM openjdk:8-jre-alpine
# Chuyển thư mục làm việc đến đây
WORKDIR /home/lapth
# Copy file jar và sh từ máy gốc sang Docker image
# Mỗi lần build lại file jar bạn phải build lại image này
COPY pdf2image.jar .
COPY run.sh .
CMD chmod +x run.sh
# Thực hiện lệnh này khi chạy docker image
CMD sh ./run.sh