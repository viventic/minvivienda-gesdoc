package co.com.minvivienda.gesdoc;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component("customErrorProcessor")
public class CustomErrorProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        int statusCode = exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class);
        String customErrorMessage;

        switch (statusCode) {
            case 400:
                customErrorMessage = "{\"error\": \"Bad Request - The request was invalid or cannot be otherwise served.\"}";
                break;
            case 401:
                customErrorMessage = "{\"error\": \"Unauthorized - Authentication is required and has failed or has not yet been provided.\"}";
                break;
            case 403:
                customErrorMessage = "{\"error\": \"Forbidden - The request was valid, but the server is refusing action.\"}";
                break;
            case 404:
                customErrorMessage = "{\"error\": \"Not Found - The requested resource could not be found.\"}";
                break;
            case 500:
                customErrorMessage = "{\"error\": \"Internal Server Error - An error occurred on the server.\"}";
                break;
            default:
                customErrorMessage = "{\"error\": \"An unexpected error occurred with status code " + statusCode + ".\"}";
                break;
        }

        exchange.getIn().setBody(customErrorMessage);
        exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
    }
}