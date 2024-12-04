package tech.uchitsa.no_outbound;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class NoOutboundConnectionsRule implements TestRule {
    public Statement apply(Statement base, Description desc) {
        NoOutboundConnections annotation = desc.getAnnotation(NoOutboundConnections.class);
        if (annotation != null) {
            return new NoOutboundConnectionsStatement(base);
        }
        return base;
    }

    private class NoOutboundConnectionsStatement extends Statement {
        private final Statement base;

        public NoOutboundConnectionsStatement(Statement base) {
            this.base = base;
        }

        public void evaluate() throws Throwable {
            //TODO
        }
    }

}
