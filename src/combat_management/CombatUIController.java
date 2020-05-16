import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CombatUIController implements  Initializable//,ControlledScreen {
{
    //ScreenController myController;

    private CombatUIAdapter adapter;

    @FXML FlowPane potions;
    @FXML FlowPane enemies;
    @FXML AnchorPane hand;
    @FXML Button drawPile;
    @FXML Button discardPile;
    @FXML Label playerStatus;
    @FXML Label energy;
    @FXML Label numDraw;
    @FXML Label numDiscard;
    @FXML AnchorPane popUpDisplay;
    @FXML Label targetPrompt;

    @FXML private Text MoneyLabel;
    @FXML private Text currentHPLabel;
    @FXML private Text maxHPLabel;
    @FXML private Text playerNameLabel;
    @FXML private ImageView potionSlot1;
    @FXML private ImageView potionSlot2;
    @FXML private ImageView potionSlot3;
    @FXML ImageView closePopUp;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playerNameLabel.setText(Game.getInstance().myPlayer.getPlayerName());

        maxHPLabel.setText(""+(Game.getInstance().myPlayer.getMaxHP()));
        MoneyLabel.setText(""+(Game.getInstance().myPlayer.getGold()));

        reloadPotions();
        reloadRelics();
        popUpDisplay.setDisable(true);
        popUpDisplay.setVisible(false);
        closePopUp.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                popUpDisplay.setVisible(false);
                popUpDisplay.setDisable(true);
            }
        });
        targetPrompt.setVisible(false);
    }

    public void reloadPotions(){
        ArrayList<Potion> pots = CombatManager.getInstance().getPlayer().getPots();

        if(pots.size() > 0){
            Image slot1  = new Image(pots.get(0).getImage()); potionSlot1.setImage(slot1);
            Tooltip.install(potionSlot1, new Tooltip(pots.get(0).getName() + ": " + pots.get(0).getPotionDescription()));
            potionSlot1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    CombatManager.getInstance().potionSelected(pots.get(0));
                }
            });
            potionSlot1.setDisable(false);
        }
        else{potionSlot1.setImage(null);potionSlot1.setDisable(true);}



        if(pots.size() > 1){Image slot2  = new Image(pots.get(1).getImage()); potionSlot2.setImage(slot2);
            Tooltip.install(potionSlot2, new Tooltip( pots.get(1).getName() + ": " + pots.get(1).getPotionDescription()));
            potionSlot2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    CombatManager.getInstance().potionSelected(pots.get(1));
                }

            });
            potionSlot2.setDisable(false);
        }
        else{potionSlot2.setImage(null);potionSlot2.setDisable(true);}

        if(pots.size() > 2){Image slot3  = new Image(pots.get(2).getImage()); potionSlot3.setImage(slot3);
            Tooltip.install(potionSlot3, new Tooltip( pots.get(2).getName() + ": " + pots.get(2).getPotionDescription()));
            potionSlot3.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    CombatManager.getInstance().potionSelected(pots.get(2));
                }
            });
            potionSlot3.setDisable(false);
        }
        else{potionSlot3.setImage(null);potionSlot3.setDisable(true);}
    }



    @FXML
    private HBox relicSlotHBox;

    public void reloadRelics(){
        ArrayList<Relic> relics = CombatManager.getInstance().getPlayer().getRelics();
        relicSlotHBox.getChildren().clear();
        for(int i = 0; i < relics.size(); i++){
            ImageView tempRelicImage = new ImageView();
            tempRelicImage.setFitWidth(56);
            tempRelicImage.setFitHeight(56);
            Image relicImage = new Image(relics.get(i).getImage());
            tempRelicImage.setImage(relicImage);
            tempRelicImage.setPickOnBounds(true);
            Tooltip.install(tempRelicImage, new Tooltip( relics.get(i).getName() + ": " + relics.get(i).getRelicDescription()));
            relicSlotHBox.getChildren().add(tempRelicImage);

        }
    }

    @FXML
    protected void endTurn(){
        targetPrompt.setVisible(false);
        adapter.endTurnPressed();
    }

    public void setUIAdapter( CombatUIAdapter adapter) {
        this.adapter = adapter;
    }

    public void updatePlayer(){
        String status = "";
        for(StatusEffect effect : CombatManager.getInstance().getPlayer().getStatusEffects())
            if(! (effect instanceof RelicEffect))
                status = status + effect.toString() + "\n";//change this to effect.getName() later
        playerStatus.setText(status);
        energy.setText("Energy: " + CombatManager.getInstance().uiEnergyString());

        currentHPLabel.setText(""+(CombatManager.getInstance().getPlayer().getHP()));
    }

    public void updateEnemies() throws IOException {
        enemies.getChildren().clear();
        for (Enemy e : CombatManager.getInstance().getEnemies()) {

            FileInputStream file = new FileInputStream("src/res/EnemyView.fxml");
            FXMLLoader loader = new FXMLLoader();
            AnchorPane pane = loader.load(file);
            EnemyViewController controller = loader.getController();
            controller.setHp(e.getHP(), e.getMaxHP());
            controller.setIntent(e.getIntents());
            ImageView img = new ImageView(e.getImage());
            img.setFitWidth(150);
            img.setFitHeight(150);
            controller.getButton().setGraphic(img);
            controller.getButton().setText("");
            controller.getButton().setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            CombatManager.getInstance().targetSelected(e);
                        }
                    }
            );
            controller.setStatus(e.getStatusEffects());
            controller.setName(e.getName());
            enemies.getChildren().add(pane);
        }
    }

    public void updatePotions(){
        reloadPotions();
    }

    public void updateCardPiles(){
        hand.getChildren().clear();
        ArrayList<Card> cards = CombatManager.getInstance().getHand();
        int i = 0;

        for (Card c : cards) {
            Button cardBtn = new Button("");
            ImageView img = new ImageView(c.getImage());
            img.setFitWidth(147);
            img.setFitHeight(200.0);
            cardBtn.setGraphic(img);
            cardBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    CombatManager.getInstance().cardSelected(c);
                }
            });
            hand.getChildren().add(cardBtn);
            cardBtn.setLayoutX(155 * i);
            cardBtn.setLayoutY(0);
            i++;
        }
        //hand.setPrefWrapLength(147 * cards.size());

        drawPile.setText("");
        ImageView drawPileImg = new ImageView("CardBackRed.png");
        drawPileImg.setFitHeight(200);
        drawPileImg.setFitWidth(147 );
        drawPile.setGraphic(drawPileImg);
        drawPile.setGraphicTextGap(0);
        numDraw.setText(CombatManager.getInstance().getDrawPileSize() + "");

        ImageView discardPileImg = new ImageView("CardBackBlue.png");
        discardPileImg.setFitHeight(200);
        discardPileImg.setFitWidth(147 );
        discardPile.setText("");
        discardPile.setGraphic(discardPileImg);
        numDiscard.setText(CombatManager.getInstance().getDiscardPileSize() +"");

    }

    @FXML
    void backToMap() {
        CombatManager.getInstance().backToMap();
    }

    @FXML
    void showDrawPile() {
        if(CombatManager.getInstance().getDrawPile().isEmpty())
            return;
        popUpDisplay.setDisable(false);
        popUpDisplay.setVisible(true);
        FlowPane cards = (FlowPane) (((ScrollPane)popUpDisplay.getChildren().get(0)).getContent());
        cards.getChildren().clear();
        for(Card c : CombatManager.getInstance().getDrawPile()) {
            ImageView img = new ImageView(c.getImage());
            img.setFitWidth(148*1.5);
            img.setFitHeight(200*1.5);
            cards.getChildren().add(img);
        }
        System.out.println("Showing draw pile");
    }



    @FXML
    void showDiscardPile() {
        if(CombatManager.getInstance().getDiscardPile().isEmpty())
            return;
        popUpDisplay.setDisable(false);
        popUpDisplay.setVisible(true);
        FlowPane cards = (FlowPane) (((ScrollPane)popUpDisplay.getChildren().get(0)).getContent());
        cards.getChildren().clear();
        for(Card c : CombatManager.getInstance().getDiscardPile()) {
            ImageView img = new ImageView(c.getImage());
            img.setFitWidth(148*1.5);
            img.setFitHeight(200*1.5);
            cards.getChildren().add(img);
        }
        System.out.println("Showing discard pile");
    }

    @FXML
    void openMap(ActionEvent event) {
        CombatManager.getInstance().showMap();
    }

    @FXML
    void showDeck(ActionEvent event) {
        CombatManager.getInstance().showDeck();
    }

    @FXML
    void openSettings(ActionEvent event) { ///yeni fxml ve controller kur
        CombatManager.getInstance().showSettings();
    }

    void showPrompt(boolean show ,String name) {
        targetPrompt.setText("Choose a target for " + name);
        targetPrompt.setVisible(show);
    }
}
