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

    private static class NoOutboundConnectionsStatement extends Statement {
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

        private void overrideSocketClass() throws NoSuchFieldException, IllegalAccessException {
            Class<?> socketClass = Socket.class;
            Field classLoaderField = Class.class.getDeclaredField("classLoader");
            classLoaderField.setAccessible(true);
            classLoaderField.set(socketClass, new ClassLoader() {
                @Override
                protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
                    if (name.equals("java.net.Socket")) {
                        throw new ClassNotFoundException("Outbound connections are prohibited by @NoOutboundConnections");
                    }
                    return super.loadClass(name, resolve);
                }
            });
        }


        private void restoreSocketClass() throws NoSuchFieldException, IllegalAccessException {
            Class<?> socketClass = Socket.class;
            Field classLoaderField = Class.class.getDeclaredField("classLoader");
            classLoaderField.setAccessible(true);
            classLoaderField.set(socketClass, null);
        }
    }

}
