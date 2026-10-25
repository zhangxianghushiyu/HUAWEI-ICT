package com.ruoyi.platform.utils;

import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class Sha256Util {

    /**
     * 针对广告回调验签
     */
    public static String csjKeySign(String key, String tranId) {
        try {
            // 需要计算哈希值的字符串

            String inputString = key + ":" + tranId;

            // 创建SHA-256哈希算法的MessageDigest对象
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");

            // 使用SHA-256计算字符串的哈希值
            byte[] hash = sha256.digest(inputString.getBytes("UTF-8"));

            // 将字节转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
       return null;
    }

    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //74ccb35d6136e499b9529d5451e7dd8c5b53881aa1aa0d329b6ac458b84de865
        String mockKey1 = Sha256Util.csjKeySign("3aae56d1a957985709c8ac8b5db894f8", "cae31495-a11d-48a9-839f-a4d22e14814a");
        String mockKey2 = Sha256Util.csjKeySign("64bf42979ad060d42c373d2308ab1c0a", "cae31495-a11d-48a9-839f-a4d22e14814a");
        String mockKey3 = Sha256Util.csjKeySign("aac70f1a14fe19e4dfd75e608c3c79be", "cae31495-a11d-48a9-839f-a4d22e14814a");
        System.out.println(mockKey1);
        System.out.println(mockKey2);
        System.out.println(mockKey3);
    }
}
