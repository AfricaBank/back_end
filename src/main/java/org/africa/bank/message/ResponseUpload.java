package org.africa.bank.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseUpload {
    private String fileId;
    private String fileName;
    private String message;
}
