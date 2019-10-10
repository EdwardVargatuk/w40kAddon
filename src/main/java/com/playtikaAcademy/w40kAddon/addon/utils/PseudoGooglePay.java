package com.playtikaAcademy.w40kAddon.addon.utils;

import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

/**
 * Class is simple simulation of payment service like Google Pay
 * 04.10.2019 23:30
 *
 * @author Edward
 */
public class PseudoGooglePay {

    /**
     * A constant integer you define to track a request for payment data activity
     */
    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 42;

    /**
     * Create a Google Pay API base request object with properties used in all requests
     */
    private JSONObject getBaseRequest() throws JSONException {
        return new JSONObject()
                .put("apiVersion", 2)
                .put("apiVersionMinor", 0);
    }

    /**
     * Card networks supported by app and gateway
     */
    private JSONArray getAllowedCardNetworks() {
        return new JSONArray()
                .put("MASTERCARD")
                .put("VISA");
    }

    /**
     * Card authentication methods supported by app and your
     */
    private JSONArray getAllowedCardAuthMethods() {
        return new JSONArray()
                .put("PAN_ONLY")
                .put("CRYPTOGRAM_3DS");
    }

    /**
     * Describe app's support for the CARD payment method
     */
    private JSONObject getBaseCardPaymentMethod() throws JSONException {
        JSONObject cardPaymentMethod = new JSONObject();
        cardPaymentMethod.put("type", "CARD");
        cardPaymentMethod.put(
                "parameters",
                new JSONObject()
                        .put("allowedAuthMethods", getAllowedCardAuthMethods())
                        .put("allowedCardNetworks", getAllowedCardNetworks()));

        return cardPaymentMethod;
    }

    /**
     * Provide Google Pay API with a payment amount, currency, and amount status
     */
    private JSONObject getTransactionInfo(double price) throws JSONException {
        JSONObject transactionInfo = new JSONObject();
        transactionInfo.put("totalPrice", price);
        transactionInfo.put("totalPriceStatus", "FINAL");
        transactionInfo.put("currencyCode", "USD");

        return transactionInfo;
    }

    /**
     * Information about the merchant requesting payment information
     */
    private JSONObject getMerchantInfo() throws JSONException {
        return new JSONObject()
                .put("merchantName", "W40k addon's Shop");
    }

    /**
     * An object describing accepted forms of payment by app, used to determine a viewer's
     * readiness to pay
     */
    public Optional<JSONObject> getIsReadyToPayRequest() {
        try {
            JSONObject isReadyToPayRequest = getBaseRequest();
            isReadyToPayRequest.put(
                    "allowedPaymentMethods", new JSONArray().put(getBaseCardPaymentMethod()));
            return Optional.of(isReadyToPayRequest);
        } catch (JSONException e) {
            return Optional.empty();
        }
    }

    /**
     * An object describing information requested in a Google Pay payment sheet
     */
    private Optional<JSONObject> getPaymentDataRequest(double price) {
        try {
            JSONObject paymentDataRequest = getBaseRequest();
            paymentDataRequest.put("transactionInfo", getTransactionInfo(price));
            paymentDataRequest.put("merchantInfo", getMerchantInfo());
            return Optional.of(paymentDataRequest);
        } catch (JSONException e) {
            return Optional.empty();
        }
    }

    /**
     * Display the Google Pay payment sheet after client's interaction with the Google Pay form
     */
    public JSONObject requestPayment(double price) throws JSONException {
        Optional<JSONObject> paymentDataRequestJson = getPaymentDataRequest(price);

        if (!paymentDataRequestJson.isPresent()) {
            return new JSONObject().put("error", "paymentDataRequestJson is empty");
        }
        JSONObject jsonObject = paymentDataRequestJson.get();
        jsonObject.put("resultStatus", "OK");
        jsonObject.put("requestCode", LOAD_PAYMENT_DATA_REQUEST_CODE);
        return jsonObject;
    }

    /**
     * It have dummy and not real payment validation
     *
     * @param data need to be validate
     * @return JSONObject with any 'result'
     */
    public JSONObject validateRequestResult(int requestCode, JSONObject data) throws JSONException {
        String resultStatus = String.valueOf(data.get("resultStatus"));
        if (requestCode == LOAD_PAYMENT_DATA_REQUEST_CODE && !StringUtils.isEmpty(resultStatus)) {
            switch (resultStatus) {
                case "OK":
                    double userBalance = (double) data.get("userBalance");
                    JSONObject transactionInfo = (JSONObject) data.get("transactionInfo");
                    double totalPrice = (double) transactionInfo.get("totalPrice");
                    //in real situation we don't need to do check like this because googlePay do it for us
                    if (userBalance > totalPrice) {
                        return new JSONObject().put("result", "OK");
                    } else return new JSONObject().put("result", "There is no enough money on your account");
                case "CANCELED":
                    return new JSONObject().put("result", "Operation Cancelled");
                case "ERROR":
                    return new JSONObject().put("result", "Error:" + (data).get("error"));
                default:
                    // Do nothing.
            }
        }
        return new JSONObject().put("result", "There is problem during getting request data");
    }
}

