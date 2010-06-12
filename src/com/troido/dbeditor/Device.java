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


import java.util.Arrays;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;


public class Device implements TreeObject{
	private String name;
	public String getName() {
		return name;
	}
	private Package[] packages;

	public Package[] getPackages() {
		return packages;
	}

	public Device(String name) {
		this.name = name;
		List<Database> databases = ADBConnector.getDatabases(name);
		createPackages(databases);

	}

	private void createPackages(List<Database> databases) {
		Hashtable<String, Package> packageMap=new Hashtable<String, Package>();
		for (Database db : databases) {
			Package p = packageMap.get(db.getPackageName());
			if (p == null) {
				p = new Package(db.getPackageName());
				packageMap.put(db.getPackageName(), p);
			}
			p.addDatabase(db);
		}
		packages=packageMap.values().toArray(new Package[0]);
		Arrays.sort(packages,new Comparator<Package>() {

			@Override
			public int compare(Package o1, Package o2) {
				return o1.compareTo(o2);
			}
		});
	}
	public String toString(){
		StringBuilder sb=new StringBuilder();
		sb.append(name);
		sb.append("\n");
		for (Package p:packages){
			sb.append(p.toString());
		}
		return sb.toString();
	}

	@Override
	public String getIconPath() {
		return "img/device.png";
	}
	
}
