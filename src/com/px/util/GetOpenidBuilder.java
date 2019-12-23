package com.px.util;

import java.io.IOException;

import net.sf.json.JSONObject;

public class GetOpenidBuilder implements WxPayDataBuilder {

	private String appid, appsecret, code;
	private static String sendUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";
	private StringBuffer reStringBuffer;
	private boolean build = false;

	public static String getSendUrl() {
		return sendUrl;
	}

	public static void setSendUrl(String sendUrl) {
		GetOpenidBuilder.sendUrl = sendUrl;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private void appendParam(String key, String value, boolean isneed) throws LackParamExceptions {

		if (value == null) {
			if (isneed) {
				throw new LackParamExceptions("����" + key + "����Ϊ��");
			} else {
				return;
			}
		}
		this.reStringBuffer.append(key + "=" + value + "&");
	}

	@Override
	public boolean build() throws LackParamExceptions {
		// TODO Auto-generated method stub
		build = false;
		reStringBuffer = new StringBuffer();
		appendParam("appid", appid, true);
		appendParam("secret", appsecret, true);
		appendParam("code", code, true);
		appendParam("grant_type", "authorization_code", true);
		reStringBuffer.deleteCharAt(reStringBuffer.length() - 1);
		build = true;

		return build;
	}

	@Override
	public JSONObject hand() throws LackParamExceptions {
		if (!build) {
			throw new LackParamExceptions("δbuild�ɹ�������ȷ��build�ɹ���������");
		}
		String result = "";
		JSONObject jsonObject = null;
		try {
			// �����ǵ�����
			result = WxPayUtil.sendHttpsRequest(sendUrl, reStringBuffer.toString(), "text/xml", "utf-8", "GET");
			jsonObject = JSONObject.fromObject(result);
		} catch (IOException e) {
			e.printStackTrace();
			// ��������
			throw new LackParamExceptions(e.toString());
		}
		return jsonObject;
	}

}
