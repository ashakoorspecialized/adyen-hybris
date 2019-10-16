/*
 *                        ######
 *                        ######
 *  ############    ####( ######  #####. ######  ############   ############
 *  #############  #####( ######  #####. ######  #############  #############
 *         ######  #####( ######  #####. ######  #####  ######  #####  ######
 *  ###### ######  #####( ######  #####. ######  #####  #####   #####  ######
 *  ###### ######  #####( ######  #####. ######  #####          #####  ######
 *  #############  #############  #############  #############  #####  ######
 *   ############   ############  #############   ############  #####  ######
 *                                       ######
 *                                #############
 *                                ############
 *
 *  Adyen Hybris Extension
 *
 *  Copyright (c) 2017 Adyen B.V.
 *  This file is open source and available under the MIT license.
 *  See the LICENSE file for more info.
 */
package com.adyen.v6.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.SignatureException;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.adyen.httpclient.HTTPClientException;
import com.adyen.model.PaymentResult;
import com.adyen.model.checkout.PaymentMethod;
import com.adyen.model.checkout.PaymentsResponse;
import com.adyen.model.modification.ModificationResult;
import com.adyen.model.recurring.RecurringDetail;
import com.adyen.model.terminal.ConnectedTerminalsResponse;
import com.adyen.model.terminal.TerminalAPIResponse;
import com.adyen.service.exception.ApiException;
import com.adyen.v6.model.RequestInfo;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.core.model.user.CustomerModel;

public interface AdyenPaymentService {
    /**
     * Performs authorization request via Adyen API
     */
    PaymentResult authorise(CartData cartData, HttpServletRequest request, CustomerModel customerModel) throws Exception;

    ConnectedTerminalsResponse getConnectedTerminals() throws IOException, ApiException ;

    PaymentsResponse authorisePayment(CartData cartData, RequestInfo requestInfo, CustomerModel customerModel) throws Exception;

    /**
     * Performs 3D secure authorization request via Adyen API
     */
    PaymentResult authorise3D(HttpServletRequest request, String paRes, String md) throws Exception;

    PaymentsResponse authorise3DPayment(String paymentData, String paRes, String md) throws Exception;
    PaymentsResponse authorise3DS2Payment(String paymentData, String token, String type) throws Exception;

    /**
     * Performs Capture request via Adyen API
     */
    ModificationResult capture(BigDecimal amount, Currency currency, String authReference, String merchantReference) throws Exception;

    /**
     * Performs cancelOrRefund request via Adyen API
     */
    ModificationResult cancelOrRefund(String authReference, String merchantReference) throws Exception;

    /**
     * Performs refund request via Adyen API
     */
    ModificationResult refund(BigDecimal amount, Currency currency, String authReference, String merchantReference) throws Exception;

    /**
     * Get payment methods using /paymentMethods - Checkout API
     */
    List<PaymentMethod> getPaymentMethods(BigDecimal amount, String currency, String countryCode, String shopperLocale, String shopperReference) throws IOException, ApiException;

    /**
     * @deprecated use getPaymentMethods including shopperReference instead {@link #getPaymentMethods(BigDecimal amount, String currency, String countryCode, String shopperLocale, String shopperReference)
     */
    @Deprecated
    List<com.adyen.model.hpp.PaymentMethod> getPaymentMethods(BigDecimal amount, String currency, String countryCode, String shopperLocale) throws HTTPClientException, SignatureException, IOException;

    /**
     * Retrieve stored cards from recurring contracts via Adyen API
     */
    List<RecurringDetail> getStoredCards(String customerId) throws IOException, ApiException;

    /**
     * Disables a recurring contract via Adyen API
     */
    boolean disableStoredCard(String customerId, String recurringReference) throws IOException, ApiException;

    /**
     * Retrieves payment response from /payments/details for redirect methods like klarna
     */
    PaymentsResponse getPaymentDetailsFromPayload( HashMap<String, String> details, String paymentData) throws Exception;

    /**
     * Retrieves payment response from /payments/details
     */
    PaymentsResponse getPaymentDetailsFromPayload( HashMap<String, String> details) throws Exception;

    /**
     * Retrieves origin key from /originKeys endpoint
     */

    String getOriginKey(String originDomain) throws ApiException, IOException;

    /**
     * Returns the HPP base URL for the current basestore
     */
    String getHppEndpoint();

    /**
     * Returns the Device Fingerprint url
     */
    String getDeviceFingerprintUrl();

    /**
     * Send POS Payment Request using Adyen Terminal API
     */
    TerminalAPIResponse sendSyncPosPaymentRequest(CartData cartData, CustomerModel customer, String serviceId) throws Exception;
    /**
     * Send POS Status Request using Adyen Terminal API
     */
    TerminalAPIResponse sendSyncPosStatusRequest(CartData cartData, String serviceId) throws Exception;
}
