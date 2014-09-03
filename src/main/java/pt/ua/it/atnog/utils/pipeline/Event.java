package pt.ua.it.atnog.utils.pipeline;

public class Event {
    private boolean endOfStream;

    public Event(boolean endOfStream) {
        this.endOfStream = endOfStream;
    }

    public Event() {
        endOfStream = false;
    }

    public boolean endOfStream() {
        return endOfStream;
    }
}
