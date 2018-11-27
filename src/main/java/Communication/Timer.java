package Communication;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Timer {
    @NonNull
    private long time;

    //TODO Possibility of replacing it with asynchronous calls
    public void setStart() {
        long start = System.currentTimeMillis();
        long current = start;
        while (current - start <= time)
            current = System.currentTimeMillis();
        // TODO Do sth after time miliceconds

    }

}
