package com.amnapp.defsdemo;

public class VocanotesInfo {
    private String headWord;
    private String relatedWord;
    private String meaning;
    private String exampleSentence;
    private String otherMemo;

    public VocanotesInfo(String headWord, String relatedWord, String meaning, String exampleSentence, String otherMemo) {
        this.headWord = headWord;
        this.relatedWord = relatedWord;
        this.meaning = meaning;
        this.exampleSentence = exampleSentence;
        this.otherMemo = otherMemo;
    }

    public void setHeadWord(String headWord) {
        this.headWord = headWord;
    }

    public void setRelatedWord(String relatedWord) {
        this.relatedWord = relatedWord;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public void setExampleSentence(String exampleSentence) {
        this.exampleSentence = exampleSentence;
    }

    public void setOtherMemo(String otherMemo) {
        this.otherMemo = otherMemo;
    }

    public String getHeadWord() {
        return headWord;
    }

    public String getRelatedWord() {
        return relatedWord;
    }

    public String getMeaning() {
        return meaning;
    }

    public String getExampleSentence() {
        return exampleSentence;
    }

    public String getOtherMemo() {
        return otherMemo;
    }
}
