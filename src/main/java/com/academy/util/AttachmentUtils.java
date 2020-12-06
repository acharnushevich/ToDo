package com.academy.util;

import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class AttachmentUtils {
    public byte[] toByte(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int reads = is.read();
        while(reads != -1){
            baos.write(reads);
            reads = is.read();
        }
        return baos.toByteArray();
    }
}
