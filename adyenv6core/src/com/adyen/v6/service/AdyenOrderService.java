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

import com.adyen.model.PaymentResult;
import com.adyen.model.checkout.PaymentsResponse;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.fraud.model.FraudReportModel;

public interface AdyenOrderService {
    void updateOrderFromPaymentsResponse(AbstractOrderModel order, PaymentsResponse paymentsResponse);

    /**
     * Updates order's metadata using the API Payment response
     * Covers fraud, avs, cc authorisation data
     */
    void updateOrderFromPaymentResult(AbstractOrderModel order, PaymentResult paymentResult);

    FraudReportModel createFraudReportFromPaymentsResponse(PaymentsResponse paymentsResponse);

    /**
     * Creates FraudReportModel from PaymentResult
     * @return FraudReportModel|null
     */
    FraudReportModel createFraudReportFromPaymentResult(PaymentResult paymentResult);

    /**
     * Store FraudReportModel
     */
    void storeFraudReport(FraudReportModel fraudReport);

    void storeFraudReportFromPaymentsResponse(AbstractOrderModel order, PaymentsResponse paymentsResponse);

    /**
     * Create FraudReportModel from PaymentResult and assigns it to order
     */
    void storeFraudReportFromPaymentResult(AbstractOrderModel order, PaymentResult paymentResult);
}
