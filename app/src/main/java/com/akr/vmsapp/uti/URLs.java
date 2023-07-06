package com.akr.vmsapp.uti;

public class URLs {
    private static final String ROOT_URL = "http://192.168.240.194/vms/";

    /* adm */
    public static final String ADM_REGISTER = ROOT_URL + "?c=admin";
    public static final String ADM_LOGIN = ROOT_URL + "?r=admin";

    public static final String GET_ADMINS = ROOT_URL + "?r=admins";
    public static final String GET_MAKERS = ROOT_URL + "?r=makers";
    public static final String GET_OWNERS = ROOT_URL + "?r=owners";
    public static final String GET_VEHICLES = ROOT_URL + "?r=vehicles";
    public static final String GET_VEHICLE_TYPES = ROOT_URL + "?r=vehicle_types";

    public static final String ADD_COUNTRY = ROOT_URL + "?c=country";
    public static final String ADD_BRAND = ROOT_URL + "?c=brand";
    public static final String ADD_MODEL = ROOT_URL + "?c=model";
    public static final String ADD_VEHICLE_TYPE = ROOT_URL + "?c=vehicle_type";

    /* mec */
    public static final String MEC_REGISTER = ROOT_URL + "?c=maker";
    public static final String MEC_LOGIN = ROOT_URL + "?r=maker";

    public static final String ADD_GARAGE = ROOT_URL + "?c=garage";
    public static final String ADD_SPARE_PART = ROOT_URL + "?c=spare_part";
    public static final String ADD_SPARE_SHOP = ROOT_URL + "?c=spare_shop";

    /* own */
    public static final String OWN_REGISTER = ROOT_URL + "?c=owner";
    public static final String OWN_LOGIN = ROOT_URL + "?r=owner";

    public static final String GET_EXPENSES = ROOT_URL + "?r=expenses";
    public static final String GET_INSURANCES = ROOT_URL + "?r=insurances";

    public static final String ADD_GRG_RATING = ROOT_URL + "?c=grg_rating";
    public static final String ADD_MEC_RATING = ROOT_URL + "?c=mec_rating";
    public static final String ADD_VEHICLE = ROOT_URL + "?c=vehicle";
    public static final String ADD_EXPENSE = ROOT_URL + "?c=expense";
    public static final String ADD_INSURANCE = ROOT_URL + "?c=insurance";

    /* gen */
    public static final String GET_COUNTRIES = ROOT_URL + "?r=countries";
    public static final String GET_GARAGES = ROOT_URL + "?r=garages";
    public static final String GET_BRANDS = ROOT_URL + "?r=brands";
    public static final String GET_MODELS = ROOT_URL + "?r=models";
    public static final String GET_GRG_RATINGS = ROOT_URL + "?r=grg_ratings";
    public static final String GET_MEC_RATINGS = ROOT_URL + "?r=mec_ratings";
    public static final String GET_SPARE_PARTS = ROOT_URL + "?r=spare_parts";
    public static final String GET_SPARE_SHOPS = ROOT_URL + "?r=spare_shops";

    // Material icons are delightful, beautifully crafted
}
