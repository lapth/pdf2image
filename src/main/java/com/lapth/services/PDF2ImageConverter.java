package com.lapth.services;

import com.lapth.model.PDF2ImageOut;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class PDF2ImageConverter implements IPDF2ImageConverter {

    /**
     * Chuyển file PDF thành các file ảnh.
     * Mỗi trang PDF sẽ được chuyển thành một file ảnh, có thứ tự là -1 đến -N
     *
     * Ví dụ file PDF có 2 trang
     * Thì đầu ra là:
     * - file-1.png
     * - file-2.png
     *
     * @param luongDuLieuPDF Luồng dữ liệu của file PDF
     * @param thuMucChuaFile Thư mục chứa file ảnh
     *
     * @return Tên file ảnh và số lượng file được sinh ra.
     * @throws IOException
     */
    public PDF2ImageOut convert(InputStream luongDuLieuPDF, String thuMucChuaFile) throws IOException {
        // Tạo file name theo chuẩn UUID để đảm bảo sẽ không trùng lặp
        String tenFile = UUID.randomUUID().toString();

        // Đọc dữ liệu từ luồng dữ liệu
        PDDocument pdfFile = PDDocument.load(luongDuLieuPDF);
        PDFRenderer pdfRenderer = new PDFRenderer(pdfFile);

        // Duyệt qua toàn bộ các trang PDF
        for (int trang = 0; trang < pdfFile.getNumberOfPages(); trang++) {
            // Lấy dữ liệu ảnh
            // Bạn muốn kích thước ảnh nhỏ hơn thì tùy chỉnh dpi và kiểu ảnh lại
            BufferedImage anhPDF = pdfRenderer.renderImageWithDPI(trang, 300, ImageType.RGB);

            // Ghi ảnh ra file với hậu tố là -trang.png
            ImageIOUtil.writeImage(anhPDF, thuMucChuaFile + "/" + tenFile + "-" + (trang + 1) + ".png", 300);
        }

        // Chuẩn bị kết quả trả về
        PDF2ImageOut out = new PDF2ImageOut();
        out.setFileName(tenFile);
        out.setCount(pdfFile.getNumberOfPages());

        pdfFile.close();

        return out;
    }
}
