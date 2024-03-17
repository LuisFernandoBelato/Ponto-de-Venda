package br.fipp.pedidosfx;

import br.fipp.pedidosfx.db.dals.CategoriaDAL;
import br.fipp.pedidosfx.db.dals.ClienteDAL;
import br.fipp.pedidosfx.db.entidades.Categoria;
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

public class CategoriaViewControler implements Initializable {
    public static Categoria categoria=null;
    public TableColumn onId;
    public TableColumn onNome;
    public TableColumn onDescricao;
    public TextField tfPesquisa;
    public TableView<Categoria> tableView;

    private void abrirCategoria() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("categoria-cad-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage= new Stage();
        stage.setTitle("Clientes");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        onId.setCellValueFactory(new PropertyValueFactory<>("id"));
        onNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        onDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        preencherTabela("");

    }

    private void preencherTabela(String filtro) {
        List<Categoria> categorias = new CategoriaDAL().get(filtro);
        tableView.setItems(FXCollections.observableArrayList(categorias));
    }

    public void onPesquisar(KeyEvent keyEvent) {
        String filtro=tfPesquisa.getText().toUpperCase();
        preencherTabela("upper(cat_nome) like '%"+filtro+"%'");
    }

    public void onNovaCategoria(ActionEvent actionEvent) throws IOException {
        abrirCategoria();
        preencherTabela("");
    }

    public void onAlterar(ActionEvent actionEvent) throws IOException {

        if(tableView.getSelectionModel().getSelectedIndex()>=0) {
            categoria = tableView.getSelectionModel().getSelectedItem();
            abrirCategoria();
            preencherTabela("");
            categoria=null;
        }


    }

    public void onApagar(ActionEvent actionEvent) {

        Categoria categoria=tableView.getSelectionModel().getSelectedItem();
        if(categoria!=null)
        {
            //perguntar se deseja apagar realmente
            new CategoriaDAL().apagar(categoria);
            preencherTabela("");
        }

    }

    public void onFechar(ActionEvent actionEvent) {
        ((Button)actionEvent.getSource()).getScene().getWindow().hide();
        //tfPesquisa.getParent().getScene().getWindow().hide();

    }

}
