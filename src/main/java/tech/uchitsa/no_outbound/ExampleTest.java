package tech.uchitsa.no_outbound;

import org.junit.Rule;
import org.junit.Test;

public class ExampleTest {
    @Rule
    public NoOutboundConnectionsRule noOutboundConnectionsRule = new NoOutboundConnectionsRule();

    @Test
    @NoOutboundConnections
    public void testWithoutOutboundConnections() {
        //  test code
    }

}
