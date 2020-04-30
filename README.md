## Các bước thực hiện
### Build
```shell script
mvn clean package
```
### Copy `pdf2image.jar` vào thư mục `docker`
```shell script
cp target/pdf2image.jar docker/
```
### Build docker image
```shell script
cd docker
docker build . 
```
### Test docker image 
Xem readme trong thư mục release

## Chạy service ở môi trường DEV
```
mvn spring-boot:run -Dspring.config.location=path\to\config\application.properties

hoặc

java -jar target\pdf2image.jar -Dspring.config.location=path\to\config\application.properties
```

# Các API
- GET: /ping
- POST /convert
- POST /convert/file

## Bản quyền
Bạn có thể sử dụng mã nguồn ở repository này cho bất kỳ mục đích nào của mình mà không cần xin phép tác giả.