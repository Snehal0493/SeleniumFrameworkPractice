package TestComponents;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

public class ExtentLog4jAppender extends AppenderSkeleton {

    @Override
    protected void append(LoggingEvent event) {
        ExtentTest test = Listener.getCurrentTest();
        if (test == null) return; // no active test yet

        Status status = map(event.getLevel());
        String msg = event.getRenderedMessage();

        if (event.getThrowableInformation() != null) {
            test.log(status, msg, event.getThrowableInformation().getThrowable(), null);
        } else {
            test.log(status, msg);
        }
    }

    private Status map(Level level) {
        if (level.isGreaterOrEqual(Level.ERROR)) return Status.FAIL;
        if (level.isGreaterOrEqual(Level.WARN))  return Status.WARNING;
        if (level.isGreaterOrEqual(Level.INFO))  return Status.INFO;
        return Status.FAIL; // default;
    }

    @Override public void close() {}
    @Override public boolean requiresLayout() { return false; }
}
