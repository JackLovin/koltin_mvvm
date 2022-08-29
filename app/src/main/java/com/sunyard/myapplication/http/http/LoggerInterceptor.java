//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.myapplication.http.http;

import android.text.TextUtils;


import com.example.myapplication.http.util.MyLog;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Response.Builder;
import okhttp3.ResponseBody;
import okio.Buffer;


public class LoggerInterceptor implements Interceptor {

	private String tag = "Http";
	private boolean showResponse = true;

	public LoggerInterceptor(String tag, boolean showResponse) {
		this.showResponse = showResponse;
		this.tag = tag;
	}

	public LoggerInterceptor() {

	}

	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();
		this.logForRequest(request);
		Response response = chain.proceed(request);
		return this.logForResponse(response);
	}

	private Response logForResponse(Response response) {
		try {
			MyLog.d(this.tag, "============================response\'log============================");
			Builder e = response.newBuilder();
			Response clone = e.build();
			MyLog.d(this.tag, "url : " + clone.request().url());
			MyLog.d(this.tag, "code : " + clone.code());
			MyLog.d(this.tag, "protocol : " + clone.protocol());
			if (!TextUtils.isEmpty(clone.message())) {
				MyLog.d(this.tag, "message : " + clone.message());
			}
			if (this.showResponse) {
				ResponseBody body = clone.body();
				if (body != null) {
					MediaType mediaType = body.contentType();
					if (mediaType != null) {
						MyLog.d(this.tag, "responseBody\'s contentType : " + mediaType.toString());
						if (this.isText(mediaType)) {
							String resp = body.string();
							MyLog.d(this.tag, "responseBody\'s content : " + resp);
							body = ResponseBody.create(mediaType, resp);
							return response.newBuilder().body(body).build();
						}

						MyLog.d(this.tag, "responseBody\'s content :  maybe [file part] , too large too print , ignored!");
					}
				}
			}

			MyLog.d(this.tag, "============================返回消息：\'log============================end");
		} catch (Exception var7) {
			var7.printStackTrace();
		}

		return response;
	}

	private void logForRequest(Request request) {
		try {
			String e = request.url().toString();
			Headers headers = request.headers();
			MyLog.d(this.tag, "============================请求消息\'log============================");
			MyLog.d(this.tag, "method : " + request.method());
			MyLog.d(this.tag, "url : " + e);
			if (headers.size() > 0) {
				MyLog.d(this.tag, "headers : " + headers.toString());
			}

			RequestBody requestBody = request.body();
			if (requestBody != null) {
				MediaType mediaType = requestBody.contentType();
				if (mediaType != null) {
					MyLog.d(this.tag, "requestBody\'s contentType : " + mediaType.toString());
					if (this.isText(mediaType)) {
						MyLog.d(this.tag, "requestBody\'s content : " + this.bodyToString(request));
					} else {
						MyLog.d(this.tag, "requestBody\'s content :  maybe [file part] , too large too print , ignored!");
					}
				}
			}

			MyLog.d(this.tag, "============================request\'log============================end");
		} catch (Exception var6) {
			var6.printStackTrace();
		}

	}

	private boolean isText(MediaType mediaType) {
		return mediaType.type().equals("text") || mediaType.subtype().equals("json") || mediaType.subtype().equals("xml") || mediaType.subtype().equals("html") || mediaType.subtype().equals("webviewhtml");
	}

	private String bodyToString(Request request) {
		try {
			Request e = request.newBuilder().build();
			Buffer buffer = new Buffer();
			assert e.body() != null;
			e.body().writeTo(buffer);
			return buffer.readUtf8();
		} catch (IOException var4) {
			return "something error when show requestBody.";
		}
	}
}
