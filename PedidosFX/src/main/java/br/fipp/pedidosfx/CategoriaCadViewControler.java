package br.fipp.pedidosfx;

import br.fipp.pedidosfx.db.DBSingleton;
import br.fipp.pedidosfx.db.dals.CategoriaDAL;
import br.fipp.pedidosfx.db.dals.ClienteDAL;
import br.fipp.pedidosfx.db.entidades.Categoria;
import br.fipp.pedidosfx.db.entidades.Cliente;
import br.fipp.pedidosfx.util.MaskFieldUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CategoriaCadViewControler implements Initializable {
    public TextField tfid;
    public TextField tfnome;
    public TextField tfdesc;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{tfnome.requestFocus();});
        if (CategoriaViewControler.categoria!=null)
        {
            tfid.setText(""+CategoriaViewControler.categoria.getId());
            tfnome.setText(CategoriaViewControler.categoria.getNome());
            tfdesc.setText(CategoriaViewControler.categoria.getDescricao());
        }

    }

    public void onConfirmar(ActionEvent actionEvent) {
        Categoria categoria = new Categoria(0,tfnome.getText(),tfdesc.getText());
        if(CategoriaViewControler.categoria==null) {
            if (!new CategoriaDAL().gravar(categoria)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(DBSingleton.getConexao().getMensagemErro());
                alert.showAndWait();
            }
        }
        else {
            categoria.setId(CategoriaViewControler.categoria.getId());
            System.out.println(categoria.getId());
            System.out.println(categoria.getDescricao());
            System.out.println(categoria.getNome());
            if (!new CategoriaDAL().alterar(categoria)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(DBSingleton.getConexao().getMensagemErro());
                alert.showAndWait();
            }
        }
        // salvar ou alterar o cliente
        ((Button)actionEvent.getSource()).getScene().getWindow().hide();
    }

    public void onCancelar(ActionEvent actionEvent) {
        ((Button)actionEvent.getSource()).getScene().getWindow().hide();
    }



}
