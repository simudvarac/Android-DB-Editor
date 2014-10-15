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


import java.util.ArrayList;
import java.util.List;


public class Package  implements TreeObject, Comparable<Package>{
	private String name;
	private List<Database> databases=new ArrayList<Database>();

	public Package(String name) {
		this.name = name;
	}
	public void addDatabase(Database db){
		this.databases.add(db);
	}


	public String getName() {
		return name;
	}
	public List<Database> getDatabases() {
		return databases;
	}
	public String toString(){
		StringBuilder sb=new StringBuilder();
		sb.append(name);
		sb.append("\n");
		for (Database db:databases){
			sb.append("\t"+db.getName()+"\n");
		}
		return sb.toString();
	}
	@Override
	public int compareTo(Package pack) {
		return this.getName().compareTo(pack.getName());
	}
	@Override
	public String getIconPath() {
		return "img/package.png";
	}

}
