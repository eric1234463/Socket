/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

/**
 *
 * @author Eric
 */
public class Client {

	public DataInputStream input;
	public DataOutputStream output;
	public Socket socket;
	public String username;
	public String host;
	public String password;
	public String FILE_TO_RECEIVED = "c:/temp/client/";
	public String serverFilePath = "c:/temp/server/";
	BufferedInputStream bis;
	BufferedOutputStream bos;
	public ArrayList<FileList> fileList = new ArrayList<FileList>();

	public boolean Login(String host, String username, String password) {
		final Alert alert = new Alert(Alert.AlertType.INFORMATION);
		try {
			this.socket = new Socket(host, 8999);
			this.username = username;
			this.password = password;
			output = new DataOutputStream(socket.getOutputStream());
			output.writeUTF(username);
			output.writeUTF(password);
			input = new DataInputStream(socket.getInputStream());
			String response = input.readUTF();
			System.out.println(response);
			if (response.equals("Login Success")) {
				return true;
			} else {
				alert.setTitle("Error");
				alert.setHeaderText(response);
				alert.showAndWait();
				return false;
			}
		} catch (IOException ex) {
			Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
			alert.setTitle("Error");
			alert.setHeaderText("Server Connection Fail");
			alert.showAndWait();
			return false;
		}
	}

	public void downloadFile(String fileName, String fileSize) throws IOException {
		output.writeUTF("downloadFile");
		System.out.println("downloadFile");
		output.writeUTF(fileName);
		System.out.println(fileName);
		String FileToBeDownload = FILE_TO_RECEIVED + fileName;
		int size = Integer.parseInt(fileSize);
		byte[] data = new byte[1048576];
		int packetCounter = 0;
		int byteReceive = 0 ;
		File newFile = new File(FileToBeDownload);
		FileOutputStream fileOut = new FileOutputStream(newFile);
		while(size > packetCounter){
			byteReceive = input.read(data);
			packetCounter += byteReceive;
			fileOut.write(data, 0, byteReceive);
		}
		
		fileOut.close();
		System.out.println("Finish Download");
		final Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Success");
		alert.setHeaderText("Finish Download");
		alert.showAndWait();
	}

	public ArrayList<FileList> readFile() throws IOException {

		output.writeUTF("browserFile");
		output.writeUTF(this.serverFilePath);
		String fileInfo;
		try {
			while (true) {
				fileInfo = input.readUTF();
				if (fileInfo.equals("End of bowersFiles")) {
					break;
				} else {
					System.out.println(fileInfo);
					String fileName = fileInfo.split(",")[0];
					String filePath = fileInfo.split(",")[1];
					String fileSize = fileInfo.split(",")[2];
					this.fileList.add(new FileList(fileName, filePath, fileSize));
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileList;
	}
}
