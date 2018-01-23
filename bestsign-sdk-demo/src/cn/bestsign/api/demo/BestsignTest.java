package cn.bestsign.api.demo;

import cn.bestsign.api.demo.utils.RSAUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * Created By tianh, 2018/1/2.
 **/
public class BestsignTest {

    //开发者ID
    private static String developerId = "1896178307720807011";
    //开发者私钥
    private static String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC0JrpuJJgLOKJVtjQkhrNDjAo0s3LnLDLuktfMXXJ/3pWim3isgTlsUvkYo+9VKv9zKPD7rYT2BWQmCSSlwKbokd0wDrugwopYTIx/WoI+KRmXEmtVkMpcT/k+i6Ih34vDCsLpk4mapf7Xj+lrVMRXZy9v2eRMa3wnfhOwdJQfUEho3e2KRIUF5E5/fgl3im54R0MMneWPC2KDDZGXmE/Lg2jwQPpozNOjBv35UZTJvR821BD75wzw9klMEHmt22eqC/W+Trf/AmKOx8aXl60A+TaKPgTnVHswLowBAAt7vL01miJ7qlTAN+8A+6Jr2jS2gO8LQ6fxT6uzlRY14Z/jAgMBAAECggEAc+7EZOM50WbV2TDTJ3pj3KE/ZCDjXe9sq2lmZIbyi2VziFxi8SiMCrDuyrOc7oMoNzTuuBg3i5d2lp+lrOFoyBwuaqHgjxkCrMY+WCqnzFbot/bLihoOkA+LR3vWj9Prfk3rlyMyF4qhkJl1TnQTkme9+E4RhDhbgpK5GwI63FbaMLbL+Cw9vP1H5jTfqneHI/fjN/RFFjrm1VD4eCAaKNbhyYE8+sR3x0Kbw8SVLoih3RJBeCVnc5+/0yy4UHz9V++8fEZzqVDKNQRu5hPPneoWvRVKDTdBnhQqZxKI/StJiy5swEIEYq1a8hMdD93kuvIITz2pXw9wHGkY7ezzUQKBgQDh4SJXOL/wP3xtHiVu/r2BBzUaSzMV6iw70KpC+byhPRcFE8ogx815P8Zt6jDyzrtC0ZA2sCsTQq3/rOLG100GspaxVtYiAFLFC4VPRUOahvDj22O+Rekwrq4nPg2lZP0Wt5hskRCPve9DphkwswxJfQt7pbPRhrYdWq5VLIKlOQKBgQDMLJF86neV/+/GaJU2bMlyHAfjQW99pF+/qH0wznmVbuVWBoK4USaS4eGORz+Cx79pKl4dNV+UvlSA8AuLzQ+YyUuvfXgKPehnDFuNvzgMkPRUp5reUMDzfbvBN62rHK1jO/8HMeZQUnafAx/5dUnjR6VMcbhbWAbmJmRgJ2+p+wKBgAL6VNmRhfZE3/8QRq4P7a+lyK1wEFxZmfuv5I69fB8kDwmiGSgVej/+9z67t6l70DwxRxVxfR/j4SddwB+e9wT/lb0AyBHqryyp2jgRUbLX1JUsb0Qy58AcjW83AjcL/cou4XOM9grvFhhuOCbMNX1CiMQ4iwZYIE6Cw4mb7J0BAoGAQ6tt2OH5Gp0GAlN9SNmLdqd1sctpQVIubd5RB9EXGQD1P6rOvnoe98WntTlGAnljpl5lbPbYo/rlFQr6OK6RQclNrW8/Tt2v3h+JZJSA5iFQ6ZHXUWGgYdNFXEew5qqNiPtEjkTqmaqLKC6n9Uz7XTnMvmZefN/TAYqt7/SCHOsCgYEA0XM9KkeBmh1XWHHKaICopSKMxzcql94bJ0vwVYA8u9xF15+jD8faubkb0r59FD/Hnc7Zc8iIYXiaf3qr+N+lddo92iDDfXHXDAU98dWbrNIoNrKln04u043cEZNl6aFqSoTaWu9M8eHZugakElHeKyBkExZ+nnGd171Hx+CeDJo=";

    private static String host = "https://openapi.bestsign.info/openapi/v2";
    //private static String host = "https://openapi.bestsign.info/openapi/v3";

    private static String userAccount = "test1";

    private static String enterpriseAccount = "enterpriseAccount";

    private static String fid = "2920644942452298673";  //修改前的pdf文件
    private static String afterFid = "5419326175367886668"; // 修改后的pdf文件
    //private static String afterFid = "4926289778022527542"; // 修改后的pdf文件

    //private static String contractId = "151494714501000001";
    private static String contractId = "151496401401000001";
    private static int pageNum = 1;

    public static void main(String[] args) throws Exception {
        // 1.注册用户
        //personalUserReg();

        // 2.设置个人用户证件信息 (设置企业用户证件信息 )
        //setPersonalCredential();
        //setEnterpriseCredential();

        // 3. 同步申请数字证书
        //applyCert();

        // 4. 生成用户签名/印章图片
        //createSignatureImage();

        // 5. 上传用户签名/印章图片
        //uploadSignatureImage();

        // 6. 下载用户签名/印章图片
        //downloadSignatureImage();

        // 7. 上传合同文件  === 或者 使用上传并创建合同接口【4.1】
//        //uploadFile();
//
//        // 7.1 查询个人用户证件信息
//        JSONObject jsonObject = getPersonalCredential();
//        String name = jsonObject.getString("name");
//        String identity = jsonObject.getString("identity");
//        System.out.println("name:" + name);
//        System.out.println("identity: " + identity);
//
//        // 8. 为PDF文件添加元素
//        addPDFElements("刘文", identity);

        // 9. 下载文件
        //getDownloadURL();

        // 10. 创建合同
//        createContract();

        // 11. 上传并创建和合同
        //uploadAndCreateContract();

        // 12. 签署合同
//        signContract();
        //signContractAuto();

        // 13.锁定并结束合同
        //【跟接口定义不一致，没有返回新的fid】
        //lockAndFinishContract();

        // 14. 查询合同信息
        //{"errno":0,"cost":23,"data":{"fid":"3309092530870397646","senderAccount":"test1","finishTime":"2018-01-03 10:58:20","docType":"0","description":"测试合同描述","title":"测试合同标题","userId":"151488345201000001","sendTime":"2018-01-03 10:39:05.0","signers":["test1"],"developerId":"1896178307720807011","expireTime":"1514956645","pages":"1","isAnyProcess":"0","contractId":"151494714501000001","isDel":"0","status":"5"},"errmsg":""}
        //getContractInfo();

        // 15. 下载合同文件
        //downloadContract();

        // 16. 获取预览页URL
        //getPreviewURL();

        // 17. 设置合同签署参数
        //setSignerConfig();

        // 18. 获取合同手动签署页URL
        //getSignURL();

        // 19. 获取合同预览图片
        //previewContract();

        // 20. 撤销合同
        //cancelContract();

        // 21. 单个添加签署者
        //addSigner();
        //addSigners();

        // 22. 获取合同文件列表 [可能用不到]
        //getContracts();
    }



    /**
     * 【1.6】
     *  个人用户注册
     * [注册，设置实名信息，申请数字证书，生成默认签名]
     * @throws Exception
     */
    public static String personalUserReg() throws Exception{
        String method = "/user/reg/";

        JSONObject requestBody= new JSONObject(); // request body 参数
        requestBody.put("account","test3");
        //requestBody.put("account",enterpriseAccount);
        requestBody.put("name",enterpriseAccount);
        requestBody.put("userType","1");  // 1:个人   2：企业
        requestBody.put("mail","");
        requestBody.put("mobile","18819466153");

        sendPost(method, requestBody.toJSONString());
        return userAccount;
    }

    /**
     * 【1.7】
     *  设置个人用户证件信息
     */
    public static void setPersonalCredential(){
        String method = "/user/setPersonalCredential/";

        JSONObject requestBody = new JSONObject();
        requestBody.put("account", "test3");
        requestBody.put("name","test3");
        requestBody.put("identity","610427198406172518");
        requestBody.put("identityType","0");
        requestBody.put("contactMail","wanghf617@163.com");
        requestBody.put("contactMobile","13456833929");
        requestBody.put("province","浙江");
        requestBody.put("city","杭州");
        requestBody.put("address","西湖");

        sendPost(method, requestBody.toJSONString());
    }

    /**
     * 【1.8】
     *  设置企业用户证件信息
     */
    public static void setEnterpriseCredential(){
        String method = "/user/setEnterpriseCredential/";

        JSONObject requestBody = new JSONObject();
        requestBody.put("account",enterpriseAccount);
        requestBody.put("regCode","91110108551385082Q");
        requestBody.put("taxCode","91110108551385082Q");
        requestBody.put("orgCode","55138508-2");
        requestBody.put("name","小米科技有限责任公司");
        requestBody.put("legalPerson","王海峰");
        requestBody.put("legalPersonIdentity","610427198406172518");
        requestBody.put("legalPersonIdentityType","0");
        requestBody.put("legalPersonMobile","18951765075");
        requestBody.put("contactMail","wanghf617@qq.com");
        requestBody.put("contactMobile","18951765075");
        requestBody.put("province","浙江");
        requestBody.put("city","杭州");
        requestBody.put("address","西湖");

        sendPost(method, requestBody.toJSONString());
    }

    /**
     * 【1.9】
     *  同步申请数字证书
     */
    public static void applyCert(){
        String method = "/user/applyCert/";

        JSONObject requestBody = new JSONObject();
        requestBody.put("account","test3");
        //requestBody.put("account",enterpriseAccount);
        requestBody.put("certType","");

        sendPost(method, requestBody.toJSONString());
    }


    /**
     * 【2.1】
     *  生成用户签名/印章图片
     */
    public static void createSignatureImage(){
        String method = "/signatureImage/user/create/";

        JSONObject requestBody= new JSONObject(); // request body 参数
        requestBody.put("account",userAccount);
        requestBody.put("text","李四");

        sendPost(method, requestBody.toJSONString());
    }

    /**
     * 【2.2】
     *  上传用户签名/印章图片
     */
    public static void uploadSignatureImage() throws Exception {
        String method = "/signatureImage/user/upload/";

        FileInputStream file = new FileInputStream("D:\\签名.png");
        byte[] bdata = IOUtils.toByteArray(file);
        String fdata = Base64.encodeBase64String(bdata);

        JSONObject requestBody = new JSONObject();
        requestBody.put("account",userAccount);
        requestBody.put("imageData",fdata);

        sendPost(method, requestBody.toJSONString());
    }

    /**
     * 【2.3】
     *  下载用户签名/印章图片
     */
    public static void downloadSignatureImage(){
        String method = "/signatureImage/user/download/";

        String urlParams = "account="+ userAccount +"&imageName=default";

        sendGet(method, urlParams);
    }

    /**
     * 上传合同文件
     */
    public static void uploadFile() throws IOException {
        String method = "/storage/upload/";

        FileInputStream file = new FileInputStream("D:\\合同demo5.pdf");
        byte[] bdata = IOUtils.toByteArray(file);
        String fmd5 = DigestUtils.md5Hex(bdata);
        String fdata = Base64.encodeBase64String(bdata);

        JSONObject requestBody= new JSONObject(); // request body 参数
        requestBody.put("account", userAccount);
        requestBody.put("fmd5", fmd5);
        requestBody.put("ftype", "pdf");
        requestBody.put("fname", "合同demo4.pdf");
        requestBody.put("fpages", "1");
        requestBody.put("fdata", fdata);

        sendPost(method, requestBody.toJSONString());
    }

    /**
     * 【1.10】
     * 查询个人用户证件信息
     */
    public static JSONObject getPersonalCredential(){
        String method = "/user/getPersonalCredential/";

        JSONObject requestBody = new JSONObject();
        requestBody.put("account",userAccount);
        return sendPost(method, requestBody.toJSONString());
    }

    /**
     * 为PDF文件添加元素
     */
    public static void addPDFElements(String name, String identity){
        String method = "/storage/addPDFElements/";

        String signParams = "&noSign=1"; //RSA签名参数，详见参数签名章节描述
        JSONObject requestBody= new JSONObject();
        requestBody.put("fid", fid);
        requestBody.put("account", userAccount);

        JSONArray elements = new JSONArray();
        JSONObject ele = new JSONObject();
        ele.put("pageNum", String.valueOf(pageNum));
        ele.put("x", "0.212");
        ele.put("y","0.808");
        ele.put("type", "text");
        ele.put("value", name);
        ele.put("fontSize", "10");

        JSONObject ele2 = new JSONObject();
        ele2.put("pageNum", String.valueOf(pageNum));
        ele2.put("x", "0.42");
        ele2.put("y","0.808");
        ele2.put("type", "text");
        ele2.put("value", identity);
        ele2.put("fontSize", "10");

        JSONObject ele3 = new JSONObject();
        ele3.put("pageNum", String.valueOf(pageNum));
        ele3.put("x", "0.75");
        ele3.put("y","0.808");
        ele3.put("type", "text");
        String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        ele3.put("value", date);
        ele3.put("fontSize", "10");

        elements.add(ele);
        elements.add(ele2);
        elements.add(ele3);

        requestBody.put("elements",elements);

        sendPost2(method, requestBody.toJSONString(), signParams);
    }


    /**
     * 【3.4】
     *  下载文件
     */
    public static void getDownloadURL() {
        String method = "/storage/download/";
        String urlParams = "fid=" + afterFid;

        sendGet(method, urlParams);
    }

    /**
     * 【4.11】
     * 创建合同
     */
    public static void createContract(){
        String method = "/contract/create/";

        JSONObject requestBody= new JSONObject(); // request body 参数
        requestBody.put("account",userAccount);
        requestBody.put("fid","3422097611019436972");
        long epochSecond = Instant.now().getEpochSecond() + 10 * 60 * 60;
        requestBody.put("expireTime",String.valueOf(epochSecond));
        requestBody.put("title","test1合同");
        requestBody.put("description","test1合同描述");

        sendPost(method, requestBody.toJSONString());
    }

    /**
     * 【4.1】上传并创建合同
     * @throws Exception
     */
    public static void uploadAndCreateContract() throws Exception {
        String method = "/storage/contract/upload/";

        FileInputStream file = new FileInputStream("D:\\合同demo6.pdf");
        byte[] bdata = IOUtils.toByteArray(file);
        String fmd5 = DigestUtils.md5Hex(bdata);
        String fdata = Base64.encodeBase64String(bdata);

        JSONObject requestBody= new JSONObject(); // request body 参数
        requestBody.put("account",userAccount);
        requestBody.put("fmd5",fmd5);
        requestBody.put("ftype","pdf");
        requestBody.put("fname","合同demo.pdf");
        requestBody.put("fpages",String.valueOf(pageNum));
        requestBody.put("fdata",fdata);
        requestBody.put("title","合同demo.title");
        requestBody.put("description","合同demo.description");
        long expireTime = Instant.now().getEpochSecond() + 10 * 60 * 60;
        requestBody.put("expireTime",String.valueOf(expireTime));

        sendPost(method, requestBody.toJSONString());
    }

    /**
     * 【4.2】 签署合同
     */
    public static void signContract(){
        String method = "/contract/sign/cert/";

        JSONObject requestBody= new JSONObject(); // request body 参数
        requestBody.put("contractId","151498313601000001");
        requestBody.put("signerAccount",userAccount);
        JSONObject pos1 = new JSONObject();
        pos1.put("pageNum","1");
        pos1.put("x","0.66");
        pos1.put("y","0.69");

        JSONArray signaturePositions = new JSONArray();
        signaturePositions.add(pos1);
        requestBody.put("signaturePositions",signaturePositions);
        requestBody.put("signatureImageData","");
        requestBody.put("signatureImageName","");

        sendPost(method, requestBody.toJSONString());
    }

    /**
     * 【4.4】
     *  锁定并完成合同
     */
    public static void lockAndFinishContract(){
        String method = "/contract/lock/";

        JSONObject requestBody= new JSONObject(); // request body 参数
        requestBody.put("contractId",contractId);

        sendPost(method, requestBody.toJSONString());
    }

    /**
     * 【4.6】
     *  查询合同信息
     */
    public static void getContractInfo(){
        String method = "/contract/getInfo/";

        JSONObject requestBody= new JSONObject(); // request body 参数
        requestBody.put("contractId",contractId);

        sendPost(method, requestBody.toJSONString());
    }

    /**
     *  【4.9】
     *   下载合同文件
     */
    public static void downloadContract(){
        String method = "/contract/download/";
        String urlParams = "contractId=" + contractId;

        sendGet(method, urlParams);
    }

    /**
     * 【4.10】
     *  获取预览页URL
     */
    public static void getPreviewURL(){
        String method = "/contract/getPreviewURL/";

        JSONObject requestBody= new JSONObject(); // request body 参数
        requestBody.put("contractId",contractId);
        requestBody.put("account",userAccount);
        long expireTime = Instant.now().getEpochSecond() + 2 * 60 * 60;
        System.out.println(expireTime);
        requestBody.put("expireTime",String.valueOf(expireTime));
        requestBody.put("quality","100");

        sendPost(method, requestBody.toJSONString());
    }

    /**
     * 【4.14】
     *  设置合同签署参数
     */
    public static void setSignerConfig(){
        String method = "/contract/setSignerConfig/";

        JSONObject requestBody= new JSONObject(); // request body 参数
        requestBody.put("contractId",contractId);
        requestBody.put("account",userAccount);
        JSONArray signaturePositions = new JSONArray();
        JSONObject pos1 = new JSONObject();
        pos1.put("pageNum","1");
        pos1.put("x","0.1");
        pos1.put("y","0.2");
        signaturePositions.add(pos1);
        requestBody.put("signaturePositions",signaturePositions);
        requestBody.put("isAllowChangeSignaturePosition","0");
        requestBody.put("returnUrl","");
        requestBody.put("password","");
        requestBody.put("isVerifySigner","0");
        requestBody.put("vcodeMail","");
        requestBody.put("vcodeMobile","18819466153");
        requestBody.put("isDrawSignatureImage","1");
        requestBody.put("signatureImageName","default");
        requestBody.put("certType","");
        requestBody.put("app","");

        sendPost(method, requestBody.toJSONString());
    }

    /**
     * 【4.17】
     *  获取合同手动签署页URL
     */
    public static void getSignURL(){
        String method = "/contract/getSignURL/";

        JSONObject requestBody= new JSONObject(); // request body 参数
        requestBody.put("contractId",contractId);
        requestBody.put("account",userAccount);
        long expireTime = Instant.now().getEpochSecond() + 3 * 60 * 60;
        requestBody.put("expireTime",String.valueOf(expireTime));
        requestBody.put("quality","100");

        sendPost(method, requestBody.toJSONString());
    }

    /**
     *  【4.20】
     *   获取合同预览图片
     */
    public static void previewContract(){
        String method = "/contract/preview/";
        String urlParams = "contractId="+ contractId +"&pageNum=2&quality=100";

        sendGet(method, urlParams);
    }

    /**
     * 【4.5】
     *  撤销合同
     */
    public static void cancelContract(){
        String method = "/contract/cancel/";

        JSONObject requestBody= new JSONObject(); // request body 参数
        requestBody.put("contractId",contractId);

        sendPost(method, requestBody.toJSONString());
    }

    /**
     * 【4.12】
     *  添加单个签署者
     */
    public static void addSigner(){
        String method = "/contract/addSigner/";

        JSONObject requestBody= new JSONObject(); // request body 参数
        requestBody.put("contractId",contractId);
        requestBody.put("signer","test2");

        sendPost(method, requestBody.toJSONString());
    }

    /**
     * 【8.6】
     * 签署合同（自动签）
     */
    public static void signContractAuto(){
        String method = "/contract/sign/cert/";

        JSONObject requestBody= new JSONObject(); // request body 参数
        requestBody.put("contractId",contractId);
        requestBody.put("signerAccount","test3");
        JSONObject pos1 = new JSONObject();
        pos1.put("pageNum","1");
        pos1.put("x","0.1");
        pos1.put("y","0.4");
        JSONObject pos2 = new JSONObject();
        pos2.put("pageNum","1");
        pos2.put("x","0.5");
        pos2.put("y","0.5");
        JSONArray signaturePositions = new JSONArray();
        signaturePositions.add(pos1);
        signaturePositions.add(pos2);
        requestBody.put("signaturePositions",signaturePositions);
        requestBody.put("signatureImageData","");
        requestBody.put("signatureImageName","");

        sendPost(method, requestBody.toJSONString());
    }

    /**
     * 【4.21】
     * 获取合同列表
     */
    public static void getContracts(){
        String method = "/catalog/getContracts/";

        JSONObject requestBody= new JSONObject(); // request body 参数
        requestBody.put("catalogName","测试");

        sendPost(method, requestBody.toJSONString());
    }

    /**
     * 发送POST请求
     * @param method
     * @param requestBody
     */
    private static JSONObject sendPost(String method, String requestBody){
        try {
            String rtick = RSAUtils.getRtick();
            String signParams = RSAUtils.calcRsaSign(developerId, privateKey, host, method, rtick, null, requestBody);
            String params = "?developerId=" + developerId + "&rtick=" + rtick + "&signType=rsa&sign=";

            //构建OKHttpClient来发送请求，这里客户端可根据实际情况处理
            OkHttpClient client = new OkHttpClient();

            //请求类型为JSON
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, requestBody);
            Request.Builder builder = new Request.Builder();

            //完整请求url包含：请求host，方法名，签名参数，url参数
            String url = host + method + params + signParams;
            Request request = builder.url(url)
                    .post(body)
                    .addHeader("content-type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();

            //请求返回结果无论成功失败，http-status均为200
            if(response.code() == 200){
                ResponseBody responseBody = response.body();

                //返回结果解析
                JSONObject userObj = JSON.parseObject(responseBody.string());

                //返回errno为0，表示成功，其他表示失败
                if(userObj.getIntValue("errno") == 0){
                    JSONObject data = userObj.getJSONObject("data");
                    if(data != null){
                        System.out.println("contractId="+data.getString("contractId"));
                    }

                    System.out.println("success");
                    System.out.println(userObj);
                    return data;
                }else{
                    System.out.println("error: code="+userObj.getIntValue("errno")+", errmsg="+userObj.getString("errmsg"));
                }
            }else{

                //非正常结果
                System.out.println("error:  code="+response.code()+", errmsg="+response.body().string());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 发送POST请求
     * @param method
     * @param requestBody
     */
    private static JSONObject sendPost2(String method, String requestBody, String urlParams){
        try {
            String rtick = RSAUtils.getRtick();
            String signParams = RSAUtils.calcRsaSign(developerId, privateKey, host, method, rtick, null, requestBody);
            String params = "?developerId=" + developerId + "&rtick=" + rtick + "&signType=rsa&sign=";

            //构建OKHttpClient来发送请求，这里客户端可根据实际情况处理
            OkHttpClient client = new OkHttpClient();

            //请求类型为JSON
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, requestBody);
            Request.Builder builder = new Request.Builder();

            //完整请求url包含：请求host，方法名，签名参数，url参数
            String url = host + method + params + signParams + urlParams;
            Request request = builder.url(url)
                    .post(body)
                    .addHeader("content-type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();

            //请求返回结果无论成功失败，http-status均为200
            if(response.code() == 200){
                ResponseBody responseBody = response.body();

                //返回结果解析
                JSONObject userObj = JSON.parseObject(responseBody.string());

                //返回errno为0，表示成功，其他表示失败
                if(userObj.getIntValue("errno") == 0){
                    JSONObject data = userObj.getJSONObject("data");
                    System.out.println("success");
                    System.out.println(userObj);

                    return data;
                }else{
                    System.out.println("error: code="+userObj.getIntValue("errno")+", errmsg="+userObj.getString("errmsg"));
                }
            }else{

                //非正常结果
                System.out.println("error:  code=" + response.code() + ", errmsg=" + response.body().string());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 发送GET请求
     * @param method
     * @param urlParams
     */
    private static void sendGet(String method, String urlParams){
        try {
            String rtick = RSAUtils.getRtick();
            String signParams = RSAUtils.calcRsaSign(developerId, privateKey, host, method, rtick, urlParams, null);
            String params = "?developerId=" + developerId + "&rtick=" + rtick + "&signType=rsa&sign=";

            //构建OKHttpClient来发送请求，这里客户端可根据实际情况处理
            OkHttpClient client = new OkHttpClient();
            Request.Builder builder = new Request.Builder();
            //完整请求url包含：请求host，方法名，签名参数，url参数
            String url = host + method + params + signParams + "&" + urlParams;
            System.out.println(url);
            Request request = builder.url(url)
                    .get()
                    .addHeader("content-type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
            //请求返回结果无论成功失败，http-status均为200
            if(response.code() == 200){
                ResponseBody responseBody = response.body();

                byte[] responseBytes = responseBody.bytes();
                System.out.println(responseBytes.length);
            }else{
                //非正常结果
                System.out.println("error:  code=" + response.code() + ", errmsg=" + response.body().string());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
