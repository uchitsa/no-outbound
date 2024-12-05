package tech.uchitsa.no_outbound;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.lang.reflect.Field;
import java.net.Socket;

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
            try {
                overrideSocketClass();
                base.evaluate();
            } finally {
                restoreSocketClass();
            }
        }

        private void overrideSocketClass() throws NoSuchFieldException {
            Class<?> socketClass = Socket.class;
            Field classLoaderField = Class.class.getDeclaredField("classLoader");

        }


        private void restoreSocketClass() {
        }
    }

}
