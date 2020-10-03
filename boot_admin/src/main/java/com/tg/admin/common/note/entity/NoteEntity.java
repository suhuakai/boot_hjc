package com.tg.admin.common.note.entity;

import lombok.Data;

@Data
public class NoteEntity {


    String account;

    String code;

    String type;

    String mold;

     String fnGeetestChallenge;
     String fnGeetestValidate;
     String fnGeetestSeccode;


    public String getAccount() {
        return account;
    }

    public NoteEntity setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public NoteEntity setType(String type) {
        this.type = type;
        return this;
    }

    public String getFnGeetestChallenge() {
        return fnGeetestChallenge;
    }

    public void setFnGeetestChallenge(String fnGeetestChallenge) {
        this.fnGeetestChallenge = fnGeetestChallenge;
    }

    public String getFnGeetestValidate() {
        return fnGeetestValidate;
    }

    public void setFnGeetestValidate(String fnGeetestValidate) {
        this.fnGeetestValidate = fnGeetestValidate;
    }

    public String getFnGeetestSeccode() {
        return fnGeetestSeccode;
    }

    public void setFnGeetestSeccode(String fnGeetestSeccode) {
        this.fnGeetestSeccode = fnGeetestSeccode;
    }
}
