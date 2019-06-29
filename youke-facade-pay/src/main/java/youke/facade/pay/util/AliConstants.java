package youke.facade.pay.util;

import youke.common.constants.ApiCodeConstant;

/**
 * 阿里支付常类
 */
public class AliConstants {
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public final static String APP_ID = "2018020802160791";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public final static String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC5M7WLp7pzz3Ha2FU58JSe3NWXKiK7FJWKc4E/VGeyq6hoBLPU2LTe2MvUqjTvdyEzciecSn5wkN/Y+p1GYD7buL3VR7Q4YXn0ncbLvGUgkdrgDMzn9t5DwNK2b4dSQtzQwWm49Uv547VSmjQA/cxzpDp91laR9a+JwAKt8dLorZ7ODd+VaJ3Bx8TVOnoOBQuwcTsMiZuNMmAHKoOz57LyyLjobVFkrEfU2par/O5Pplnhj/aFntzxLDj/T2Se8gJ+OmEwZENaugh7Hp9Vldy6rVS2GBKdvrvqvgO+AVkSlxnxwfqVwy9p9GU5pQXUUmqs7g4f8DEFVc6ioH6UjagVAgMBAAECggEADNugMQ/2C/BNEUqPsDQZvWXLlnEgdCibsT8PrBvqDyeHy6D380EQXaBjlmIyxqVYsr+sLFenq50DTYcowCgSdSLAWzxdMMTjuK3zjVWJF9fIgGGjID+tcrrCZlQb6wQlXIDxHBw7qB+9CP0XuzHjreuq++2TmRKbm06vYPA3GlxFO/QfKvsuEB6gBIKffm2cE/W9K0CbX9UoTp/qsJ2lyO8bHiqBJr8B+Z206zA0CRcX74tVFDVtuZkUTRtnMjgi74R4GIAmz5EgiOftIPxii3s9osHG+x1sJkVv+ZIqjQGaDHZhx2+0D3W0kWWMsxYQ83+HVHUM6I1SdPEN8vNpAQKBgQDbajAOZ+wKEeo+az52Zw169oEEq01oN72yPYTcjUIEua7dH0ibQ9s6bSTyf1pACEYLGpZog/3XHMP0sKA7LFZF9Pudn6p/wsP3wHzNwPC5E95t4oSS1pk6Q3jhVfLhKSgheHH9RygJHqdz0gI/FK+w74K38KBDvomj9YpyjpkgtQKBgQDYFSIrs0MhD5ZmZG89/q0n/Mo6JmVZrlKTPlNSRnk1rGQ8fN77mYy1JgLq514x+cgAFaw2mSzJHqFS7Oob9zQIn8i+jz+14Y7CaHYLJLUlAmrqFwNdvpJcpisDoTZop5iosDOedOT+9OSEFCJDuxdqGFCCLxDVqwvpu6Exfp7l4QKBgAIyPejnAWXYaA/X4vgsb+YbMY9qZ4vzguKV2w6aG18QvL9Z4NAtDbS4Avaqb6zUaF8UFQ1WxOOyVgPNAM2W1AY/PYWGY56DxJJmStmEZOR1XnSbH9iYbVA3/K3Evnmif9RV8E4ouC3fMK3oHD2lZ1zOCPDuZ6xF6/rIKmqbIs1BAoGBAJBAc4Jm9u6QQGB0tgrWBPTH/p0ZWtmm1Xt0xR0M7QgW4Qe8eQPP+y5HhPUapjiIvqrHzSLZ2RsetqXsgVFQE02aleIZMXgTb0F7iSphRAjD92n1GQsxGPZ+/AI9UlxFXlVm497pvKI0DIXcBw/eS4BJYR23m+54dMiBuNeY5y0hAoGAXHUGZNiWBfUu+E7xHsvDj6PJBX7WPESE6WY190V2QzvNwBWzgSfxhexCuPFVRtHXFClG40CccK1YJ2zIamwU3jpr8c1FNsw4+BcmAqUZvmyX02iPZdRWHDYrAoCBDP48m0IsYvgxLRS1HetyeGJpyRW5imbdnQaZzMNbey/m1to=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public final static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA5hv+LfL757Xj/2g+KoU2fir145mAyakdsBmkCksnVgK95d7GUevghnC9X6v896rMRyporU3NpRZZZxfc6o342v7DicbVXcHkSY3jyGBqbTkO1m3j5igtl6Z0Nyqhx9hQw5c3ohBj/YkvTLxStg7F8eL2IlAcgL7f5OYZn0L4aGQALXyUspH59MOomOvlbXF/F/WSobfdlHA2hu/rr8V2jMQMfEcf11sDAE1wT6EO07BMFUGNNun4/ZkF3yd+O8hX/OlwQEOVCMpMBcALGd3zHyeybyCj9vwkLFTtRyO0MgayOtM/4lnl20z7oeB8B8Mti0ggP4HEWVTyBidwhIdkHwIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public final static String notify_url = ApiCodeConstant.DOMAIN_PCAPI+"pay/alipayback";

    // 签名方式
    public final static String SIGN_TYPE = "RSA2";

    // 字符编码格式
    public final static String CHARSET = "utf-8";

    // 支付宝网关
    public final static String gatewayUrl = "https://openapi.alipay.com/gateway.do";

}
