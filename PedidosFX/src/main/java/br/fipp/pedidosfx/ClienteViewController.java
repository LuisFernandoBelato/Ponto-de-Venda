package br.fipp.pedidosfx;

import br.fipp.pedidosfx.db.DBSingleton;
import br.fipp.pedidosfx.db.dals.ClienteDAL;
import br.fipp.pedidosfx.db.entidades.Cliente;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClienteViewController implements Initializable {
    public static Cliente cliente=null;
    public TextField tfPesquisa;
    public TableView <Cliente>tableView;
    public TableColumn <Cliente,Integer> colID;
    public TableColumn <Cliente, String> colNome;
    public TableColumn <Cliente, String> colCidade;
    public TableColumn <Cliente, String> colBairro;
    public TableColumn <Cliente, String> colUF;
    public TableColumn <Cliente, Long> colCpf;
    public TableColumn <Cliente, String> colEmail;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
        colBairro.setCellValueFactory(new PropertyValueFactory<>("bairro"));
        colUF.setCellValueFactory(new PropertyValueFactory<>("uf"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("documento"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        preencherTabela("");
    }
    private void preencherTabela(String filtro)
    {
        List<Cliente> clientes = new ClienteDAL().get(filtro);
        tableView.setItems(FXCollections.observableArrayList(clientes));
    }

    public void onNovoCliente(ActionEvent actionEvent) throws IOException {
        abrirCadCliente();
        preencherTabela("");
    }

    public void onPesquisar(KeyEvent keyEvent) {
        String filtro=tfPesquisa.getText().toUpperCase();
        preencherTabela("upper(cli_nome) like '%"+filtro+"%'");
    }

    public void onAlterar(ActionEvent actionEvent) throws IOException {
        if(tableView.getSelectionModel().getSelectedIndex()>=0) {
            cliente = tableView.getSelectionModel().getSelectedItem();
            abrirCadCliente();
            preencherTabela("");
            cliente=null;
        }
    }

    public void onApagar(ActionEvent actionEvent) {
        Cliente cliente=tableView.getSelectionModel().getSelectedItem();
        if(cliente!=null)
        {
            //perguntar se deseja apagar realmente
            new ClienteDAL().apagar(cliente);
            preencherTabela("");
        }
    }

    public void onFechar(ActionEvent actionEvent) {
        ((Button)actionEvent.getSource()).getScene().getWindow().hide();
    }
    private void abrirCadCliente() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("cliente-cad-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setTitle("Clientes");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
