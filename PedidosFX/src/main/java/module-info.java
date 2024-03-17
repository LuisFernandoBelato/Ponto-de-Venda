module br.fipp.pedidosfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires java.logging;
    requires org.json;

    opens br.fipp.pedidosfx to javafx.fxml;
    opens br.fipp.pedidosfx.db.entidades;
    exports br.fipp.pedidosfx;
    exports br.fipp.pedidosfx.db.entidades;
}