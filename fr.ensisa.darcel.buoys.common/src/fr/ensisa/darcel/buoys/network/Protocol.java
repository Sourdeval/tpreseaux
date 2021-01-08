package fr.ensisa.darcel.buoys.network;

public class Protocol {

    public static final int BUOYS_UDP_PORT                 = 6666;
    public static final int BUOYS_TCP_PORT                = 7777;

    private static final int REQUEST_START                = 1000;
    private static final int REPLY_START                = 2000;

    public static final int REQUEST_DO_RECEIVE_CURRENT            = 3001;
    public static final int REPLY_DO_RECEIVE_CURRENT                = 4001;
    public static final int REQUEST_DO_SEND_NEW            = 3002;
    public static final int REPLY_DO_SEND_NEW                = 4002;
    public static final int REQUEST_DO_GET_BUOY_LIST            = 3003;
    public static final int REPLY_DO_GET_BUOY_LIST                = 4003;
    public static final int REQUEST_DO_GET_BUOY            = 3004;
    public static final int REPLY_DO_GET_BUOY            = 4004;

    public static final int REQUEST_DO_DELETE            = 3005;
    public static final int REPLY_DO_DELETE             = 4005;

    public static final int REQUEST_DO_CREATE_BUOY = 3006;
    public static final int REPLY_DO_CREATE_BUOY = 4006;

    public static final int REQUEST_DO_UPDATE_BUOY = 3007;
    public static final int REPLY_DO_UPDATE_BUOY = 4007;

    public static final int REQUEST_DO_CLEAR_DATA = 3008;
    public static final int REPLY_DO_CLEAR_DATA = 4008;
    public static final int REPLY_OK = 3000;

    public static final int REQUEST_DO_GET_BUOY_LAST_TICK= 3009;
    public static final int REPLY_DO_GET_BUOY_LAST_TICK= 4009;

    public static final int REQUEST_DO_GET_BUOY_DATA = 3010;
    public static final int REPLY_DO_GET_BUOY_DATA = 4010;

    public static final int REQUEST_DO_GET_BUOY_BUOY = 3011;
    public static final int REPLY_DO_GET_BUOY_BUOY = 4011;
    public static final int REQUEST_DO_UPDATE_VERSION = 3012;
    public static final int REPLY_DO_UPDATE_VERSION = 4012;
    public static final int REQUEST_DO_SEND_DATA = 3013;

    private static final int COMMON_START                = 000;
    private static final int BUOY_START                    = 100;
    private static final int CONFIG_START                = 200;
    private static final int CLIENT_START                = 300;
    private static final int SERVICE_START                = 400;

    public static final int REPLY_KO                    = REPLY_START+COMMON_START+2;


    public static final int UDP_STD1                    = 0x01;
    public static final int UDP_STD2                    = 0x02;
    public static final int UDP_SERVICE                    = 0x03;




}