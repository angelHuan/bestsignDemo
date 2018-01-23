package cn.bestsign.api.demo.client;

import cn.bestsign.api.demo.utils.HttpClientSender;
import cn.bestsign.api.demo.utils.RSAUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

/**
 * 上上签混合云SDK客户端
 */
public class BestsignSDKClient {

	private String developerId;

	private String privateKey;

	private String serverHost;

	private String sdkHost;

	private static String urlSignParams = "?developerId=%s&rtick=%s&signType=rsa&sign=%s";

	public BestsignSDKClient(String developerId, String privateKey,
			String serverHost, String sdkHost) {
		this.developerId = developerId;
		this.privateKey = privateKey;
		this.serverHost = serverHost;
		this.sdkHost = sdkHost;
	}

	// ***************常规接口start*****************************************************
	/**
	 * 1.1 个人用户注册
	 * 
	 * @param account
	 *            用户账号
	 * @param name
	 *            姓名
	 * @param mail
	 *            用来接收通知邮件的电子邮箱
	 * @param mobile
	 *            用来接收通知短信的手机号码
	 * @param identity
	 *            证件号码
	 * @param identityType
	 *            枚举值：0-身份证，目前仅支持身份证
	 * @param contactMail
	 *            电子邮箱
	 * @param contactMobile
	 *            手机号码
	 * @param province
	 *            省份
	 * @param city
	 *            城市
	 * @param address
	 *            地址
	 * @return 是否成功
	 * @throws IOException
	 */
	public int userPersonalReg(String account, String name, String mail,
			String mobile, String identity, String identityType,
			String contactMail, String contactMobile, String province,
			String city, String address) throws Exception {
		String host = this.serverHost;
		String method = "/user/reg/";

		// 组装请求参数，作为requestbody
		JSONObject requestBody = new JSONObject();
		requestBody.put("account", account);
		requestBody.put("name", name);
		requestBody.put("userType", "1");
		requestBody.put("mail", mail);
		requestBody.put("mobile", mobile);

		JSONObject credential = new JSONObject();
		credential.put("identity", identity);
		credential.put("identityType", identityType);
		credential.put("contactMail", contactMail);
		credential.put("contactMobile", contactMobile);
		credential.put("province", province);
		credential.put("city", city);
		credential.put("address", address);
		requestBody.put("credential", credential);
		requestBody.put("applyCert", "1");

		// 生成一个时间戳参数
		String rtick = RSAUtils.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtils.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, null,
				requestBody.toJSONString());
		// 签名参数追加为url参数
		String urlParams = String.format(urlSignParams, this.developerId,
				rtick, paramsSign);
		// 发送请求
		String responseBody = HttpClientSender.sendHttpPost(host, method,
				urlParams, requestBody.toJSONString());
		// 返回结果解析
		JSONObject userObj = JSON.parseObject(responseBody);
		// 返回errno为0，表示成功，其他表示失败
		if (userObj.getIntValue("errno") == 0) {
			return 0;
		} else {
			throw new Exception(userObj.getIntValue("errno") + ":"
					+ userObj.getString("errmsg"));
		}
	}

	/**
	 * 1.2 企业用户注册
	 * 
	 * @param account
	 *            用户账号
	 * @param name
	 *            企业名称
	 * @param mail
	 *            用来接收通知邮件的电子邮箱
	 * @param mobile
	 *            用来接收通知短信的手机号码
	 * @param regCode
	 *            营业执照代码或者统一社会信用代码
	 * @param taxCode
	 *            税务登记证代码 如果是三证合一，此项填统一社会信用代码
	 * @param orgCode
	 *            组织机构代码 如果是三证合一，此项填统一社会信用代码
	 * @param legalPerson
	 *            法人代表姓名
	 * @param legalPersonIdentity
	 *            法人代表证件号码
	 * @param legalPersonIdentityType
	 *            法人代表证件类型
	 * @param legalPersonMobile
	 *            法人代表手机号码
	 * @param contactMail
	 *            联系邮箱
	 * @param contactMobile
	 *            联系手机号码
	 * @param province
	 *            所在省份
	 * @param city
	 *            所在城市
	 * @param address
	 *            公司地址
	 * @return 是否成功
	 * @throws IOException
	 */
	public int userEnterpriseReg(String account, String name, String mail,
			String mobile, String regCode, String taxCode, String orgCode,
			String legalPerson, String legalPersonIdentity,
			String legalPersonIdentityType, String legalPersonMobile,
			String contactMail, String contactMobile, String province,
			String city, String address) throws Exception {
		String host = this.serverHost;
		String method = "/user/reg/";

		// 组装请求参数，作为requestbody
		JSONObject requestBody = new JSONObject();
		requestBody.put("account", account);
		requestBody.put("name", name);
		requestBody.put("userType", "2");
		requestBody.put("mail", mail);
		requestBody.put("mobile", mobile);

		JSONObject credential = new JSONObject();
		credential.put("regCode", regCode);
		credential.put("taxCode", taxCode);
		credential.put("orgCode", orgCode);
		credential.put("legalPerson", legalPerson);
		credential.put("legalPersonIdentity", legalPersonIdentity);
		credential.put("legalPersonIdentityType", legalPersonIdentityType);
		credential.put("legalPersonMobile", legalPersonMobile);
		credential.put("contactMail", contactMail);
		credential.put("contactMobile", contactMobile);
		credential.put("province", province);
		credential.put("city", city);
		credential.put("address", address);
		requestBody.put("credential", credential);
		requestBody.put("applyCert", "1");

		// 生成一个时间戳参数
		String rtick = RSAUtils.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtils.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, null,
				requestBody.toJSONString());
		// 签名参数追加为url参数
		String urlParams = String.format(urlSignParams, this.developerId,
				rtick, paramsSign);
		// 发送请求
		String responseBody = HttpClientSender.sendHttpPost(host, method,
				urlParams, requestBody.toJSONString());
		// 返回结果解析
		JSONObject userObj = JSON.parseObject(responseBody);
		// 返回errno为0，表示成功，其他表示失败
		if (userObj.getIntValue("errno") == 0) {
			return 0;
		} else {
			throw new Exception(userObj.getIntValue("errno") + ":"
					+ userObj.getString("errmsg"));
		}
	}

	/**
	 * 2. 查询CA证书编号
	 * 
	 * @param account
	 *            用户账号
	 * @param certType
	 *            证书类型，目前支持CFCA和ZJCA，可为空，由系统自动判断
	 * @return
	 * @throws Exception
	 */
	public String getCert(String account) throws Exception {
		String host = this.serverHost;
		String method = "/user/getCert/";

		// 组装请求参数，作为requestbody
		JSONObject requestBody = new JSONObject();
		requestBody.put("account", account);

		// 生成一个时间戳参数
		String rtick = RSAUtils.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtils.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, null,
				requestBody.toJSONString());
		// 签名参数追加为url参数
		String urlParams = String.format(urlSignParams, this.developerId,
				rtick, paramsSign);
		// 发送请求
		String responseBody = HttpClientSender.sendHttpPost(host, method,
				urlParams, requestBody.toJSONString());
		// 返回结果解析
		JSONObject userObj = JSON.parseObject(responseBody);
		// 返回errno为0，表示成功，其他表示失败
		if (userObj.getIntValue("errno") == 0) {
			JSONObject data = userObj.getJSONObject("data");
			if (data != null) {
				return data.getString("certId");
			}
			return null;
		} else {
			throw new Exception(userObj.getIntValue("errno") + ":"
					+ userObj.getString("errmsg"));
		}
	}

	/**
	 * 3.1 生成用户签名印章
	 * 
	 * @param account
	 *            用户账号
	 * @param text
	 *            自定义文字 自定义签字或者印章文字，为空则默认获取注册时填写的用户名称
	 * @return 文件id
	 * @throws Exception
	 */
	public String signatureImageCreate(String account, String text)
			throws Exception {
		String host = this.sdkHost;
		String method = "/storage/signatureImage/user/create/";

		// 组装请求参数，作为requestbody
		JSONObject requestBody = new JSONObject();
		requestBody.put("account", account);
		requestBody.put("text", text);

		// 生成一个时间戳参数
		String rtick = RSAUtils.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtils.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, null,
				requestBody.toJSONString());
		// 签名参数追加为url参数
		String urlParams = String.format(urlSignParams, this.developerId,
				rtick, paramsSign);
		// 发送请求
		String responseBody = HttpClientSender.sendHttpPost(host, method,
				urlParams, requestBody.toJSONString());
		// 返回结果解析
		JSONObject userObj = JSON.parseObject(responseBody);
		// 返回errno为0，表示成功，其他表示失败
		if (userObj.getIntValue("errno") == 0) {
			return "success";
		} else {
			throw new Exception(userObj.getIntValue("errno") + ":"
					+ userObj.getString("errmsg"));
		}
	}

	/**
	 * 3.2 上传用户签名图片
	 * 
	 * @param account
	 *            用户账号
	 * @param imageData
	 *            图片base64字符串
	 * @return 文件id
	 * @throws Exception
	 */
	public String signatureImageUpload(String account, String imageName,
			String imageData) throws Exception {
		String host = this.sdkHost;
		String method = "/storage/signatureImage/user/upload/";

		// 组装请求参数，作为requestbody
		JSONObject requestBody = new JSONObject();
		requestBody.put("account", account);
		requestBody.put("imageName", imageName);
		requestBody.put("imageData", imageData);

		// 生成一个时间戳参数
		String rtick = RSAUtils.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtils.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, null,
				requestBody.toJSONString());
		// 签名参数追加为url参数
		String urlParams = String.format(urlSignParams, this.developerId,
				rtick, paramsSign);
		// 发送请求
		String responseBody = HttpClientSender.sendHttpPost(host, method,
				urlParams, requestBody.toJSONString());
		// 返回结果解析
		JSONObject userObj = JSON.parseObject(responseBody);
		// 返回errno为0，表示成功，其他表示失败
		if (userObj.getIntValue("errno") == 0) {
			return "success";
		} else {
			throw new Exception(userObj.getIntValue("errno") + ":"
					+ userObj.getString("errmsg"));
		}
	}

	/**
	 * 4 上传合同(包含基础接口的上传文件和创建合同) 2.9.0版本新增
	 * 
	 * @param account
	 *            用户账号
	 * @param fid
	 *            文件ID 上传文件后产生的文件ID（fid）
	 * @param expireTime
	 *            合同有效期，单位：秒；计算方法：当前系统时间的时间戳秒数+有效期秒数
	 * @param title
	 *            合同标题
	 * @param description
	 *            合同内容描述
	 * @return 合同id
	 * @throws Exception
	 */
	public String contractUpload(String account, String fmd5, String ftype,
			String fname, String fpages, String fdata, String expireTime,
			String title, String description) throws Exception {
		String host = this.sdkHost;
		String method = "/storage/contract/upload/";

		// 组装请求参数，作为requestbody
		JSONObject requestBody = new JSONObject();
		requestBody.put("account", account);
		requestBody.put("fmd5", fmd5);
		requestBody.put("ftype", ftype);
		requestBody.put("fname", fname);
		requestBody.put("fpages", fpages);
		requestBody.put("fdata", fdata);
		requestBody.put("title", title);
		requestBody.put("description", description);
		requestBody.put("expireTime", expireTime);

		// 生成一个时间戳参数
		String rtick = RSAUtils.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtils.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, null,
				requestBody.toJSONString());
		// 签名参数追加为url参数
		String urlParams = String.format(urlSignParams, this.developerId,
				rtick, paramsSign);
		// 发送请求
		String responseBody = HttpClientSender.sendHttpPost(host, method,
				urlParams, requestBody.toJSONString());
		// 返回结果解析
		JSONObject userObj = JSON.parseObject(responseBody);
		// 返回errno为0，表示成功，其他表示失败
		if (userObj.getIntValue("errno") == 0) {
			JSONObject data = userObj.getJSONObject("data");
			if (data != null) {
				return data.getString("contractId");
			}
			return null;
		} else {
			throw new Exception(userObj.getIntValue("errno") + ":"
					+ userObj.getString("errmsg"));
		}
	}

	/**
	 * 5.1 合同签署(用于自动签署)
	 * 
	 * @param contractId
	 *            合同ID
	 * @param signer
	 *            签署者账号
	 * @param signatureImageData
	 *            签名图片
	 * @param signaturePositions
	 *            签名位置数组，如果在5.5设置了签名位置，此参数可以为空
	 * @return
	 * @throws Exception
	 */
	public String contractSignCert(String contractId, String signer,
			String signatureImageData, JSONArray signaturePositions)
			throws Exception {
		String host = this.sdkHost;
		String method = "/storage/contract/sign/cert/";

		// 组装请求参数，作为requestbody
		JSONObject requestBody = new JSONObject();
		requestBody.put("contractId", contractId);
		requestBody.put("signer", signer);
		requestBody.put("signatureImageData", signatureImageData);
		requestBody.put("signaturePositions", signaturePositions);

		// 生成一个时间戳参数
		String rtick = RSAUtils.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtils.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, null,
				requestBody.toJSONString());
		// 签名参数追加为url参数
		String urlParams = String.format(urlSignParams, this.developerId,
				rtick, paramsSign);
		// 发送请求
		String responseBody = HttpClientSender.sendHttpPost(host, method,
				urlParams, requestBody.toJSONString());
		// 返回结果解析
		JSONObject userObj = JSON.parseObject(responseBody);
		System.out.println(userObj.toJSONString());
		// 返回errno为0，表示成功，其他表示失败
		if (userObj.getIntValue("errno") == 0) {
			return "success";
		} else {
			throw new Exception(userObj.getIntValue("errno") + ":"
					+ userObj.getString("errmsg"));
		}
	}

	/**
	 * 5.13 发送合同（用于手动签署）
	 * 
	 * @param contractId
	 *            合同ID
	 * @param account
	 *            需要手动签署的用户账号
	 * @param signaturePositions
	 *            指定的默认签署位置，json array格式，每一项为一个json对象。 x,y坐标使用百分比，取值0.0 -
	 *            1.0。如果不指定此参数，表示由签署者自行在页面上选一个签署位置
	 * @param returnUrl
	 *            手动签署时，当用户签署完成后，指定回跳的页面地址
	 * @param vcodeMobile
	 *            手动签署时指定接收手机验证码的手机号，如果需要采用手动签署页面，则此项必填
	 * @param expireTime
	 *            预览链接的过期时间，unix时间戳。超过此时间则无法签署合同，需要获取新的签署合同url
	 * @param dpi
	 *            预览图片清晰度，枚举值：70-低清（默认），90-普清，120-高清，160-超清
	 * @return url
	 * @throws Exception
	 */
	public String contractSend(String contractId, String account,
			JSONArray signaturePositions, String returnUrl, String vcodeMobile,
			String expireTime, String dpi) throws Exception {
		String host = this.serverHost;
		String method = "/contract/send/";

		// 组装请求参数，作为requestbody
		JSONObject requestBody = new JSONObject();
		requestBody.put("contractId", contractId);
		requestBody.put("signer", account);
		requestBody.put("signaturePositions", signaturePositions);
		requestBody.put("returnUrl", returnUrl);
		requestBody.put("vcodeMobile", vcodeMobile);
		requestBody.put("expireTime", expireTime);
		requestBody.put("quality", "100");
		requestBody.put("dpi", dpi);

		// 生成一个时间戳参数
		String rtick = RSAUtils.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtils.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, null,
				requestBody.toJSONString());
		// 签名参数追加为url参数
		String urlParams = String.format(urlSignParams, this.developerId,
				rtick, paramsSign);
		// 发送请求
		String responseBody = HttpClientSender.sendHttpPost(host, method,
				urlParams, requestBody.toJSONString());
		// 返回结果解析
		JSONObject userObj = JSON.parseObject(responseBody);
		// 返回errno为0，表示成功，其他表示失败
		if (userObj.getIntValue("errno") == 0) {
			JSONObject data = userObj.getJSONObject("data");
			if (data != null) {
				return data.getString("url");
			}
			return null;
		} else {
			throw new Exception(userObj.getIntValue("errno") + ":"
					+ userObj.getString("errmsg"));
		}
	}

	/**
	 * 5.21 锁定结束合同
	 * 
	 * @param contractId
	 *            合同ID
	 * @return 文件id
	 * @throws Exception
	 */
	public String contractLockFinish(String contractId) throws Exception {
		String host = this.sdkHost;
		String method = "/storage/contract/lock/";

		// 组装请求参数，作为requestbody
		JSONObject requestBody = new JSONObject();
		requestBody.put("contractId", contractId);

		// 生成一个时间戳参数
		String rtick = RSAUtils.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtils.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, null,
				requestBody.toJSONString());
		// 签名参数追加为url参数
		String urlParams = String.format(urlSignParams, this.developerId,
				rtick, paramsSign);
		// 发送请求
		String responseBody = HttpClientSender.sendHttpPost(host, method,
				urlParams, requestBody.toJSONString());
		// 返回结果解析
		JSONObject userObj = JSON.parseObject(responseBody);
		// 返回errno为0，表示成功，其他表示失败
		if (userObj.getIntValue("errno") == 0) {
			JSONObject data = userObj.getJSONObject("data");
			if (data != null) {
				return data.getString("fid"); // 文件id，公有云API无此参数；混合云SDK返回该参数
			}
			return null;
		} else {
			throw new Exception(userObj.getIntValue("errno") + ":"
					+ userObj.getString("errmsg"));
		}
	}

	// *********************************常规API接口end****************************************

	// *********************************特殊API接口start****************************************
	/**
	 * 1.2 用户注册
	 * 
	 * @param account
	 *            用户账号
	 * @param name
	 *            用户名称
	 * @param userType
	 *            用户类型 1-个人 2-企业
	 * @param mail
	 *            用来接收通知邮件的电子邮箱
	 * @param mobile
	 *            用来接收通知短信的手机号码
	 * @return 是否成功
	 * @throws IOException
	 */
	public int userReg(String account, String name, String userType,
			String mail, String mobile) throws Exception {
		String host = this.serverHost;
		String method = "/user/reg/";

		// 组装请求参数，作为requestbody
		JSONObject requestBody = new JSONObject();
		requestBody.put("account", account);
		requestBody.put("name", name);
		requestBody.put("userType", userType);
		requestBody.put("mail", mail);
		requestBody.put("mobile", mobile);

		// 生成一个时间戳参数
		String rtick = RSAUtils.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtils.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, null,
				requestBody.toJSONString());
		// 签名参数追加为url参数
		String urlParams = String.format(urlSignParams, this.developerId,
				rtick, paramsSign);
		// 发送请求
		String responseBody = HttpClientSender.sendHttpPost(host, method,
				urlParams, requestBody.toJSONString());
		// 返回结果解析
		JSONObject userObj = JSON.parseObject(responseBody);
		// 返回errno为0，表示成功，其他表示失败
		if (userObj.getIntValue("errno") == 0) {
			return 0;
		} else {
			throw new Exception(userObj.getIntValue("errno") + ":"
					+ userObj.getString("errmsg"));
		}
	}

	/**
	 * 1.3 设置个人实名信息
	 * 
	 * @param account
	 *            用户注册账号
	 * @param name
	 *            姓名
	 * @param identity
	 *            证件号码
	 * @param identityType
	 *            枚举值：0-身份证，目前仅支持身份证
	 * @param contactMail
	 *            电子邮箱
	 * @param contactMobile
	 *            手机号码
	 * @param province
	 *            省份
	 * @param city
	 *            城市
	 * @param address
	 *            地址
	 * @return
	 * @throws Exception
	 */
	public int setPersonalCredential(String account, String name,
			String identity, String identityType, String contactMail,
			String contactMobile, String province, String city, String address)
			throws Exception {
		String host = this.serverHost;
		String method = "/user/setPersonalCredential/";

		// 组装请求参数，作为requestbody
		JSONObject requestBody = new JSONObject();
		requestBody.put("account", account);
		requestBody.put("name", name);
		requestBody.put("identity", identity);
		requestBody.put("identityType", identityType);
		requestBody.put("contactMail", contactMail);
		requestBody.put("contactMobile", contactMobile);
		requestBody.put("province", province);
		requestBody.put("city", city);
		requestBody.put("address", address);

		// 生成一个时间戳参数
		String rtick = RSAUtils.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtils.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, null,
				requestBody.toJSONString());
		// 签名参数追加为url参数
		String urlParams = String.format(urlSignParams, this.developerId,
				rtick, paramsSign);
		// 发送请求
		String responseBody = HttpClientSender.sendHttpPost(host, method,
				urlParams, requestBody.toJSONString());
		// 返回结果解析
		JSONObject userObj = JSON.parseObject(responseBody);
		// 返回errno为0，表示成功，其他表示失败
		if (userObj.getIntValue("errno") == 0) {
			return 0;
		} else {
			throw new Exception(userObj.getIntValue("errno") + ":"
					+ userObj.getString("errmsg"));
		}
	}

	/**
	 * 1.5 设置企业实名信息
	 * 
	 * @param account
	 *            用户账号
	 * @param regCode
	 *            营业执照代码或者统一社会信用代码
	 * @param taxCode
	 *            税务登记证代码 如果是三证合一，此项填统一社会信用代码
	 * @param orgCode
	 *            组织机构代码 如果是三证合一，此项填统一社会信用代码
	 * @param name
	 *            企业名称
	 * @param legalPerson
	 *            法人代表姓名
	 * @param legalPersonIdentity
	 *            法人代表证件号码
	 * @param legalPersonIdentityType
	 *            法人代表证件类型
	 * @param legalPersonMobile
	 *            法人代表手机号码
	 * @param contactMail
	 *            联系邮箱
	 * @param contactMobile
	 *            联系手机号码
	 * @param province
	 *            所在省份
	 * @param city
	 *            所在城市
	 * @param address
	 *            公司地址
	 * @return
	 * @throws Exception
	 */
	public int setEnterpriseCredential(String account, String regCode,
			String taxCode, String orgCode, String name, String legalPerson,
			String legalPersonIdentity, String legalPersonIdentityType,
			String legalPersonMobile, String contactMail, String contactMobile,
			String province, String city, String address) throws Exception {
		String host = this.serverHost;
		String method = "/user/setEnterpriseCredential/";

		// 组装请求参数，作为requestbody
		JSONObject requestBody = new JSONObject();
		requestBody.put("account", account);
		requestBody.put("regCode", regCode);
		requestBody.put("taxCode", taxCode);
		requestBody.put("orgCode", orgCode);
		requestBody.put("name", name);
		requestBody.put("legalPerson", legalPerson);
		requestBody.put("legalPersonIdentity", legalPersonIdentity);
		requestBody.put("legalPersonIdentityType", legalPersonIdentityType);
		requestBody.put("legalPersonMobile", legalPersonMobile);
		requestBody.put("contactMail", contactMail);
		requestBody.put("contactMobile", contactMobile);
		requestBody.put("province", province);
		requestBody.put("city", city);
		requestBody.put("address", address);

		// 生成一个时间戳参数
		String rtick = RSAUtils.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtils.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, null,
				requestBody.toJSONString());
		// 签名参数追加为url参数
		String urlParams = String.format(urlSignParams, this.developerId,
				rtick, paramsSign);
		// 发送请求
		String responseBody = HttpClientSender.sendHttpPost(host, method,
				urlParams, requestBody.toJSONString());
		// 返回结果解析
		JSONObject userObj = JSON.parseObject(responseBody);
		// 返回errno为0，表示成功，其他表示失败
		if (userObj.getIntValue("errno") == 0) {
			return 0;
		} else {
			throw new Exception(userObj.getIntValue("errno") + ":"
					+ userObj.getString("errmsg"));
		}
	}

	/**
	 * 1.9 同步申请CA证书
	 * 
	 * @param account
	 *            用户账号
	 * @param certType
	 *            证书类型，目前支持CFCA和ZJCA，可为空，由系统自动判断
	 * @return
	 * @throws Exception
	 */
	public String syncApplyCert(String account, String certType)
			throws Exception {
		String host = this.serverHost;
		String method = "/user/applyCert/";

		// 组装请求参数，作为requestbody
		JSONObject requestBody = new JSONObject();
		requestBody.put("account", account);
		requestBody.put("certType", certType);

		// 生成一个时间戳参数
		String rtick = RSAUtils.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtils.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, null,
				requestBody.toJSONString());
		// 签名参数追加为url参数
		String urlParams = String.format(urlSignParams, this.developerId,
				rtick, paramsSign);
		// 发送请求
		String responseBody = HttpClientSender.sendHttpPost(host, method,
				urlParams, requestBody.toJSONString());
		// 返回结果解析
		JSONObject userObj = JSON.parseObject(responseBody);
		// 返回errno为0，表示成功，其他表示失败
		if (userObj.getIntValue("errno") == 0) {
			JSONObject data = userObj.getJSONObject("data");
			if (data != null) {
				// 返回taskId
				return data.getString("certId");
			}
			return null;
		} else {
			throw new Exception(userObj.getIntValue("errno") + ":"
					+ userObj.getString("errmsg"));
		}
	}

	/**
	 * 2.3 下载用户默认签名
	 * 
	 * @param account
	 *            用户账号
	 * @param imageName
	 *            签名图片名称 传空或default为默认签名图片。 企业用户传自定义印章名称则下载指定的图片。
	 * @return
	 * @throws Exception
	 */
	public byte[] signatureImageDownload(String account, String imageName)
			throws Exception {
		String host = this.sdkHost;
		String method = "/storage/signatureImage/user/download/";

		// 组装url参数
		String urlParams = "account=" + account + "&imageName=" + imageName;

		// 生成一个时间戳参数
		String rtick = RSAUtils.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtils.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, urlParams, null);
		// 签名参数追加为url参数
		urlParams = String.format(urlSignParams, this.developerId, rtick,
				paramsSign) + "&" + urlParams;
		// 发送请求
		byte[] responseBody = HttpClientSender.sendHttpGet(host, method,
				urlParams);
		// 返回结果解析
		return responseBody;
	}

	/**
	 * 4.1 上传文件
	 * 
	 * @param account
	 *            文件归属者账号
	 * @param fmd5
	 *            文件MD5值，上传前需获取文件base64的md5
	 * @param ftype
	 *            文件类型 默认pdf
	 * @param fname
	 *            完整文件名称
	 * @param fpages
	 *            文件的页数
	 * @param fdata
	 *            文件内容base64字符串
	 * @return 文件id
	 * @throws Exception
	 */
	public String storageUpload(String account, String fmd5, String ftype,
			String fname, String fpages, String fdata) throws Exception {
		String host = this.sdkHost;
		String method = "/storage/upload/";

		// 组装请求参数，作为requestbody
		JSONObject requestBody = new JSONObject();
		requestBody.put("account", account);
		requestBody.put("fmd5", fmd5);
		requestBody.put("ftype", ftype);
		requestBody.put("fname", fname);
		requestBody.put("fpages", fpages);
		requestBody.put("fdata", fdata);

		// 生成一个时间戳参数
		String rtick = RSAUtils.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtils.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, null,
				requestBody.toJSONString());
		// 签名参数追加为url参数
		String urlParams = String.format(urlSignParams, this.developerId,
				rtick, paramsSign);
		// 发送请求
		String responseBody = HttpClientSender.sendHttpPost(host, method,
				urlParams, requestBody.toJSONString());
		// 返回结果解析
		JSONObject userObj = JSON.parseObject(responseBody);
		// 返回errno为0，表示成功，其他表示失败
		if (userObj.getIntValue("errno") == 0) {
			JSONObject data = userObj.getJSONObject("data");
			System.out.println(userObj.toJSONString());
			if (data != null) {
				return data.getString("fid"); // 文件id，公有云API无此参数；混合云SDK返回该参数
			}
			return null;
		} else {
			throw new Exception(userObj.getIntValue("errno") + ":"
					+ userObj.getString("errmsg"));
		}
	}

	/**
	 * 4.2 文件下载
	 * 
	 * @param fid
	 *            上传文件产生的fid
	 * @return 文件流
	 * @throws Exception
	 */
	public byte[] storageDownload(String fid) throws Exception {
		String host = this.sdkHost;
		String method = "/storage/download/";

		// 组装url参数
		String urlParams = "fid=" + fid;

		// 生成一个时间戳参数
		String rtick = RSAUtils.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtils.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, urlParams, null);
		// 签名参数追加为url参数
		urlParams = String.format(urlSignParams, this.developerId, rtick,
				paramsSign) + "&" + urlParams;
		// 发送请求
		byte[] responseBody = HttpClientSender.sendHttpGet(host, method,
				urlParams);
		// 返回结果解析
		return responseBody;
	}

	/**
	 * 5.1 创建合同
	 * 
	 * @param account
	 *            用户账号
	 * @param fid
	 *            文件ID 上传文件后产生的文件ID（fid）
	 * @param expireTime
	 *            合同有效期，单位：秒；计算方法：当前系统时间的时间戳秒数+有效期秒数
	 * @param title
	 *            合同标题
	 * @param description
	 *            合同内容描述
	 * @return 合同id
	 * @throws Exception
	 */
	public String contractCreate(String account, String fid, String expireTime,
			String title, String description) throws Exception {
		String host = this.serverHost;
		String method = "/contract/create/";

		// 组装请求参数，作为requestbody
		JSONObject requestBody = new JSONObject();
		requestBody.put("account", account);
		requestBody.put("fid", fid);
		requestBody.put("expireTime", expireTime);
		requestBody.put("title", title);
		requestBody.put("description", description);

		// 生成一个时间戳参数
		String rtick = RSAUtils.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtils.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, null,
				requestBody.toJSONString());
		// 签名参数追加为url参数
		String urlParams = String.format(urlSignParams, this.developerId,
				rtick, paramsSign);
		// 发送请求
		String responseBody = HttpClientSender.sendHttpPost(host, method,
				urlParams, requestBody.toJSONString());
		// 返回结果解析
		JSONObject userObj = JSON.parseObject(responseBody);
		// 返回errno为0，表示成功，其他表示失败
		if (userObj.getIntValue("errno") == 0) {
			JSONObject data = userObj.getJSONObject("data");
			if (data != null) {
				return data.getString("contractId"); // 文件id，公有云API无此参数；混合云SDK返回该参数
			}
			return null;
		} else {
			throw new Exception(userObj.getIntValue("errno") + ":"
					+ userObj.getString("errmsg"));
		}
	}

	/**
	 * 5.2 添加单个签署人
	 * 
	 * @param contractId
	 *            合同ID
	 * @param signer
	 *            签署人用户账号，必须先成功注册账号
	 * @return 0-成功
	 * @throws Exception
	 */
	public int contractAddSigner(String contractId, String signer)
			throws Exception {
		String host = this.serverHost;
		String method = "/contract/addSigner/";

		// 组装请求参数，作为requestbody
		JSONObject requestBody = new JSONObject();
		requestBody.put("contractId", contractId);
		requestBody.put("signer", signer);

		// 生成一个时间戳参数
		String rtick = RSAUtils.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtils.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, null,
				requestBody.toJSONString());
		// 签名参数追加为url参数
		String urlParams = String.format(urlSignParams, this.developerId,
				rtick, paramsSign);
		// 发送请求
		String responseBody = HttpClientSender.sendHttpPost(host, method,
				urlParams, requestBody.toJSONString());
		// 返回结果解析
		JSONObject userObj = JSON.parseObject(responseBody);
		// 返回errno为0，表示成功，其他表示失败
		if (userObj.getIntValue("errno") == 0) {
			return 0;
		} else {
			throw new Exception(userObj.getIntValue("errno") + ":"
					+ userObj.getString("errmsg"));
		}
	}

	/**
	 * 5.5 设置合同签署参数
	 * 
	 * @param contractId
	 *            合同编号
	 * @param account
	 *            签署者用户账号
	 * @param signaturePositions
	 *            指定的默认签署位置，json array格式，每一项为一个json对象。 x,y坐标使用百分比，取值0.0 -
	 *            1.0。如果不指定此参数，表示由签署者自行在页面上选一个签署位置
	 * @param isAllowChangeSignaturePosition
	 *            在有指定signaturePOSTion参数的情况下，是否允许拖动签名位置。取值1/0。（0：不允许，1：允许，
	 *            下面都是这样的）。默认值：1
	 * @param returnUrl
	 *            手动签署时，当用户签署完成后，指定回跳的页面地址
	 * @param vcodeMobile
	 *            手动签署时指定接收手机验证码的手机号，如果需要采用手动签署页面，则此项必填
	 * @param isDrawSignatureImage
	 *            手动签署时是否手绘签名。取值0/1。 0手动签署时签名图片点击不触发签名面板。 1手动签署时可以点击签名图片触发手绘签名
	 * @return
	 * @throws Exception
	 */
	public int setSignerConfig(String contractId, String account,
			JSONArray signaturePositions, String returnUrl, String vcodeMobile)
			throws Exception {
		String host = this.serverHost;
		String method = "/contract/setSignerConfig/";

		// 组装请求参数，作为requestbody
		JSONObject requestBody = new JSONObject();
		requestBody.put("contractId", contractId);
		requestBody.put("account", account);
		requestBody.put("signaturePositions", signaturePositions);
		requestBody.put("isAllowChangeSignaturePosition", "");
		requestBody.put("returnUrl", returnUrl);
		requestBody.put("password", "");
		requestBody.put("isVerifySigner", "0");
		requestBody.put("vcodeMail", "");
		requestBody.put("vcodeMobile", vcodeMobile);
		requestBody.put("isDrawSignatureImage", "");
		requestBody.put("signatureImageName", "default"); // 使用默认值
		requestBody.put("certType", ""); // 系统自动判断
		requestBody.put("app", "");

		// 生成一个时间戳参数
		String rtick = RSAUtils.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtils.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, null,
				requestBody.toJSONString());
		// 签名参数追加为url参数
		String urlParams = String.format(urlSignParams, this.developerId,
				rtick, paramsSign);
		// 发送请求
		String responseBody = HttpClientSender.sendHttpPost(host, method,
				urlParams, requestBody.toJSONString());
		// 返回结果解析
		JSONObject userObj = JSON.parseObject(responseBody);
		// 返回errno为0，表示成功，其他表示失败
		if (userObj.getIntValue("errno") == 0) {
			return 0;
		} else {
			throw new Exception(userObj.getIntValue("errno") + ":"
					+ userObj.getString("errmsg"));
		}
	}

	/**
	 * 5.13 获取手动签署URL
	 * 
	 * @param contractId
	 *            合同ID
	 * @param account
	 *            需要手动签署的用户账号
	 * @param expireTime
	 *            预览链接的过期时间，unix时间戳。超过此时间则无法签署合同，需要获取新的签署合同url
	 * @param dpi
	 *            预览图片清晰度，枚举值：70-低清（默认），90-普清，120-高清，160-超清
	 * @return url
	 * @throws Exception
	 */
	public String getSignURL(String contractId, String account,
			String expireTime, String dpi) throws Exception {
		String host = this.serverHost;
		String method = "/contract/getSignURL/";

		// 组装请求参数，作为requestbody
		JSONObject requestBody = new JSONObject();
		requestBody.put("contractId", contractId);
		requestBody.put("account", account);
		requestBody.put("expireTime", expireTime);
		requestBody.put("quality", "100");
		requestBody.put("dpi", dpi);

		// 生成一个时间戳参数
		String rtick = RSAUtils.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtils.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, null,
				requestBody.toJSONString());
		// 签名参数追加为url参数
		String urlParams = String.format(urlSignParams, this.developerId,
				rtick, paramsSign);
		// 发送请求
		String responseBody = HttpClientSender.sendHttpPost(host, method,
				urlParams, requestBody.toJSONString());
		// 返回结果解析
		JSONObject userObj = JSON.parseObject(responseBody);
		// 返回errno为0，表示成功，其他表示失败
		if (userObj.getIntValue("errno") == 0) {
			JSONObject data = userObj.getJSONObject("data");
			if (data != null) {
				return data.getString("url");
			}
			return null;
		} else {
			throw new Exception(userObj.getIntValue("errno") + ":"
					+ userObj.getString("errmsg"));
		}
	}

	/**
	 * 5.10 拒绝签署
	 * 
	 * @param contractId
	 *            合同ID
	 * @param signer
	 *            签署者用户账号
	 * @param refuseReason
	 *            填写取消或者拒绝签署合同的原因
	 * @return 0-成功
	 * @throws Exception
	 */
	public int contractRefuse(String contractId, String signer,
			String refuseReason) throws Exception {
		String host = this.serverHost;
		String method = "/contract/refuse/";

		// 组装请求参数，作为requestbody
		JSONObject requestBody = new JSONObject();
		requestBody.put("contractId", contractId);
		requestBody.put("signer", signer);
		requestBody.put("refuseReason", refuseReason);

		// 生成一个时间戳参数
		String rtick = RSAUtils.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtils.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, null,
				requestBody.toJSONString());
		// 签名参数追加为url参数
		String urlParams = String.format(urlSignParams, this.developerId,
				rtick, paramsSign);
		// 发送请求
		String responseBody = HttpClientSender.sendHttpPost(host, method,
				urlParams, requestBody.toJSONString());
		// 返回结果解析
		JSONObject userObj = JSON.parseObject(responseBody);
		// 返回errno为0，表示成功，其他表示失败
		if (userObj.getIntValue("errno") == 0) {
			return 0;
		} else {
			throw new Exception(userObj.getIntValue("errno") + ":"
					+ userObj.getString("errmsg"));
		}
	}

	/**
	 * 5.4 查询合同信息
	 * 
	 * @param contractId
	 *            合同ID
	 * @return 文件id
	 * @throws Exception
	 */
	public JSONObject contractGetInfo(String contractId) throws Exception {
		String host = this.serverHost;
		String method = "/contract/getInfo/";

		// 组装请求参数，作为requestbody
		JSONObject requestBody = new JSONObject();
		requestBody.put("contractId", contractId);

		// 生成一个时间戳参数
		String rtick = RSAUtils.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtils.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, null,
				requestBody.toJSONString());
		// 签名参数追加为url参数
		String urlParams = String.format(urlSignParams, this.developerId,
				rtick, paramsSign);
		// 发送请求
		String responseBody = HttpClientSender.sendHttpPost(host, method,
				urlParams, requestBody.toJSONString());
		// 返回结果解析
		JSONObject userObj = JSON.parseObject(responseBody);
		// 返回errno为0，表示成功，其他表示失败
		if (userObj.getIntValue("errno") == 0) {
			JSONObject data = userObj.getJSONObject("data");
			return data;
		} else {
			throw new Exception(userObj.getIntValue("errno") + ":"
					+ userObj.getString("errmsg"));
		}
	}

	/**
	 * 5.14 获取合同预览URL
	 * 
	 * @param contractId
	 *            合同ID
	 * @param account
	 *            合同签署者的账号
	 * @param expireTime
	 *            预览链接的过期时间，unix时间戳。超过此时间则无法预览合同，需要获取新的预览合同url
	 * @param dpi
	 *            预览图片清晰度，枚举值：70-低清（默认），90-普清，120-高清，160-超清
	 * @return url
	 * @throws Exception
	 */
	public String getPreviewURL(String contractId, String account,
			String expireTime, String dpi) throws Exception {
		String host = this.serverHost;
		String method = "/contract/getPreviewURL/";

		// 组装请求参数，作为requestbody
		JSONObject requestBody = new JSONObject();
		requestBody.put("contractId", contractId);
		requestBody.put("account", account);
		requestBody.put("expireTime", expireTime);
		requestBody.put("quality", "100");
		requestBody.put("dpi", dpi);

		// 生成一个时间戳参数
		String rtick = RSAUtils.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtils.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, null,
				requestBody.toJSONString());
		// 签名参数追加为url参数
		String urlParams = String.format(urlSignParams, this.developerId,
				rtick, paramsSign);
		// 发送请求
		String responseBody = HttpClientSender.sendHttpPost(host, method,
				urlParams, requestBody.toJSONString());
		// 返回结果解析
		JSONObject userObj = JSON.parseObject(responseBody);
		// 返回errno为0，表示成功，其他表示失败
		if (userObj.getIntValue("errno") == 0) {
			JSONObject data = userObj.getJSONObject("data");
			if (data != null) {
				return data.getString("url");
			}
			return null;
		} else {
			throw new Exception(userObj.getIntValue("errno") + ":"
					+ userObj.getString("errmsg"));
		}
	}

	/**
	 * 5.16 下载合同PDF文件
	 * 
	 * @param contractId
	 *            合同编号
	 * @return
	 * @throws Exception
	 */
	public byte[] contractDownload(String contractId) throws Exception {
		String host = this.sdkHost;
		String method = "/storage/contract/download/";

		// 组装url参数
		String urlParams = "contractId=" + contractId;

		// 生成一个时间戳参数
		String rtick = RSAUtils.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtils.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, urlParams, null);
		// 签名参数追加为url参数
		urlParams = String.format(urlSignParams, this.developerId, rtick,
				paramsSign) + "&" + urlParams;
		// 发送请求
		byte[] responseBody = HttpClientSender.sendHttpGet(host, method,
				urlParams);
		// 返回结果解析
		return responseBody;
	}

	/**
	 * 1.1 用户查询
	 * 
	 * @param account
	 *            用户账号
	 * @throws IOException
	 */
	public JSONObject userBaseInfo(String account) throws Exception {
		String host = this.serverHost;
		String method = "/user/baseInfo/"; // 请求方法名

		// 组装请求参数，作为requestbody
		JSONObject requestBody = new JSONObject();
		requestBody.put("account", account);

		// 生成一个时间戳参数
		String rtick = RSAUtils.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtils.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, null,
				requestBody.toJSONString());
		// 签名参数追加为url参数
		String urlParams = String.format(urlSignParams, this.developerId,
				rtick, paramsSign);
		// 发送请求
		String responseBody = HttpClientSender.sendHttpPost(host, method,
				urlParams, requestBody.toJSONString());
		// 返回结果解析
		JSONObject userObj = JSON.parseObject(responseBody);
		// 返回errno为0，表示成功，其他表示失败
		if (userObj.getIntValue("errno") == 0) {
			JSONObject data = userObj.getJSONObject("data");
			return data;
		} else {
			throw new Exception(userObj.getIntValue("errno") + ":"
					+ userObj.getString("errmsg"));
		}
	}

	/**
	 * pdf文件添加图片或者文字元素
	 * 
	 * @param account
	 *            用户账号
	 * @param fid
	 *            文件id
	 * @param elements
	 *            pdf元素列表，格式如下： [ { "pageNum": "1", "x": "0.1", "y": "0.1",
	 *            "type": "text", "value": "我是测试文本内容", "fontSize": "14" } ]
	 * @return
	 * @throws Exception
	 */
	public String storageAddPDFElements(String account, String fid,
			JSONArray elements) throws Exception {
		String host = this.sdkHost;
		String method = "/storage/addPdfElements/";

		// 组装请求参数，作为requestbody
		JSONObject requestBody = new JSONObject();
		requestBody.put("account", account);
		requestBody.put("fid", fid);
		requestBody.put("elements", elements);

		// 生成一个时间戳参数
		String rtick = RSAUtils.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtils.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, null,
				requestBody.toJSONString());
		// 签名参数追加为url参数
		String urlParams = String.format(urlSignParams, this.developerId,
				rtick, paramsSign);
		// 发送请求
		String responseBody = HttpClientSender.sendHttpPost(host, method,
				urlParams, requestBody.toJSONString());
		// 返回结果解析
		JSONObject userObj = JSON.parseObject(responseBody);
		// 返回errno为0，表示成功，其他表示失败
		if (userObj.getIntValue("errno") == 0) {
			JSONObject data = userObj.getJSONObject("data");
			if (data != null) {
				return data.getString("fid"); // 文件id，公有云API无此参数；混合云SDK返回该参数
			}
			return null;
		} else {
			throw new Exception(userObj.getIntValue("errno") + ":"
					+ userObj.getString("errmsg"));
		}
	}

	public JSONObject getSignerConfig(String contractId, String account)
			throws Exception {
		String host = this.serverHost;
		String method = "/contract/getSignerConfig/";

		// 组装请求参数，作为requestbody
		JSONObject requestBody = new JSONObject();
		requestBody.put("contractId", contractId);
		requestBody.put("account", account);

		// 生成一个时间戳参数
		String rtick = RSAUtils.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtils.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, null,
				requestBody.toJSONString());
		// 签名参数追加为url参数
		String urlParams = String.format(urlSignParams, this.developerId,
				rtick, paramsSign);
		// 发送请求
		String responseBody = HttpClientSender.sendHttpPost(host, method,
				urlParams, requestBody.toJSONString());
		// 返回结果解析
		JSONObject userObj = JSON.parseObject(responseBody);
		// 返回errno为0，表示成功，其他表示失败
		if (userObj.getIntValue("errno") == 0) {
			JSONObject data = userObj.getJSONObject("data");
			return data;
		} else {
			throw new Exception(userObj.getIntValue("errno") + ":"
					+ userObj.getString("errmsg"));
		}
	}

	public JSONObject getSignerStatus(String contractId) throws Exception {
		String host = this.serverHost;
		String method = "/contract/getSignerStatus/";

		// 组装请求参数，作为requestbody
		JSONObject requestBody = new JSONObject();
		requestBody.put("contractId", contractId);

		// 生成一个时间戳参数
		String rtick = RSAUtils.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtils.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, null,
				requestBody.toJSONString());
		// 签名参数追加为url参数
		String urlParams = String.format(urlSignParams, this.developerId,
				rtick, paramsSign);
		// 发送请求
		String responseBody = HttpClientSender.sendHttpPost(host, method,
				urlParams, requestBody.toJSONString());
		// 返回结果解析
		JSONObject userObj = JSON.parseObject(responseBody);
		// 返回errno为0，表示成功，其他表示失败
		if (userObj.getIntValue("errno") == 0) {
			JSONObject data = userObj.getJSONObject("data");
			return data;
		} else {
			throw new Exception(userObj.getIntValue("errno") + ":"
					+ userObj.getString("errmsg"));
		}
	}

	public String contractPagingSignCert(String contractId, String signer,
			String signatureImageData, String signerCert, String signY)
			throws Exception {
		String host = this.sdkHost;
		String method = "/contract/cert/paging/seal/sign/";

		// 组装请求参数，作为requestbody
		JSONObject requestBody = new JSONObject();
		requestBody.put("contractId", contractId);
		requestBody.put("signer", signer);
		requestBody.put("signatureImageData", signatureImageData);
		requestBody.put("signerCert", signerCert);
		requestBody.put("signY", signY);

		// 生成一个时间戳参数
		String rtick = RSAUtils.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtils.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, null,
				requestBody.toJSONString());
		// 签名参数追加为url参数
		String urlParams = String.format(urlSignParams, this.developerId,
				rtick, paramsSign);
		// 发送请求
		String responseBody = HttpClientSender.sendHttpPost(host, method,
				urlParams, requestBody.toJSONString());
		// 返回结果解析
		JSONObject userObj = JSON.parseObject(responseBody);
		// 返回errno为0，表示成功，其他表示失败
		if (userObj.getIntValue("errno") == 0) {
			return "success";
		} else {
			throw new Exception(userObj.getIntValue("errno") + ":"
					+ userObj.getString("errmsg"));
		}
	}
	
	/**
     * 发送短信验证码
     * @param contractId
     * @param account
     * @param sendTarget
     * @param sendType
     * @param seconds
     * @return
     * @throws Exception
     */
    public int sendSignVCode(String contractId,
                              String account,
                              String sendTarget,
                             String sendType,
                             String seconds) throws Exception {
        String host = this.serverHost;
        String method = "/contract/sendSignVCode/";

        //组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("contractId", contractId);
        requestBody.put("account", account);
        requestBody.put("sendTarget", sendTarget);
        requestBody.put("sendType", sendType);
        requestBody.put("seconds", seconds);

        //生成一个时间戳参数
        String rtick = RSAUtils.getRtick();
        //计算参数签名
        String paramsSign = RSAUtils.calcRsaSign(this.developerId, this.privateKey, host, method, rtick, null, requestBody.toJSONString());
        //签名参数追加为url参数
        String urlParams = String.format(urlSignParams, this.developerId, rtick, paramsSign);
        //发送请求
        String responseBody = HttpClientSender.sendHttpPost(host, method, urlParams, requestBody.toJSONString());
        //返回结果解析
        JSONObject userObj = JSON.parseObject(responseBody);
        //返回errno为0，表示成功，其他表示失败
        if(userObj.getIntValue("errno") == 0){
            return 0;
        }else{
            throw new Exception(userObj.getIntValue("errno")+":"+userObj.getString("errmsg"));
        }
    }

    /**
     * 验证短信验证码
     * @param contractId
     * @param account
     * @param vcode
     * @return
     * @throws Exception
     */
    public String verifySignVCode(String contractId,
                             String account,
                             String vcode) throws Exception {
        String host = this.serverHost;
        String method = "/contract/verifySignVCode/";

        //组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("contractId", contractId);
        requestBody.put("account", account);
        requestBody.put("vcode", vcode);

        //生成一个时间戳参数
        String rtick = RSAUtils.getRtick();
        //计算参数签名
        String paramsSign = RSAUtils.calcRsaSign(this.developerId, this.privateKey, host, method, rtick, null, requestBody.toJSONString());
        //签名参数追加为url参数
        String urlParams = String.format(urlSignParams, this.developerId, rtick, paramsSign);
        //发送请求
        String responseBody = HttpClientSender.sendHttpPost(host, method, urlParams, requestBody.toJSONString());
        //返回结果解析
        JSONObject userObj = JSON.parseObject(responseBody);
        //返回errno为0，表示成功，其他表示失败
        if(userObj.getIntValue("errno") == 0){
            JSONObject data = userObj.getJSONObject("data");
            if(data != null){
                return data.getString("result");
            }
            return "0";
        }else{
            throw new Exception(userObj.getIntValue("errno")+":"+userObj.getString("errmsg"));
        }
    }

    /**
     * 生成短链接
     * @param longUrl
     * @return
     * @throws Exception
     */
    public String shorturlCreate(String longUrl) throws Exception {
        String host = this.serverHost;
        String method = "/notice/shorturl/create/";

        //组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("longUrl", longUrl);

        //生成一个时间戳参数
        String rtick = RSAUtils.getRtick();
        //计算参数签名
        String paramsSign = RSAUtils.calcRsaSign(this.developerId, this.privateKey, host, method, rtick, null, requestBody.toJSONString());
        //签名参数追加为url参数
        String urlParams = String.format(urlSignParams, this.developerId, rtick, paramsSign);
        //发送请求
        String responseBody = HttpClientSender.sendHttpPost(host, method, urlParams, requestBody.toJSONString());
        //返回结果解析
        JSONObject userObj = JSON.parseObject(responseBody);
        //返回errno为0，表示成功，其他表示失败
        if(userObj.getIntValue("errno") == 0){
            JSONObject data = userObj.getJSONObject("data");
            if(data != null){
                return data.getString("shortUrl");
            }
            return "";
        }else{
            throw new Exception(userObj.getIntValue("errno")+":"+userObj.getString("errmsg"));
        }
    }

    public JSONObject fileMetaSdk(String fid) throws Exception {
        String host = this.serverHost;
        String method = "/file/meta/sdk/";

        //组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("fid", fid);

        //生成一个时间戳参数
        String rtick = RSAUtils.getRtick();
        //计算参数签名
        String paramsSign = RSAUtils.calcRsaSign(this.developerId, this.privateKey, host, method, rtick, null, requestBody.toJSONString());
        //签名参数追加为url参数
        String urlParams = String.format(urlSignParams, this.developerId, rtick, paramsSign);
        //发送请求
        String responseBody = HttpClientSender.sendHttpPost(host, method, urlParams, requestBody.toJSONString());
        //返回结果解析
        JSONObject userObj = JSON.parseObject(responseBody);
        //返回errno为0，表示成功，其他表示失败
        if(userObj.getIntValue("errno") == 0){
            JSONObject data = userObj.getJSONObject("data");
            return data;
        }else{
            throw new Exception(userObj.getIntValue("errno")+":"+userObj.getString("errmsg"));
        }
    }
    
    /**
     * 1.8 异步申请CA证书
     * @param account 用户账号
     * @param certType 证书类型，目前支持CFCA和ZJCA，可为空，由系统自动判断
     * @return
     * @throws Exception
     */
    public String asyncApplyCert(String account,
                              String certType) throws Exception {
        String host = this.serverHost;
        String method = "/user/async/applyCert/submit/";

        //组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("account", account);
        requestBody.put("certType", certType);

        //生成一个时间戳参数
        String rtick = RSAUtils.getRtick();
        //计算参数签名
        String paramsSign = RSAUtils.calcRsaSign(this.developerId, this.privateKey, host, method, rtick, null, requestBody.toJSONString());
        //签名参数追加为url参数
        String urlParams = String.format(urlSignParams, this.developerId, rtick, paramsSign);
        //发送请求
        String responseBody = HttpClientSender.sendHttpPost(host, method, urlParams, requestBody.toJSONString());
        //返回结果解析
        JSONObject userObj = JSON.parseObject(responseBody);
        //返回errno为0，表示成功，其他表示失败
        if(userObj.getIntValue("errno") == 0){
            JSONObject data = userObj.getJSONObject("data");
            if(data != null){
                //返回taskId
                return data.getString("taskId");
            }
            return null;
        }else{
            throw new Exception(userObj.getIntValue("errno")+":"+userObj.getString("errmsg"));
        }
    }

    /**
     * 1.9 异步申请状态查询
     * @param account 用户账号
     * @param taskId 任务单号
     * @return 状态 1-新申请 2-申请中 3-申请超时 4-申请失败 5-申请成功 -1-无效的申请（数据库无此值）
     * @throws Exception
     */
    public String asyncApplyCertStatus(String account, String taskId) throws Exception {
        String host = this.serverHost;
        String method = "/user/async/applyCert/status/";

        //组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("account", account);
        requestBody.put("taskId", taskId);

        //生成一个时间戳参数
        String rtick = RSAUtils.getRtick();
        //计算参数签名
        String paramsSign = RSAUtils.calcRsaSign(this.developerId, this.privateKey, host, method, rtick, null, requestBody.toJSONString());
        //签名参数追加为url参数
        String urlParams = String.format(urlSignParams, this.developerId, rtick, paramsSign);
        //发送请求
        String responseBody = HttpClientSender.sendHttpPost(host, method, urlParams, requestBody.toJSONString());
        //返回结果解析
        JSONObject userObj = JSON.parseObject(responseBody);
        //返回errno为0，表示成功，其他表示失败
        if(userObj.getIntValue("errno") == 0){
            JSONObject data = userObj.getJSONObject("data");
            if(data != null){
                return data.getString("status");
            }
            return null;
        }else{
            throw new Exception(userObj.getIntValue("errno")+":"+userObj.getString("errmsg"));
        }
    }
}
