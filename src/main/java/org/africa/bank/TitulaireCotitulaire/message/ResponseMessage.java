package org.africa.bank.TitulaireCotitulaire.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseMessage {

    private String message;

    public ResponseMessage(String message) {
        this.message = message;
    }

}
