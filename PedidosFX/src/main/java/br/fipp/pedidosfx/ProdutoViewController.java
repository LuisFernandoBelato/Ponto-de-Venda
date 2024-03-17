package br.fipp.pedidosfx;

import br.fipp.pedidosfx.db.dals.ClienteDAL;
import br.fipp.pedidosfx.db.dals.ProdutoDAL;
import br.fipp.pedidosfx.db.entidades.Categoria;
import br.fipp.pedidosfx.db.entidades.Cliente;
import br.fipp.pedidosfx.db.entidades.Produto;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

public class ProdutoViewController implements Initializable {
    public static Produto produto=null;
    public TextField tfPesquisa;

    @FXML
    private TableColumn<Produto, Categoria> colCategoria;

    @FXML
    private TableColumn<Produto, Double> colEstoque;

    @FXML
    private TableColumn<Produto, Integer> colID;

    @FXML
    private TableColumn<Produto, String> colNome;

    @FXML
    private TableColumn<Produto, Double> colPreco;

    @FXML
    private TableView<Produto> tableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colEstoque.setCellValueFactory(new PropertyValueFactory<>("estoque"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        preencherTabela("");
    }
    private void preencherTabela(String filtro)
    {
        List<Produto> produtos = new ProdutoDAL().get(filtro);
        tableView.setItems(FXCollections.observableArrayList(produtos));
    }


    public void onNovoCliente(ActionEvent actionEvent) throws IOException {
        abrirCadProduto();
        preencherTabela("");

    }

    public void onPesquisar(KeyEvent keyEvent) {
        String filtro=tfPesquisa.getText().toUpperCase();
        preencherTabela("upper(pro_nome) like '%"+filtro+"%'");
    }

    public void onAlterar(ActionEvent actionEvent) throws IOException {
        if(tableView.getSelectionModel().getSelectedIndex()>=0) {
            produto = tableView.getSelectionModel().getSelectedItem();
            abrirCadProduto();
            preencherTabela("");
            produto=null;
        }

    }

    public void onApagar(ActionEvent actionEvent) {
        Produto produto=tableView.getSelectionModel().getSelectedItem();
        if(produto!=null)
        {
            //perguntar se deseja apagar realmente
            new ProdutoDAL().apagar(produto);
            preencherTabela("");
        }
    }

    public void onFechar(ActionEvent actionEvent) {
        ((Button)actionEvent.getSource()).getScene().getWindow().hide();
    }
    private void abrirCadProduto() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("produto-cad-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setTitle("Produtos");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
