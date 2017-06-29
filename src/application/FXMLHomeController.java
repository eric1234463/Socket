/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLHomeController implements Initializable {

	private Client client;
	private Networking application;
	@FXML
	private TableView<FileList> tableView;
	@FXML
	private TableColumn col1, col2, col3;
	@FXML
	private TextField serverFilePath;
	private ObservableList<FileList> data = FXCollections.observableArrayList();

	public ArrayList<FileList> fileList = new ArrayList<FileList>();

	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}

	public void setApp(Networking application, Client client) throws IOException {
		col1.setCellValueFactory(new PropertyValueFactory<FileList, String>("fileName"));
		col2.setCellValueFactory(new PropertyValueFactory<FileList, String>("filePath"));
		col3.setCellValueFactory(new PropertyValueFactory<FileList, String>("fileSize"));
		tableView.getSelectionModel().setCellSelectionEnabled(true);
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		this.application = application;
		this.client = client;
		fileList = this.client.readFile();
		this.serverFilePath.setText(this.client.serverFilePath);
		ObservableList<FileList> row = FXCollections.observableArrayList();
		for (FileList file : fileList) {
			row.add(file);
		}
		data.addAll(row);
		tableView.setItems(data);
	}

	@FXML
	protected void handleSubmitButtonAction(ActionEvent event) throws IOException {
		ObservableList<FileList> selectedFileList = tableView.getSelectionModel().getSelectedItems();
		for (FileList selectedFile : selectedFileList) {
			client.downloadFile(selectedFile.getFileName(), selectedFile.getFileSize());
		}
	}

	@FXML
	protected void changeFolderPath(ActionEvent event) throws IOException {
		tableView.getItems().clear();
		tableView.refresh();
		this.client.serverFilePath = this.serverFilePath.getText();
		fileList.clear();
		fileList = this.client.readFile();
		
		ObservableList<FileList> row = FXCollections.observableArrayList();
		for (FileList file : fileList) {
			row.add(file);
		}
		data.clear();
		data.addAll(row);
		tableView.setItems(data);
		tableView.refresh();
	}

}
