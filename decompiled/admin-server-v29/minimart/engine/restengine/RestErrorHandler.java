/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.http.client.ClientHttpResponse
 *  org.springframework.web.client.ResponseErrorHandler
 */
package minimart.engine.restengine;

import java.io.IOException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class RestErrorHandler
implements ResponseErrorHandler {
    public boolean hasError(ClientHttpResponse chr) throws IOException {
        return false;
    }

    public void handleError(ClientHttpResponse chr) throws IOException {
    }
}

