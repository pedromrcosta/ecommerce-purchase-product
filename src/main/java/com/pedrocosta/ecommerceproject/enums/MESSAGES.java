package com.pedrocosta.ecommerceproject.enums;

public class MESSAGES {

    public static final String NAME_NOT_EMPTY = "Please enter a name.";
    public static final String CODE_BIGGER_TWO = "Code needs to have at least 2 characters.";

    public static final String PURCHASE_NOT_FOUND = "No purchase with given id found";

    /**
     * Messages that will appear on the Purchase model on 'observations'
     * */
    public static final String DELIVERY_NORMAL = "Dentro de prazo normal";
    public static final String DELIVERY_NORMAL_WEEKEND = "Dentro de prazo normal, fim-de-semana";
    public static final String DELIVERY_WEEKEND = "Fim-de-semana";
    public static final String DELIVERY_HEAVY_RAIN = " chuvas fortes";
    public static final String DELIVERY_STATUS_HAS_DELIVERY_IN = " tem a previsão de entrega em: ";

    public static String getDeliveryDelay(int daysOfDelay, int probabilityOfRain) {
        return " com atraso de " + daysOfDelay + " dias devido a " + probabilityOfRain + "% chance de " + DELIVERY_HEAVY_RAIN;
    }

}
