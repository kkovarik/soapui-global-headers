package cz.kkovarik.soapui.header.filter;

import static org.apache.log4j.Logger.getLogger;

import java.util.Map;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.impl.support.AbstractHttpRequest;
import com.eviware.soapui.impl.wsdl.submit.RequestFilter;
import com.eviware.soapui.model.iface.Request;
import com.eviware.soapui.model.iface.Response;
import com.eviware.soapui.model.iface.SubmitContext;
import com.eviware.soapui.model.propertyexpansion.PropertyExpansionUtils;
import com.eviware.soapui.model.testsuite.TestProperty;
import com.eviware.soapui.support.types.StringToStringsMap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;


/**
 * Request filter to add headers configured in global properties.
 *
 * @author Karel Kovarik
 * @see RequestFilter
 */
public class HeaderRequestFilter implements RequestFilter {

    public static final String HEADER_PREFIX = "header-";

    private static final Logger LOG = getLogger(SoapUI.class);

    public void filterRequest(SubmitContext submitContext, Request request) {
        if (request instanceof AbstractHttpRequest) {
            LOG.info("[RequestFilter] intercepting http request");
            final AbstractHttpRequest req = (AbstractHttpRequest) request;
            final StringToStringsMap headersMap = req.getRequestHeaders();

            for(Map.Entry<String, TestProperty> propertyEntry :
                    PropertyExpansionUtils.getGlobalProperties().getProperties().entrySet()) {
                String key = propertyEntry.getKey();
                TestProperty testProperty = propertyEntry.getValue();
                if (key.startsWith(HEADER_PREFIX)) {
                    String headerName = key.replace("header-", "");
                    if (CollectionUtils.isEmpty(headersMap.get(headerName))) {
                        LOG.info("[RequestFilter] adding header : " + headerName);
                        headersMap.put(headerName, testProperty.getValue());
                    } else {
                        LOG.info("[RequestFilter] header is already present : " + headerName);
                    }
                }
            }

            req.setRequestHeaders(headersMap);
        } else {
            LOG.info("[RequestFilter] ignoring request of type " + request.getClass());
        }
    }

    public void afterRequest(SubmitContext submitContext, Request request) {
        // do nothing
    }

    public void afterRequest(SubmitContext submitContext, Response response) {
        // do nothing
    }
}
