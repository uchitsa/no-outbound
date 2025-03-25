import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class NoOutboundConnections implements TestRule {
    @Override
    public Statement apply(Statement statement, Description description) {
        return null;
    }
}
