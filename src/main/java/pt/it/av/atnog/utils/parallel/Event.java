package pt.it.av.atnog.utils.parallel;

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
