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


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class TreeMouseListener implements MouseListener {

	public static String sqlitepath;

	public TreeMouseListener() {
	if (sqlitepath==null){
		try{
		Properties props=new Properties();
		String iniPath=new File(TreeMouseListener.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent()+"/settings.ini";
	    props.load(new FileReader(iniPath));
	    sqlitepath=props.getProperty("sqliteeditorpath");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			JTree tree = (JTree) e.getSource();
			DefaultMutableTreeNode dmt = (DefaultMutableTreeNode) tree
					.getLastSelectedPathComponent();
			Object obj = dmt.getUserObject();
			if (obj instanceof Database) {
				File dbFile = ADBConnector.getDatabase((Database) obj);
				String cmdLine[]={sqlitepath,dbFile.getPath()};
				try{
				Runtime.getRuntime().exec(cmdLine);
				new DBChangeListener((Database)obj, dbFile);
				}catch(Exception exc){
					exc.printStackTrace();
				}
			}
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
