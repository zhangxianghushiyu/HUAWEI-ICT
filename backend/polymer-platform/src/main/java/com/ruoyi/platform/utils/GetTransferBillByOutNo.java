package com.ruoyi.platform.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 商户单号查询转账单
 */
public class GetTransferBillByOutNo {
  private static String HOST = "https://api.mch.weixin.qq.com";
  private static String METHOD = "GET";
  private static String PATH = "/v3/fund-app/mch-transfer/transfer-bills/out-bill-no/{out_bill_no}";

  public void  testQueryInfo(GetTransferBillByOutNo client) {
    GetTransferBillByOutNoRequest request = new GetTransferBillByOutNoRequest();
    request.outBillNo = "1945753698391031808";
    try {
      TransferBillEntity response = client.run(request);

      // TODO: 请求成功，继续业务逻辑

      System.out.println(response);
    } catch (WXPayUtility.ApiException e) {
      // TODO: 请求失败，根据状态码执行不同的逻辑
      e.printStackTrace();
    }
  }

  public TransferBillEntity run(GetTransferBillByOutNoRequest request) {
    String uri = PATH;
    uri = uri.replace("{out_bill_no}", WXPayUtility.urlEncode(request.outBillNo));

    Request.Builder reqBuilder = new Request.Builder().url(HOST + uri);
    reqBuilder.addHeader("Accept", "application/json");
    reqBuilder.addHeader("Wechatpay-Serial", wechatPayPublicKeyId);
    reqBuilder.addHeader("Authorization", WXPayUtility.buildAuthorization(mchid, certificateSerialNo, privateKey, METHOD, uri, null));
    reqBuilder.method(METHOD, null);
    Request httpRequest = reqBuilder.build();

    // 发送HTTP请求
    OkHttpClient client = new OkHttpClient.Builder().build();
    try (Response httpResponse = client.newCall(httpRequest).execute()) {
      String respBody = WXPayUtility.extractBody(httpResponse);
      if (httpResponse.code() >= 200 && httpResponse.code() < 300) {
        // 2XX 成功，验证应答签名
        WXPayUtility.validateResponse(this.wechatPayPublicKeyId, this.wechatPayPublicKey,
            httpResponse.headers(), respBody);

        // 从HTTP应答报文构建返回数据
        return WXPayUtility.fromJson(respBody, TransferBillEntity.class);
      } else {
        throw new WXPayUtility.ApiException(httpResponse.code(), respBody, httpResponse.headers());
      }
    } catch (IOException e) {
      throw new UncheckedIOException("Sending request to " + uri + " failed.", e);
    }
  }

  private final String mchid;
  private final String certificateSerialNo;
  private final PrivateKey privateKey;
  private final String wechatPayPublicKeyId;
  private final PublicKey wechatPayPublicKey;

  public GetTransferBillByOutNo(String mchid, String certificateSerialNo, String privateKeyFilePath, String wechatPayPublicKeyId, String wechatPayPublicKeyFilePath) {
    this.mchid = mchid;
    this.certificateSerialNo = certificateSerialNo;
    this.privateKey = WXPayUtility.loadPrivateKeyFromPath(privateKeyFilePath);
    this.wechatPayPublicKeyId = wechatPayPublicKeyId;
    this.wechatPayPublicKey = WXPayUtility.loadPublicKeyFromPath(wechatPayPublicKeyFilePath);
  }


  public static class TransferBillEntity {
    @SerializedName("mch_id")
    public String mchId;
  
    @SerializedName("out_bill_no")
    public String outBillNo;
  
    @SerializedName("transfer_bill_no")
    public String transferBillNo;
  
    @SerializedName("appid")
    public String appid;
  
    @SerializedName("state")
    public TransferBillStatus state;
  
    @SerializedName("transfer_amount")
    public Long transferAmount;
  
    @SerializedName("transfer_remark")
    public String transferRemark;
  
    @SerializedName("fail_reason")
    public String failReason;
  
    @SerializedName("openid")
    public String openid;
  
    @SerializedName("user_name")
    public String userName;
  
    @SerializedName("create_time")
    public String createTime;
  
    @SerializedName("update_time")
    public String updateTime;
  }
  
  public enum TransferBillStatus {
    @SerializedName("ACCEPTED")
    ACCEPTED,
    @SerializedName("PROCESSING")
    PROCESSING,
    @SerializedName("WAIT_USER_CONFIRM")
    WAIT_USER_CONFIRM,
    @SerializedName("TRANSFERING")
    TRANSFERING,
    @SerializedName("SUCCESS")
    SUCCESS,
    @SerializedName("FAIL")
    FAIL,
    @SerializedName("CANCELING")
    CANCELING,
    @SerializedName("CANCELLED")
    CANCELLED
  }
  
  public static class GetTransferBillByOutNoRequest {
    @SerializedName("out_bill_no")
    @Expose(serialize = false)
    public String outBillNo;
  }
  
}