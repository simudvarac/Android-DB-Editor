/*
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
*/

package com.troido.dbeditor;

import java.awt.List;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ADBConnector {

	public static String runADBCommand(String device, String command) {
		String commandline = "adb";
		if (device != null)
			commandline += " -s " + device;
		commandline += " " + command;
	//System.out.println("Running " + commandline);
		try {
			Process process = Runtime.getRuntime().exec(commandline);
			StringBuilder errSB = new StringBuilder();
			StringBuilder outSB = new StringBuilder();
			InputStream err = process.getErrorStream();
			InputStream is = process.getInputStream();
			int i = 0;

			if (err.available() > 0)
				while ((i = err.read()) != -1) {
					errSB.append((char) i);
					System.out.println(i);
				}
			while ((i = is.read()) != -1)
				outSB.append((char) i);
			if (errSB.length() > 0)
				throw new RuntimeException(errSB.toString());
			return outSB.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public static ArrayList<String> devices() {
		String devices = runADBCommand(null, "devices");
		ArrayList<String> deviceList = new ArrayList<String>();
		String split[] = devices.split("\n");
		Pattern p = Pattern.compile("(.*?)\\s*device$");
		for (String s : split) {
			Matcher m = p.matcher(s);
			if (m.find())
				deviceList.add(m.group(1));
		}
		return deviceList;
	}

	public static ArrayList<String> getPackages(String deviceName) {
		String command = "shell ls /data/data";
		ArrayList<String> packageList = new ArrayList<String>();
		String s = runADBCommand(deviceName, command);
		String packages[] = s.split("\n");
		for (String pack : packages) {
			pack = pack.trim();
			if (pack.length() > 0)
				packageList.add(pack);
		}
		return packageList;
	}

	public static ArrayList<Database> getDatabases(String deviceName) {
		ArrayList<String> packages = getPackages(deviceName);
		ArrayList<Database> databases = new ArrayList<Database>();
		for (String packageName : packages) {
			String result = runADBCommand(deviceName, "shell ls /data/data/"
					+ packageName + "/databases");
			//System.out.println(result);
			String[] lines = result.split("(\n|\\s)");
			for (String db : lines) {
				// System.out.println(db);
				if (db.trim().matches(".*db"))
					databases.add(new Database(deviceName, packageName, db
							.trim()));

			}
		}
		return databases;
	}

	public static File getDatabase(Database db) {
		try {
			File f = File.createTempFile(db.getName(), ".db");
			runADBCommand(db.getDeviceName(), "pull " + db.getDBPath() + " "
					+ f.getPath());
			return (f);
		} catch (Exception exc) {
			throw new RuntimeException(exc);
		}
	}

	public static void uploadDB(Database db,File dbFile) {
		try {

			runADBCommand(db.getDeviceName(), "push "+dbFile.getPath()+" " + db.getDBPath());

		} catch (Exception exc) {
			throw new RuntimeException(exc);
		}
	}

}
