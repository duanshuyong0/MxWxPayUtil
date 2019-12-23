package com.px.util;

import java.io.IOException;

import net.sf.json.JSONObject;

public class SendTempleMsgBuilder implements WxPayDataBuilder {

	private String appid, secret;
	private static String sendUrl = "https://api.weixin.qq.com/cgi-bin/token";
	private static String sendUrl2 = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
	private String access_token;
	private JSONObject sendjson; // ����͵�����
	private JSONObject data; // �Զ�������
	private boolean build = false;
	private StringBuffer reStringBuffer;

	/**
	 * 
	 * @param toUserOpenid
	 *            �����˵�openid
	 * @param template_id
	 *            ���͵�ģ��ID
	 */
	public SendTempleMsgBuilder(String toUserOpenid, String template_id) {
		// TODO Auto-generated constructor stub
		sendjson = new JSONObject();
		data = new JSONObject();
		sendjson.put("touser", toUserOpenid);
		sendjson.put("template_id", template_id);
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public static String getSendUrl() {
		return sendUrl;
	}

	public static void setSendUrl(String sendUrl) {
		SendTempleMsgBuilder.sendUrl = sendUrl;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	/**
	 * �û����ģ����Ϣ����תС����
	 * 
	 * @param appid
	 *            ��תС�����APPID
	 * @param pagepath
	 *            ��ת��ҳ��
	 */
	public void toMiniprogram(String appid, String pagepath) {
		JSONObject minidata = new JSONObject();
		minidata.put("appid", appid);
		minidata.put("pagepath", pagepath);
		sendjson.put("miniprogram", minidata);
	}

	/**
	 * �û����ģ����Ϣ����ת��ҳ
	 * 
	 * @param url
	 *            ��ҳ��ַ
	 */
	public void toUrl(String url) {
		sendjson.put("url", url);
	}

	/**
	 * ����Զ������
	 * 
	 * @param name
	 *            �Զ����������
	 * @param color
	 *            ʮ��������ɫֵ
	 * @param value
	 *            ���������
	 */
	public void addCostomData(String name, String color, String value) {
		JSONObject infodata = new JSONObject();
		infodata.put("value", value);
		infodata.put("color", color);
		data.put(name, infodata);
	}

	@Override
	public boolean build() throws LackParamExceptions {
		build = false;
		appendParam("appid", appid, true);
		appendParam("secret", secret, true);
		appendParam("grant_type", "client_credential", true);
		reStringBuffer.deleteCharAt(reStringBuffer.length() - 1);
		build = true;
		return build;
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
	public JSONObject hand() throws LackParamExceptions {
		if (!build) {
			throw new LackParamExceptions("δbuild�ɹ�������ȷ��build�ɹ���������");
		}
		String result = "";
		JSONObject jsonObject = null;
		try {
			if (access_token == null) {
				// ��ȡaccess_token
				result = WxPayUtil.sendHttpsRequest(sendUrl, reStringBuffer.toString(), "text/xml", "utf-8", "GET");
				jsonObject = JSONObject.fromObject(result);
				if (jsonObject.getString("access_token") == null) {
					jsonObject.put("return_code", "FAIL");
					jsonObject.put("return_msg", "��ȡtokenʧ��");
					return jsonObject;
				} else {
					access_token = jsonObject.getString("access_token");
				}
			}
			result = WxPayUtil.sendHttpsRequest(sendUrl2 + access_token, sendjson.toString(), "text/xml", "utf-8",
					"POST");
			jsonObject = JSONObject.fromObject(result);

		} catch (IOException e) {
			e.printStackTrace();
			// ��������
			throw new LackParamExceptions(e.toString());
		}

		return jsonObject;
	}

}
