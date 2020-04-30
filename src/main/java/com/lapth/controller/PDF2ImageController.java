package com.lapth.controller;

import com.lapth.model.PDF2ImageOut;
import com.lapth.services.IPDF2ImageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.InputStream;

@RestController
public class PDF2ImageController {

    /**
     * Thư mục chứa dữ liệu file ảnh được sinh ra
     * Thư mục này được map từ file cấu hình application.properties
     */
    @Value("${converted.images.dir}")
    private String thuMucChuaFile;

    /**
     * Inject pdf2image service vào để dùng
     */
    @Autowired
    private IPDF2ImageConverter pdf2ImageService;

    /**
     * Ping là hàm khá đặc biệt, thường dùng để kiểm tra trạng thái của service xem còn hoạt động không
     * nó thường được dùng trong việc thiết kế các hệ thống tự động scale
     * @return
     */
    @GetMapping(path = "/ping")
    public String ping() {
        return "Hello, you are here!";
    }

    /**
     * Mình sẽ public API /convert, dữ liệu đầu vào là 1 luồng dữ liệu, với cách cài đặt này yêu cầu phía
     * Client phải đọc file dữ liệu, đẩy dữ liệu vào body và post lên cho API
     *
     * @param luongDuLieuPDF Luồng dữ liệu
     * @return JSON chứa tên file và số file được sinh ra
     * @throws Exception
     */
    @PostMapping("/convert")
    public ResponseEntity<PDF2ImageOut> convertPdf2Image(InputStream luongDuLieuPDF) throws Exception {
        try {
            PDF2ImageOut out = pdf2ImageService.convert(luongDuLieuPDF, thuMucChuaFile);
            return ResponseEntity.ok(out);
        } catch (Exception ex) {
            // Controller là điểm giao tiếp với các ứng dụng khác, nên bạn ưu tiên bắt toàn bộ các
            // lỗi có thể xảy ra của ứng dụng mình nhé, bạn có thể xử lý nó hoặc đơn giản là trả về một thông báo
            // lỗi nào đó thay vì trả toàn bộ dấu vết về phía Client. Đây là một best practice.
            return ResponseEntity.badRequest().body(new PDF2ImageOut());
        }
    }

    /**
     * API /convert/file sẽ đọc dữ liệu từ file thay vì từ stream
     * Như đã đề cập, service sẽ chạy trong Docker container nên bạn chú ý điểm này nhé.
     *
     * @param filePDF Đường dẫn file PDF
     * @return JSON chứa tên file và số file được sinh ra
     * @throws Exception
     */
    @PostMapping("/convert/file")
    public ResponseEntity<PDF2ImageOut> convertPdf2Image(String filePDF) throws Exception {
        try {
            InputStream dataStream = new FileInputStream(filePDF);
            PDF2ImageOut out = pdf2ImageService.convert(dataStream, thuMucChuaFile);
            return ResponseEntity.ok(out);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new PDF2ImageOut());
        }
    }
}
