package cn.bestsign.api.demo;

import cn.bestsign.api.demo.client.BestsignSDKClient;
import cn.bestsign.api.demo.utils.RSAUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;

/**
 * 上上签混合云SDK测试
 */
public class BestsignSDKTest {

    //开发者ID
    private static String developerId = "";
    //开发者私钥
    private static String privateKey = "";


    //混合云Server的完整HOST，需要根据不同的环境配置
    /**
     * 测试环境（http）：http://openapi.bestsign.info/openapi/v2
     * 测试环境（https）：https://openapi.bestsign.info/openapi/v2
     * 正式环境（https）：https://openapi.bestsign.cn/openapi/v2
     */
    private static String serverHost = "https://openapi.bestsign.info/openapi/v2"; //"https://openapi.bestsign.info/openapi/v2";

    //混合云SDK的完整HOST，您部署的SDK的完整HOST，请替换http://192.168.30.185:8087部分
    private static String sdkHost = "http://192.168.30.185:8087";

    //混合云SDK封装的client，您可以使用它来调用任一api。
    private static BestsignSDKClient sdkClient = new BestsignSDKClient(developerId, privateKey, serverHost, sdkHost);

    public static void main(String[] args) throws Exception{

        //用户注册场景
    	//分为两个：1. 个人注册
    	//		 2. 企业注册
        String account1 = personalUserReg(); //个人用户注册
        String account2 = enterpriselUserReg(); //企业用户注册

        //签约场景，一般分为自动签和手动签（手动签和自动签可穿插执行）
        //自动签三步：1.创建合同   2.自动签署（可根据签署人循环执行）  3.锁定结束合同
        //手动签三步：1.创建合同   2.发送合同（可根据签署人循环执行）  3.锁定结束合同

        //创建合同场景
        String contractId = createContract(account1); //创建合同
        autoSign(contractId, account1); //自动签署
        //handSign(contractId, account1); //手动签署
        finishContract(contractId); //结束合同

        //辅助接口，预览合同和下载合同
        previewContract(contractId, account1); //预览合同
        downloadContract(contractId); //下载合同pdf文件
    }

    /**
     * 个人用户注册
     * [注册，设置实名信息，申请数字证书，生成默认签名]
     * @throws Exception
     */
    public static String personalUserReg() throws Exception{
        //用户注册
        String account = "18910001001"; //账号
        String name = "尚尚"; //用户名称
        String mail = ""; //用户邮箱
        String mobile = "18910001001"; //用户手机号码

        //设置个人实名信息
        String identity = "110101199301018270"; //证件号码
        String identityType = "0"; //证件类型  0-身份证
        String contactMail = ""; //联系邮箱
        String contactMobile = mobile; //联系电话
        String province = "浙江"; //所在省份
        String city = "杭州"; //所在城市
        String address = "西湖区万塘路317号华星世纪大楼102"; //联系地址
       
        int r = sdkClient.userPersonalReg(account, name, mail, mobile, identity, identityType, contactMail, contactMobile, province, city, address);
        if(r==0){
        	System.out.println(account+"注册申请成功");
        }
        //设置默认签名
        String result = sdkClient.signatureImageCreate(account,"");
        System.out.println("默认签名设置"+result);
        return account;
    }

    /**
     * 企业用户注册
     * [用户注册，设置实名信息，申请数字证书，生成默认签名]
     * @return
     * @throws Exception
     */
    public static String enterpriselUserReg() throws Exception{
        //用户注册
        String account = "test001@bestsign.cn"; // 用户账号
        String name = "杭州上上签网络科技有限公司"; //企业名称
        String mail = ""; //用户邮箱
        String mobile = "13910001001"; //用户手机号码

        //设置个人实名信息
        String regCode = "91110108551385082Q"; // 营业执照代码或者统一社会信用代码
        String taxCode = "91110108551385082Q"; //税务登记证代码 如果是三证合一，此项填统一社会信用代码
        String orgCode = "55138508-2"; //组织机构代码 如果是三证合一，此项填统一社会信用代码
        String legalPerson = "杰克"; //法人姓名
        String legalPersonIdentity = "110101199301015299";//法人证件号码
        String legalPersonIdentityType = "0"; //法人证件类型
        String legalPersonMobile = mobile; //法人手机号码
        String contactMail = ""; //联系邮箱
        String contactMobile = mobile; //联系电话
        String province = "浙江"; //所在省份
        String city = "杭州"; //所在城市
        String address = "西湖区万塘路317号华星世纪大楼202"; //公司地址

        int i = sdkClient.userEnterpriseReg(account, name, mail, mobile, regCode, taxCode, orgCode, legalPerson, legalPersonIdentity, legalPersonIdentityType, legalPersonMobile, contactMail, contactMobile, province, city, address);
        if(i==0){
            System.out.println(account+"注册成功");
        }

        //设置默认签名
        sdkClient.signatureImageCreate(account,"");
        System.out.println("默认签名设置成功");
        return account;
    }

    /**
     * 创建合同
     * [上传文件，创建合同]
     * @throws Exception
     */
    public static String createContract(String account) throws Exception{

        //上传合同
        byte[] fileBytes = inputStream2ByteArray("d:\\test\\test.pdf");
        String fmd5 = RSAUtils.md5(fileBytes); //原始文件md5
        String ftype = "pdf"; //文件类型，仅支持pdf
        String fname = "test.pdf"; //文件名称
        String fpages = "2"; //pdf文件总页数
        String fdata = RSAUtils.base64encodeString(fileBytes); //文件内容Base64字符串       
        String expireTime = Long.toString(System.currentTimeMillis() / 1000 + (3600 * 24 * 7)); //合同过期时间  当前系统时间时间戳+过期时间秒数
        String title = "个人理财合同"; //合同名称
        String description = "这是一份来自上上签的理财合同"; //合同内容描述
        String contractId = sdkClient.contractUpload(account, fmd5, ftype, fname, fpages, fdata, expireTime, title, description);
        
        System.out.println("createContract contractId="+contractId);
        return contractId;
    }

    /**
     * 自动签署
     * @throws Exception
     */
    public static void autoSign(String contractId, String signer) throws Exception{
    	//添加合同签署人
        sdkClient.contractAddSigner(contractId, signer); //与批量添加二选一        
        
        JSONArray signaturePositions = new JSONArray(); //签署位置列表
        JSONObject pos1 = new JSONObject();
        pos1.put("pageNum","1"); //签署位置页码
        pos1.put("x","0.6"); //签署位置x坐标，百分比
        pos1.put("y","0.4"); //签署位置y坐标，百分比
        signaturePositions.add(pos1);
        String signatureImageData = ""; //签名图片，为空使用用户默认签名
        String result = sdkClient.contractSignCert(contractId, signer, signatureImageData, signaturePositions);
        System.out.println("autoSign result="+result);
    }

    /**
     * 获取手动签署URL
     * @throws Exception
     */
    public static String handSign(String contractId, String signer) throws Exception{
        
        JSONArray signaturePositions = new JSONArray();
        JSONObject pos1 = new JSONObject();
        pos1.put("pageNum","1");
        pos1.put("x","0.3");
        pos1.put("y","0.4");
        signaturePositions.add(pos1);
        String returnUrl = "https://www.bestsign.cn"; //手动签署完成后的跳转地址
        String vcodeMobile = signer; //手动签署时接收短信验证码的手机号码
        String dpi = "96"; //预览图片清晰度，枚举值：96-低清（默认），120-普清，160-高清，240-超清
        String expireTime = Long.toString(System.currentTimeMillis() / 1000 + (3600 * 24 * 7));
        //生成手动签署链接
        String url = sdkClient.contractSend(contractId, signer, signaturePositions, returnUrl, vcodeMobile, expireTime, dpi);
        System.out.println("sign url="+url);
        return url;
    }

    /**
     * 锁定结束合同
     * @throws Exception
     */
    public static void finishContract(String contractId) throws Exception{
        String result = sdkClient.contractLockFinish(contractId); //锁定结束合同
        System.out.println("finishContract result="+result);
    }

    /**
     * 获取预览合同URL
     * @throws Exception
     */
    public static void previewContract(String contractId, String signer) throws Exception{
    	String dpi = "96"; //预览图片清晰度，枚举值：96-低清（默认），120-普清，160-高清，240-超清
        String expireTime = Long.toString(System.currentTimeMillis() / 1000 + (3600 * 24 * 7));
        String url = sdkClient.getPreviewURL(contractId, signer, expireTime, dpi);
        System.out.println("preview url="+url);
    }

    /**
     * 下载合同
     * @throws Exception
     */
    public static void downloadContract(String contractId) throws Exception{
        byte[] pdf = sdkClient.contractDownload(contractId);
        byte2File(pdf, "D:\\test", contractId+".pdf"); //文件下载到本地目录
        System.out.println("download contract pdf path=" + "D:\\test\\" + contractId+".pdf");
    }

    /**
     * 辅助方法，本地文件转为byte[]
     * @param filePath
     * @return
     */
    private static byte[] inputStream2ByteArray(String filePath) {
        byte[] data = null;
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = new FileInputStream(filePath);
            out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024 * 4];
            int n = 0;
            while ((n = in.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
            data = out.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (Exception e) {
                //ignore
            }
        }
        return data;
    }

    /**
     * 辅助方法，byte数组保存为本地文件
     * @param buf
     * @param filePath
     * @param fileName
     */
    private static void byte2File(byte[] buf, String filePath, String fileName)
    {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try
        {
            File dir = new File(filePath);
            if (!dir.exists())
            {
                dir.mkdirs();
            }
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (bos != null)
            {
                try
                {
                    bos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (fos != null)
            {
                try
                {
                    fos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
