package com.appngeek.saas_multi_tenant_demo.Util;

import java.net.URI;

public class DBUtil {

	public static String databaseURLFromMYSQLJdbcUrl(String url, String newDbName) {
		try {
			String cleanURI = url.substring(5);

			URI uri = URI.create(cleanURI);
			return "jdbc:" + uri.getScheme() + "://" + uri.getHost() + ":" + uri.getPort() + "/" + newDbName
					+ "?createDatabaseIfNotExist=true&verifyServerCertificate=false&useSSL=false&requireSSL=false&useUnicode=yes&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}