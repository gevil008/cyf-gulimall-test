package com.atguigu.gulimall.auth;

import java.util.Map;

public class Test {

	public static void main(String args[]){
		String jsonString = "{\"str\":\"string\",\"num\":100,\"boolean\":true,\"obj\":{\"key1\":\"value1\",\"key2\":\"value2\"},\"list\":[{\"list1\":\"list1\"},{\"list2\":\"list2\"}]}";
		JSONReader jr = new JSONReader();
		Map map = (Map)jr.read(jsonString);
		System.out.println("Json解析完成");
		System.out.println("Map----" + map.toString());
		System.out.println("list----" + map.get("list").getClass().getName() + ":" + map.get("list"));
		System.out.println("str----" + map.get("str").getClass().getName() + ":" + map.get("str"));
		System.out.println("num----" + map.get("num").getClass().getName() + ":" + map.get("num"));
		System.out.println("boolean----" + map.get("boolean").getClass().getName() + ":" + map.get("boolean"));
		System.out.println("obj----" + map.get("obj").getClass().getName() + ":" + map.get("obj"));
	}
}
