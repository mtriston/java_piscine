package edu.school21.printer;

public class RendererStandardImpl implements Renderer {

    private final PreProcessor preProcessor;

    public RendererStandardImpl(PreProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }
    @Override
    public void render(String message) {
        System.out.print(preProcessor.preProcess(message));
    }
}
