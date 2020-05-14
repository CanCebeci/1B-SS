import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

public class CombatUIAdapter {
    // --- attributes ---
    Stage primaryStage;
    CombatUIController controller;

    // --- constructors ---
    public CombatUIAdapter() {}     // to be deleted later on. just a quick fix to make the TEST version usable.

    public CombatUIAdapter(Stage primaryStage) throws IOException{
        // set the primaryStage attribute to be able to invoke Stage.show() again
        this.primaryStage = primaryStage;

        //
        FileInputStream file = new FileInputStream("src/combat_management/CombatUI.fxml");
        FXMLLoader loader = new FXMLLoader();
        Scene combatScene = loader.load(file);
        ((CombatUIController)loader.getController()).setUIAdapter(this);
        primaryStage.setScene(combatScene);
        primaryStage.show();
    }

    // --- methods ---
    public void updateView() {
        controller.updateEnemies();
        controller.updatePlayer();
        controller.updateCardPiles();
    }

    public void endTurnPressed() {
        CombatManager.getInstance().endTurn();
        System.out.println("Turn ended...");
        System.out.println("The player has " + CombatManager.getInstance().getPlayer().getHP() + " HP left");
    }
}
