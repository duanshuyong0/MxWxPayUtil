package com.px.util;

/**
 * ����������
 * 
 * @author ��
 * @version 1.0.2
 *
 */
public class Test {
	public static void main(String[] args) {
		// ΢��Ԥ�µ� ����ʾ��
		JSAPIPay();
		// ��ȡopenid ����ʾ��
		// getOpenid();
		// ��ҵ���� ����ʾ��
		// compayWxPay();
		// ����ģ����Ϣʾ��
		// sendTempleMsg();
	}

	/**
	 * ΢��Ԥ�µ� ����ʾ��
	 */
	public static void JSAPIPay() {
		try {
			JSAPIWxPayBuilder jsapiWxPayBuilder = new JSAPIWxPayBuilder();
			jsapiWxPayBuilder.setAppid("С����APPID");
			jsapiWxPayBuilder.setAttach("Я������");
			jsapiWxPayBuilder.setMch_id("�̻���");
			jsapiWxPayBuilder.setAPI_KEY("�̻���API��Կ");
			jsapiWxPayBuilder.setBody("��Ʒ����");
			jsapiWxPayBuilder.setTotal_fee(50); // ���׽��
			jsapiWxPayBuilder.setNotify_url("�ص���ַ"); //
			jsapiWxPayBuilder.setSpbill_create_ip("���������IP"); //
			jsapiWxPayBuilder.setOpenid("OPENID");

			jsapiWxPayBuilder.build();// ��֤����
			// System.out.println(jsapiWxPayBuilder.hand());// ���ʹ���
		} catch (LackParamExceptions e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡopenid ����ʾ��
	 */
	public static void getOpenid() {
		try {
			GetOpenidBuilder getOpenidBuilder = new GetOpenidBuilder();
			getOpenidBuilder.setAppid("����С����Openid");
			getOpenidBuilder.setAppsecret("����С������Կ");
			getOpenidBuilder.setCode("С�����½ʱ��ȡ��code");
			getOpenidBuilder.build();// ��֤����
			System.out.println(getOpenidBuilder.hand());// ���ʹ���
		} catch (LackParamExceptions e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ҵ���� ����ʾ��
	 */
	public static void compayWxPay() {
		try {
			CompanyWxPayBuilder compayBuilder = new CompanyWxPayBuilder("֤��·��");
			compayBuilder.setMch_appid("��������ʹ�õ�С����appid���ں�appid");
			compayBuilder.setAPI_KEY("�����̻���API������Կ");
			compayBuilder.setDesc("���ע");
			compayBuilder.setMchid("�����̻���");
			compayBuilder.setOpenid("С������ںŶ�Ӧ��openid");
			compayBuilder.setSpbill_create_ip("����ip�������� localhost��127.0.0.1");
			compayBuilder.setAmount(200); // ֧�����

			compayBuilder.build(); // ��֤����
			System.out.println(compayBuilder.hand()); // ���ʹ���
		} catch (LackParamExceptions e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����ģ����Ϣʾ��
	 */
	public static void sendTempleMsg() {
		try {
			SendTempleMsgBuilder sendTempleMsgBuilder = new SendTempleMsgBuilder("���ں�APPID", "����ģ��ID");
			sendTempleMsgBuilder.setAccess_token("access����token");
			sendTempleMsgBuilder.toMiniprogram("�û����ģ����Ϣʱ��תС�����APPID", "��ת·��");
			sendTempleMsgBuilder.toUrl("�û����ģ����Ϣʱ��ת����ַ");
			sendTempleMsgBuilder.addCostomData("�Զ�����������", "ʮ��������ɫֵ", "������ֵ"); // ����Զ�������
			// ʾ��
			sendTempleMsgBuilder.addCostomData("first", "#ff0000", "�����µĹ���֪ͨ");
			sendTempleMsgBuilder.build(); // ��֤����
			System.out.println(sendTempleMsgBuilder.hand()); // ���ʹ���
		} catch (LackParamExceptions e) {
			e.printStackTrace();
		}
	}
}
