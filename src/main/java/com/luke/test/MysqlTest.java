package com.luke.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.luke.utils.MysqlHelper;

public class MysqlTest {

	public static String txt2String(File file) {
		StringBuilder result = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
			String s = null;
			while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
				result.append(System.lineSeparator() + s);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public static void updateSql(String str) {
		try {
			String sql = "insert into b_doc_tips_value (doc_id,tip_value) values (?,?)";
			List<Object> para = new ArrayList<>();
			para.add("hello");

//			str = CryptoUtil.aesEncrypt(str, "database");

			para.add(str);

			MysqlHelper.getInstance("tender").executeUpdate(sql, para.toArray());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		File file = new File("/tmp/OGLdpf.log");
		updateSql(txt2String(file));
	}
}
