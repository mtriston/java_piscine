package edu.school21.printer;

public class PreProcessorToUpperImpl implements PreProcessor {
    @Override
    public String preProcess(String str) {
        return str.toUpperCase();
    }
}
