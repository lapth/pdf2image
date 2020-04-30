package com.lapth.services;

import com.lapth.model.PDF2ImageOut;

import java.io.IOException;
import java.io.InputStream;

public interface IPDF2ImageConverter {

    public PDF2ImageOut convert(InputStream luongDuLieuPDF, String thuMucChuaFile) throws IOException;
}
