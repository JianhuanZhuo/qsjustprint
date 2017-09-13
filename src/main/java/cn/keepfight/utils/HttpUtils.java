package cn.keepfight.utils;

public class HttpUtils {

//	/**
//	 * 简单 Get 操作，仅传入 URL 和 Session Cookie，当然你可以在 URL 中添加 Get 参数
//	 *
//	 * @param url
//	 *            Get 方法的目标 URL
//	 * @return 简单 Get 的结果字符串
//	 * @throws Exception
//	 *             简单 Get 异常
//	 */
//	public static String simpleGet(String url) throws Exception {
//		CloseableHttpClient httpClient = HttpClients.createDefault();
//		try {
//			System.out.println("simple get: "+url);
//			HttpPost post = new HttpPost(url);
//			CloseableHttpResponse resp = httpClient.execute(post);
//			try {
//				return EntityUtils.toString(resp.getEntity());
//			} finally {
//				resp.close();
//			}
//		} finally {
//			httpClient.close();
//		}
//	}
//
//	public static String simpleGetWithEncode(String url, List<Pair<String, String>> params) throws Exception {
//		String paramStr = "";
//		for (Pair<String, String> p : params) {
//			paramStr += "&"+p.getKey()+"="+URLEncoder.encode(p.getValue(), "utf-8");
//		}
//		System.out.println();
//		params.stream()
//				.map(p -> {
//					try {
//						return p.getKey()+"="+URLEncoder.encode(p.getValue(), "utf-8");
//					} catch (UnsupportedEncodingException e) {
//						e.printStackTrace();
//					}
//					return null;
//				})
//				.collect(Collectors.joining("&"));
//		return simpleGet(url+"?"+paramStr.substring(1));
//	}
//
//	public static String simplePost(String url, String param) throws Exception {
//		CloseableHttpClient httpClient = HttpClients.createDefault();
//		try {
//			HttpPost post = new HttpPost(url);
//			// add Cookie JSESSIONID
//			// post.addHeader("Cookie", "JSESSIONID=" + jseSession);
//			StringEntity params =new StringEntity("yy="+param);
//			post.addHeader("content-type", "application/x-www-form-urlencoded");
//			post.setEntity(params);
//			CloseableHttpResponse resp = httpClient.execute(post);
//			try {
//				return EntityUtils.toString(resp.getEntity());
//			} finally {
//				resp.close();
//			}
//		} finally {
//			httpClient.close();
//		}
//	}
//
//	public static String simplePostJSONWithUTG8(String url, Pair<String, String> param) throws Exception {
//		CloseableHttpClient httpClient = HttpClients.createDefault();
//		try {
//			HttpPost post = new HttpPost(url);
//			StringEntity params =new StringEntity(param.getKey()+"="+URLEncoder.encode(param.getValue(), "utf-8"));
//			post.addHeader("content-type", "application/x-www-form-urlencoded");
//			post.setEntity(params);
//			CloseableHttpResponse resp = httpClient.execute(post);
//			try {
//				return EntityUtils.toString(resp.getEntity());
//			} finally {
//				resp.close();
//			}
//		} finally {
//			httpClient.close();
//		}
//	}
}
