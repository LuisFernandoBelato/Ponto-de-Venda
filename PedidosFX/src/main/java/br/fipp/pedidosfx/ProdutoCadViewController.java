package br.fipp.pedidosfx;

import br.fipp.pedidosfx.db.DBSingleton;
import br.fipp.pedidosfx.db.dals.CategoriaDAL;
import br.fipp.pedidosfx.db.dals.ClienteDAL;
import br.fipp.pedidosfx.db.dals.ProdutoDAL;
import br.fipp.pedidosfx.db.entidades.Categoria;
import br.fipp.pedidosfx.db.entidades.Cliente;
import br.fipp.pedidosfx.db.entidades.Produto;
import br.fipp.pedidosfx.util.MaskFieldUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProdutoCadViewController implements Initializable {

    @FXML
    private ComboBox<Categoria> cbCategoria;

    @FXML
    private TextField tfEstoque;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfPreco;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{tfNome.requestFocus();});
     //   MaskFieldUtil.numericField(tfPreco);
        MaskFieldUtil.numericField(tfEstoque);
        if (ProdutoViewController.produto!=null)
        {
            tfId.setText(""+ProdutoViewController.produto.getId());
            tfNome.setText(ProdutoViewController.produto.getNome());
            tfPreco.setText("R$ "+String.valueOf(ProdutoViewController.produto.getPreco()));
            tfEstoque.setText(String.valueOf(ProdutoViewController.produto.getEstoque()));
        }
        carregarCategorias();
    }

    private void carregarCategorias() {
        List<Categoria> categorias=new CategoriaDAL().get("");
//        for (Categoria categoria : categorias) {
            cbCategoria.getItems().addAll(categorias);
  //      }
    }


    @FXML
    void onCancelar(ActionEvent event) {

    }

    @FXML
    void onConfirmar(ActionEvent actionEvent) {
        Produto produto = new Produto(tfNome.getText(),cbCategoria.getValue(),
                Double.parseDouble(tfPreco.getText().replace("R$","")),Double.parseDouble(tfEstoque.getText()));

        if(ProdutoViewController.produto==null) {
            if (!new ProdutoDAL().gravar(produto)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(DBSingleton.getConexao().getMensagemErro());
                alert.showAndWait();
            }
        }
        else {
            produto.setId(ProdutoViewController.produto.getId());
            if (!new ProdutoDAL().alterar(produto)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(DBSingleton.getConexao().getMensagemErro());
                alert.showAndWait();
            }
        }
        // salvar ou alterar o cliente
        ((Button)actionEvent.getSource()).getScene().getWindow().hide();

    }


}
