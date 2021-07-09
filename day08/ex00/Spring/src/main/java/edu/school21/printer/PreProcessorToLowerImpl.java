package edu.school21.printer;

public class PreProcessorToLowerImpl implements PreProcessor {
    @Override
    public String preProcess(String str) {
        return str.toLowerCase();
    }
}
