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


public class Database  implements TreeObject {
	private String name;
	private String packageName;
	private String deviceName;

	Database(String deviceName, String packageName, String name) {
		this.name = name;
		this.packageName = packageName;
		this.deviceName = deviceName;
	}
String getDBPath(){
	return "/data/data/"+packageName+"/databases/"+name;
}
	public String getName() {
	return name;
}
public String getPackageName() {
	return packageName;
}
public String getDeviceName() {
	return deviceName;
}
	public String toString() {
		return deviceName + ": " + packageName + "/" + name;
	}
	@Override
	public String getIconPath() {
		return "img/db.png";
	}

}
