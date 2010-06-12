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

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;


public class AndroidDBEditor extends JFrame {
	List<Device> devices = new ArrayList<Device>();
	
	private JTree tree;

	private DefaultMutableTreeNode createNodes(Device device) {
		DefaultMutableTreeNode deviceNode=new DefaultMutableTreeNode(device.getName());
		deviceNode.setUserObject(device);
		for (Package pack:device.getPackages()){
			DefaultMutableTreeNode	packageNode= new DefaultMutableTreeNode(pack.getName());
			packageNode.setUserObject(pack);
			
			deviceNode.add(packageNode);
			for (Database database:pack.getDatabases()){
				DefaultMutableTreeNode dbNode=new DefaultMutableTreeNode(database.getName());
				dbNode.setUserObject(database);
				packageNode.add(dbNode);
				
			}
		}
		return deviceNode;
	}

	public JTree createDeviceTree(List<Device> devices) {
		DefaultMutableTreeNode top=new DefaultMutableTreeNode("Devices");
		TreePath path=new TreePath(top);
		JTree tree = new JTree(top);
		for (Device device:devices)
		top.add(createNodes(device));
		tree.expandPath(path);
		tree.setRootVisible(false);
		tree.setCellRenderer(new CellRenderer());
		tree.addMouseListener(new TreeMouseListener());
		return tree;

	}

	public AndroidDBEditor() {
		super("Android DB Editor");
		this.setVisible(true);
		List<String> deviceNames = ADBConnector.devices();
		for (String deviceName : deviceNames) {
			Device d = new Device(deviceName);
			devices.add(d);
		}
		tree=createDeviceTree(devices);
		
		JScrollPane scrollPane = new JScrollPane(tree);
		this.getContentPane().add(scrollPane);
		this.setSize(300,600);
	
	}

	public static void main(String[] args) {
		new AndroidDBEditor();
	}
}
