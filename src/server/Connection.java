import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection extends Thread {

	BufferedInputStream bis;
	OutputStream os;
	ServerSocket serversocket;
	Socket client;
	DataInputStream input;
	DataOutputStream output;
	public String FILE_TO_SEND;

	public Connection(ServerSocket serversocket) throws IOException {
		System.out.println("Waiting for connection from client");
		client = serversocket.accept();
		while (true) {
			try {
				if (this.logInfo()) {
					start();
					break;
				}
			} catch (Exception ex) {
				// System.exit(0);
			}

		}
	}

	public void run() {

		while (true) {
			try {
				String action = input.readUTF();
				System.out.println(action);
				switch (action) {
				case "browserFile":
					this.browserFile();
					continue;
				case "downloadFile":
					this.downloadFileToClient();
					continue;
				}

			} catch (Exception ex) {
				// System.exit(0);
				System.out.println("Client Already Logout the system");
				break;
			}
		}
	}

	public void downloadFileToClient() throws Exception {
		File folder = new File(FILE_TO_SEND);
		File[] listOfFiles = folder.listFiles();
		System.out.println("Start Download File");
		String fileName = input.readUTF();
		System.out.println(fileName);
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].getName().equals(fileName)) {
				DataOutputStream out = new DataOutputStream(client.getOutputStream());
				byte[] mybytearray = new byte[1048576];
				int byteSender;
				FileInputStream fileInputStream = new FileInputStream(listOfFiles[i].getCanonicalPath());
				while ((byteSender = fileInputStream.read(mybytearray)) != -1) {
					out.write(mybytearray, 0, byteSender);
				}
				fileInputStream.close();
			}
		}
		output.writeUTF("End Download File");
		System.out.println("End Download File");
	}

	public void browserFile() throws Exception {
		System.out.println("Start of bowersFiles");
		FILE_TO_SEND = input.readUTF();
		File folder = new File(FILE_TO_SEND);
		File[] listOfFiles = folder.listFiles();
		String fileInfo = null;
		for (int i = 0; i < listOfFiles.length; i++) {
			try {
				fileInfo = listOfFiles[i].getName() + "," + listOfFiles[i].getCanonicalPath() + ","
						+ listOfFiles[i].length();
			} catch (Exception ex) {
				System.exit(0);
			}
			System.out.println(fileInfo);
			output.writeUTF(fileInfo);
		}
		output.writeUTF("End of bowersFiles");
		System.out.println("End of bowersFiles");
	}

	public boolean logInfo() throws Exception {
		// open buffered reader for reading data from client
		input = new DataInputStream(client.getInputStream());
		String username = input.readUTF();
		System.out.println("SERVER SIDE - Username :" + username);
		String password = input.readUTF();
		System.out.println("SERVER SIDE - Password :" + password);
		output = new DataOutputStream(client.getOutputStream());
		if (username.equals("eric") && password.equals("1234")) {
			output.writeUTF("Login Success");
			System.out.println("Login Success");
			return true;
		} else {
			output.writeUTF("Login Failed");
			System.out.println("Login Failed");

			return false;
		}
	}

}
