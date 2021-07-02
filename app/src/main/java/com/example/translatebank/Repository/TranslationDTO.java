package com.example.translatebank.Repository;

public class TranslationDTO {
    private String original;
    private String result;
    private String datetime;
    private String from;
    private String to;

    public TranslationDTO(String original,String result,String datetime, String from,String to) {
        setOriginal(original);
        setResult(result);
        setDatetime(datetime);
        setFrom(from);
        setTo(to);
    }

    public TranslationDTO(){}

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public  String getFrom() {
        return from;
    }

    public void setFrom(String from) { this.from = from; }

    public String getTo() {
        return to;
    }

    public void setTo(String to) { this.to = to; }
}
